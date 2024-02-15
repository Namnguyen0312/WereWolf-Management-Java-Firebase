package code.org.werewolfmanagement;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import code.org.werewolfmanagement.adapter.PlayerRoleRecViewAdapter;
import code.org.werewolfmanagement.model.PlayerModel;
import code.org.werewolfmanagement.model.RoomModel;
import code.org.werewolfmanagement.utils.AndroidUtil;
import code.org.werewolfmanagement.utils.FirebaseUtil;

public class WolfFragment extends Fragment implements PlayerRoleRecViewAdapter.OnItemClickListener {
    private Button biteBtn;
    private TextView nameRoomNightTxt, nightTxt;
    private RecyclerView wolfPlayerRecView, otherPlayerRecView;
    private PlayerRoleRecViewAdapter wolfAdapter, otherRoleAdapter;

    private RoomModel roomModel;
    private String roomId;

    private int countNight, countCall;


    public WolfFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wolf, container, false);

        initView(view);


        getDataFromNightFragment();

        setNameRoom();

        setNight();

        btnClick(false);
        setUpWolfPlayerRecView();

        biteBtn.setOnClickListener(v -> {
            btnClick(true);
            biteBtn.setEnabled(false);
            setUpOtherRoleRecView();
        });



        return view;
    }

    private void setArgument() {
        Bundle bundle = AndroidUtil.passModelByArgument(roomModel, countNight);
        bundle.putInt("countCall", countCall + 1);
        NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
        navController.navigate(R.id.navigateAgainWolfFragment, bundle);
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
                .whereNotEqualTo("role", "Wolf")
                .orderBy("playerId", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<PlayerModel> options = new FirestoreRecyclerOptions.Builder<PlayerModel>()
                .setQuery(query, PlayerModel.class).build();

        otherRoleAdapter = new PlayerRoleRecViewAdapter(options, getContext(), this);
        otherPlayerRecView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        otherPlayerRecView.setAdapter(otherRoleAdapter);
        otherRoleAdapter.startListening();
    }

    private void setUpWolfPlayerRecView() {
        Query query = FirebaseUtil.getPlayerReference(roomId)
                .whereEqualTo("role", "Wolf")
                .orderBy("playerId", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<PlayerModel> options = new FirestoreRecyclerOptions.Builder<PlayerModel>()
                .setQuery(query, PlayerModel.class).build();

        wolfAdapter = new PlayerRoleRecViewAdapter(options, getContext());
        wolfPlayerRecView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        wolfPlayerRecView.setAdapter(wolfAdapter);
        wolfAdapter.startListening();
    }

    private void getDataFromNightFragment() {
        countNight = getArguments().getInt("count");
        countCall = getArguments().getInt("countCall");
        roomModel = AndroidUtil.getModelByArgument(getArguments());
        roomId = roomModel.getRoomId();
    }


    private void initView(View view) {
        wolfPlayerRecView = view.findViewById(R.id.wolfPlayerRecView);
        otherPlayerRecView = view.findViewById(R.id.otherPlayerRecView);

        biteBtn = view.findViewById(R.id.biteBtn);
        nameRoomNightTxt = view.findViewById(R.id.nameRoomNightTxt);
        nightTxt = view.findViewById(R.id.nightTxt);
    }

    private void btnClick(boolean isClick){
        if(isClick){
            wolfPlayerRecView.setVisibility(View.GONE);
            otherPlayerRecView.setVisibility(View.VISIBLE);
        }else {
            wolfPlayerRecView.setVisibility(View.VISIBLE);
            otherPlayerRecView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(int position) {
        setArgument();
    }
}