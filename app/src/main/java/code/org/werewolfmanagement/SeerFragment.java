package code.org.werewolfmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import code.org.werewolfmanagement.adapter.RoleRecViewAdapter;
import code.org.werewolfmanagement.adapter.SeerRecViewAdapter;
import code.org.werewolfmanagement.model.PlayerModel;
import code.org.werewolfmanagement.model.RoomModel;
import code.org.werewolfmanagement.utils.AndroidUtil;
import code.org.werewolfmanagement.utils.FirebaseUtil;

public class SeerFragment extends Fragment implements SeerRecViewAdapter.OnItemClickListener {
    private Button seerBtn, nextBtn;
    private TextView nameRoomNightTxt, nightTxt;
    private RecyclerView seerPlayerRecView, otherPlayerRecView;
    private RoleRecViewAdapter seerAdapter;
    private SeerRecViewAdapter otherRoleAdapter;
    private RoomModel roomModel;
    private String roomId;
    private int countNight, countCall;
    private PlayerModel playerModel;

    public SeerFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        if(otherRoleAdapter!=null){
            otherRoleAdapter.notifyDataSetChanged();
        }
        if(seerAdapter!=null){
            seerAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seer, container, false);

        initView(view);


        getDataFromNightFragment();

        setNameRoom();

        setNight();

        btnClick(false);
        setUpSeerPlayerRecView();

        seerBtn.setOnClickListener(v -> {
            btnClick(true);
            seerBtn.setEnabled(false);
            setUpOtherRoleRecView();
        });

        nextBtn.setOnClickListener(v -> {
            setArgument();
        });


        return view;
    }

    private void setArgument() {
        Bundle bundle = AndroidUtil.passModelByArgument(roomModel, countNight);
        bundle.putInt("countCall", countCall + 1);
        bundle.putString("playerName", playerModel.getNamePlayer());
        bundle.putString("playerRole", playerModel.getRole());
        NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
        navController.navigate(R.id.navigateFromSeerFragmentToRoleWhenSeerFragment, bundle);
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
                .whereNotEqualTo("role", "Seer")
                .whereEqualTo("dead", false)
                .orderBy("playerId", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<PlayerModel> options = new FirestoreRecyclerOptions.Builder<PlayerModel>()
                .setQuery(query, PlayerModel.class).build();

        otherRoleAdapter = new SeerRecViewAdapter(options, getContext(), this);
        otherPlayerRecView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        otherPlayerRecView.setAdapter(otherRoleAdapter);
        otherRoleAdapter.startListening();
    }

    private void setUpSeerPlayerRecView() {
        Query query = FirebaseUtil.getPlayerReference(roomId)
                .whereEqualTo("role", "Seer")
                .whereEqualTo("dead", false)
                .orderBy("playerId", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<PlayerModel> options = new FirestoreRecyclerOptions.Builder<PlayerModel>()
                .setQuery(query, PlayerModel.class).build();

        seerAdapter = new RoleRecViewAdapter(options, getContext());
        seerPlayerRecView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        seerPlayerRecView.setAdapter(seerAdapter);
        seerAdapter.startListening();
    }

    private void getDataFromNightFragment() {
        countNight = getArguments().getInt("count");
        countCall = getArguments().getInt("countCall");
        roomModel = AndroidUtil.getModelByArgument(getArguments());
        roomId = roomModel.getRoomId();
    }

    private void btnClick(boolean isClick){
        if(isClick){
            otherPlayerRecView.setVisibility(View.VISIBLE);
            seerPlayerRecView.setVisibility(View.GONE);
        }else {
            seerPlayerRecView.setVisibility(View.VISIBLE);
            otherPlayerRecView.setVisibility(View.GONE);
        }
    }

    private void initView(View view) {
        seerPlayerRecView = view.findViewById(R.id.seerPlayerRecView);
        otherPlayerRecView = view.findViewById(R.id.otherPlayerRecView);
        nextBtn = view.findViewById(R.id.nextBtn);
        seerBtn = view.findViewById(R.id.seerBtn);
        nameRoomNightTxt = view.findViewById(R.id.nameRoomNightTxt);
        nightTxt = view.findViewById(R.id.nightTxt);
    }



    @Override
    public void onItemClick(int position) {
        playerModel = otherRoleAdapter.getItem(position);
        setArgument();
    }
}