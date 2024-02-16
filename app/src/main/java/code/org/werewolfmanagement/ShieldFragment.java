package code.org.werewolfmanagement;

import android.os.Bundle;


import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import code.org.werewolfmanagement.adapter.NightPlayerRoleRecViewAdapter;
import code.org.werewolfmanagement.model.PlayerModel;
import code.org.werewolfmanagement.model.RoomModel;
import code.org.werewolfmanagement.utils.AndroidUtil;
import code.org.werewolfmanagement.utils.FirebaseUtil;

public class ShieldFragment extends Fragment implements NightPlayerRoleRecViewAdapter.OnItemClickListener {
    private Button protectBtn;
    private TextView nameRoomNightTxt, nightTxt;
    private RecyclerView shieldPlayerRecView, otherPlayerRecView;
    private NightPlayerRoleRecViewAdapter shieldAdapter, otherRoleAdapter;

    private RoomModel roomModel;
    private PlayerModel protectedPlayer;

    private String roomId;

    private int countNight, countCall;

    private static final String TAG = "ShieldFragment";

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

        otherRoleAdapter = new NightPlayerRoleRecViewAdapter(options, getContext(), this);
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

        shieldAdapter = new NightPlayerRoleRecViewAdapter(options, getContext());
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
            otherPlayerRecView.setVisibility(View.VISIBLE);
            shieldPlayerRecView.setVisibility(View.GONE);
        }else {
            shieldPlayerRecView.setVisibility(View.VISIBLE);
            otherPlayerRecView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(int position) {
        protectedPlayer = otherRoleAdapter.getItem(position);
        protectedPlayer.setProtected(true);
        FirebaseUtil.getPlayerReference(roomId)
                .whereEqualTo("playerId", protectedPlayer.getPlayerId())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String playerId = documentSnapshot.getId();
                            FirebaseUtil.getPlayerWithIdReference(roomId, playerId).set(protectedPlayer)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG, "Player successfully updated!");
                                            setArgument();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error updating player", e);
                                        }
                                    });
                            Log.d(TAG, "DocumentSnapshots successfully updated!");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }
}