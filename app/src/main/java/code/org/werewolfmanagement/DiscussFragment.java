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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import code.org.werewolfmanagement.adapter.DayPlayerRoleRecViewAdapter;
import code.org.werewolfmanagement.model.PlayerModel;
import code.org.werewolfmanagement.model.RoomModel;
import code.org.werewolfmanagement.utils.AndroidUtil;
import code.org.werewolfmanagement.utils.FirebaseUtil;

public class DiscussFragment extends Fragment implements DayPlayerRoleRecViewAdapter.OnItemClickListener {

    private TextView nameRoomDayTxt, dayTxt;
    private Button skipBtn;
    private RoomModel roomModel;
    private int countDay;
    private RecyclerView hangRecView;
    private DayPlayerRoleRecViewAdapter allPlayerAdapter;
    private String roomId;
    private int playerId;
    private PlayerModel playerModel;

    private static final String TAG = "DiscussFragment";

    public DiscussFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discuss, container, false);

        initView(view);

        getData();

        setNameRoom();

        setDay();

        setUpHangPlayerRecView();

        skipBtn.setOnClickListener(v -> {
            resetPlayer();
            setSkip();
        });


        return view;
    }

    private void resetPlayer() {
        FirebaseUtil.getPlayerReference(roomId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Map<String, Object> updates = new HashMap<>();
                            updates.put("bitten", false);
                            updates.put("protected", false);
                            FirebaseUtil.getPlayerWithIdReference(roomId, documentSnapshot.getId())
                                    .update(updates)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG, "Successfully updated!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error updating reset", e);
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

    private void setSkip(){
        setArgumentToNightFragment();
    }

    private void setUpHangPlayerRecView() {
        Query query = FirebaseUtil.getPlayerReference(roomId)
                .whereEqualTo("dead", false)
                .orderBy("playerId", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<PlayerModel> options = new FirestoreRecyclerOptions.Builder<PlayerModel>()
                .setQuery(query, PlayerModel.class).build();

        allPlayerAdapter = new DayPlayerRoleRecViewAdapter(options, getContext(), this);
        hangRecView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        hangRecView.setAdapter(allPlayerAdapter);
        allPlayerAdapter.startListening();

    }

    private void setArgumentToHangFragment() {
        Bundle bundle = AndroidUtil.passModelByArgument(roomModel, countDay);
        bundle.putInt("countCall", 0);
        bundle.putString("namePlayer", playerModel.getNamePlayer());
        NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
        navController.navigate(R.id.navigateFromDiscussFragmentToHangFragment, bundle);
    }

    private void setArgumentToNightFragment(){
        Bundle bundle = AndroidUtil.passModelByArgument(roomModel, countDay);
        bundle.putInt("countCall", 0);
        NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
        navController.navigate(R.id.navigateFromDiscussFragmentToNightFragment, bundle);
    }

    private void setDay() {
        dayTxt.setText("Day " + countDay);
    }

    private void setNameRoom() {
        String name = roomModel.getName();
        nameRoomDayTxt.setText(name);
    }


    private void getData() {
        countDay = getArguments().getInt("count");
        roomModel = AndroidUtil.getModelByArgument(getArguments());
        roomId = roomModel.getRoomId();
    }

    private void initView(View view) {
        nameRoomDayTxt = view.findViewById(R.id.nameRoomDayTxt);
        dayTxt = view.findViewById(R.id.dayTxt);
        skipBtn = view.findViewById(R.id.skipBtn);
        hangRecView = view.findViewById(R.id.hangRecView);
    }

    @Override
    public void onItemClick(int position) {
        playerModel = allPlayerAdapter.getItem(position);
        playerId = playerModel.getPlayerId();
        allPlayerAdapter.stopListening();
        FirebaseUtil.getPlayerReference(roomId)
                .whereEqualTo("playerId", playerId)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Map<String, Object> updates = new HashMap<>();
                            updates.put("dead", true);

                            if(documentSnapshot.getString("role").equals("Villager")){
                                roomModel.setValueOfVillager(roomModel.getValueOfVillager() - 1);
                                Log.d(TAG, "Villager" + roomModel.getValueOfVillager());
                            }else if (documentSnapshot.getString("role").equals("Shield")){
                                roomModel.setValueOfShield(roomModel.getValueOfShield() - 1);
                                Log.d(TAG, "Shield" + roomModel.getValueOfShield());
                            } else if (documentSnapshot.getString("role").equals("Wolf")) {
                                roomModel.setValueOfWolf(roomModel.getValueOfWolf() - 1);
                                Log.d(TAG, "Wolf" + roomModel.getValueOfWolf());
                            }

                            FirebaseUtil.getPlayerWithIdReference(roomId, documentSnapshot.getId())
                                    .update(updates)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            setArgumentToHangFragment();
                                            Log.d(TAG, "Successfully updated!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error updating dead", e);
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

    @Override
    public void onResume() {
        super.onResume();
        if(allPlayerAdapter!=null){
            allPlayerAdapter.notifyDataSetChanged();
        }
    }
}