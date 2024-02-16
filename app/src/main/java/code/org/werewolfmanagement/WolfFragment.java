package code.org.werewolfmanagement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class WolfFragment extends Fragment implements NightPlayerRoleRecViewAdapter.OnItemClickListener {
    private Button biteBtn;
    private TextView nameRoomNightTxt, nightTxt;
    private RecyclerView wolfPlayerRecView, otherPlayerRecView;
    private NightPlayerRoleRecViewAdapter wolfAdapter, otherRoleAdapter;

    private RoomModel roomModel;
    private PlayerModel bittenPlayer;
    private String roomId;

    private int countNight, countCall;

    private static final String TAG = "WolfFragment";

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
        bundle.putInt("countCall", countCall);
        NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
        navController.navigate(R.id.navigateFromWolfFragmentToDayFragment, bundle);
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

        otherRoleAdapter = new NightPlayerRoleRecViewAdapter(options, getContext(), this);
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

        wolfAdapter = new NightPlayerRoleRecViewAdapter(options, getContext());
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
            otherPlayerRecView.setVisibility(View.VISIBLE);
            wolfPlayerRecView.setVisibility(View.GONE);
        }else {
            wolfPlayerRecView.setVisibility(View.VISIBLE);
            otherPlayerRecView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(int position) {
        bittenPlayer = otherRoleAdapter.getItem(position);
        bittenPlayer.setBitten(true);
        FirebaseUtil.getPlayerReference(roomId)
                .whereEqualTo("playerId", bittenPlayer.getPlayerId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String playerId = documentSnapshot.getId();
                            FirebaseUtil.getPlayerWithIdReference(roomId, playerId).set(bittenPlayer)
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