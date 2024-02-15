package code.org.werewolfmanagement;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import code.org.werewolfmanagement.adapter.PlayerRoleRecViewAdapter;
import code.org.werewolfmanagement.model.PlayerModel;
import code.org.werewolfmanagement.model.RoomModel;
import code.org.werewolfmanagement.utils.AndroidUtil;
import code.org.werewolfmanagement.utils.FirebaseUtil;

public class ShieldFragment extends Fragment implements PlayerRoleRecViewAdapter.OnItemClickListener {
    private Button protectBtn;
    private TextView nameRoomNightTxt, nightTxt;
    private RecyclerView shieldPlayerRecView, otherPlayerRecView;
    private PlayerRoleRecViewAdapter shieldAdapter, otherRoleAdapter;

    private RoomModel roomModel;
    private String roomId;

    private int countNight, countCall;


    public ShieldFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shield, container, false);

        initView(view);


        getDataFromNightFragment();

        setNameRoom();

        setNight();

        btnClick(false);
        setUpshieldPlayerRecView();


        protectBtn.setOnClickListener(v -> {
            btnClick(true);
            protectBtn.setEnabled(false);
            setUpOtherRoleRecView();
        });



        return view;
    }

    private void setArgument() {
        Bundle bundle = AndroidUtil.passModelByArgument(roomModel, countNight);
        bundle.putInt("countCall", countCall + 1);
        NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
        navController.navigate(R.id.navigateFromShieldFragmentToNightFragment, bundle);
    }

    private void setNameRoom() {
        String name = roomModel.getName();
        nameRoomNightTxt.setText(name);
    }

    private void setNight() {
        nightTxt.setText("Night " + countNight);
    }

    private void setUpOtherRoleRecView(){
        Query query = FirebaseUtil.getPlayerReference(roomId)
                .orderBy("playerId", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<PlayerModel> options = new FirestoreRecyclerOptions.Builder<PlayerModel>()
                .setQuery(query, PlayerModel.class).build();

        otherRoleAdapter = new PlayerRoleRecViewAdapter(options, getContext(), this);
        otherPlayerRecView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        otherPlayerRecView.setAdapter(otherRoleAdapter);
        otherRoleAdapter.startListening();
    }

    private void setUpshieldPlayerRecView() {
        Query query = FirebaseUtil.getPlayerReference(roomId)
                .whereEqualTo("role", "Shield")
                .orderBy("playerId", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<PlayerModel> options = new FirestoreRecyclerOptions.Builder<PlayerModel>()
                .setQuery(query, PlayerModel.class).build();

        shieldAdapter = new PlayerRoleRecViewAdapter(options, getContext());
        shieldPlayerRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        shieldPlayerRecView.setAdapter(shieldAdapter);
        shieldAdapter.startListening();
    }

    private void getDataFromNightFragment() {
        countNight = getArguments().getInt("count");
        countCall = getArguments().getInt("countCall");
        roomModel = AndroidUtil.getModelByArgument(getArguments());
        roomId = roomModel.getRoomId();
    }


    private void initView(View view) {
        shieldPlayerRecView = view.findViewById(R.id.shieldPlayerRecView);
        otherPlayerRecView = view.findViewById(R.id.otherPlayerRecView);

        protectBtn = view.findViewById(R.id.protectBtn);
        nameRoomNightTxt = view.findViewById(R.id.nameRoomNightTxt);
        nightTxt = view.findViewById(R.id.nightTxt);
    }

    private void btnClick(boolean isClick){
        if(isClick){
            shieldPlayerRecView.setVisibility(View.GONE);
            otherPlayerRecView.setVisibility(View.VISIBLE);
        }else {
            shieldPlayerRecView.setVisibility(View.VISIBLE);
            otherPlayerRecView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        countNight++;
        Log.d("FragmentcountNight", "Fragment created. countNight: " + countNight);
    }

    @Override
    public void onResume() {
        super.onResume();
        countNight = countNight;
        Log.d("FragmentcountNight", "Fragment resumed. countNight: " + countNight);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("FragmentcountNight", "Fragment paused. countNight: " + countNight);
    }

    @Override
    public void onItemClick(int position) {
        setArgument();
    }
}