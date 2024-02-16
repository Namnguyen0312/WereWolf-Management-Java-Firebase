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

public class DeadFragment extends Fragment {

    private TextView nameRoomDayTxt, dayTxt;
    private RoomModel roomModel;
    private LinearLayout layoutClick, deadScr, noOneDeadScr;
    private int countDay;
    private RecyclerView deadRecView;
    private DayPlayerRoleRecViewAdapter  deadPlayerAdapter;
    private String roomId;

    private static final String TAG = "DeadFragment";

    public DeadFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dead, container, false);
        initView(view);

        getData();

        setNameRoom();

        setDay();

        setUpKilledPlayerRecView();

        layoutClick.setOnClickListener(v -> {
            setArgument();
        });

        return view;
    }

    private void setDay() {
        dayTxt.setText("Day " + countDay);
    }

    private void setNameRoom() {
        String name = roomModel.getName();
        nameRoomDayTxt.setText(name);
    }

    private void setArgument() {
        Bundle bundle = AndroidUtil.passModelByArgument(roomModel, countDay);
        bundle.putInt("countCall", 0);
        NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
        navController.navigate(R.id.navigateFromDeadFragmentToHangFragment, bundle);
    }

    private void getData() {
        countDay = getArguments().getInt("count");
        roomModel = AndroidUtil.getModelByArgument(getArguments());
        roomId = roomModel.getRoomId();
    }

    private void deadView(boolean isNoOneDead) {
        if (isNoOneDead) {
            noOneDeadScr.setVisibility(View.VISIBLE);
            deadScr.setVisibility(View.GONE);
        } else {
            deadScr.setVisibility(View.VISIBLE);
            noOneDeadScr.setVisibility(View.GONE);
        }
    }


    private void setUpKilledPlayerRecView() {
        Query queryBitten = FirebaseUtil.getPlayerReference(roomId)
                .whereEqualTo("bitten", true)
                .orderBy("playerId", Query.Direction.ASCENDING);

        queryBitten.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                boolean isProtected = documentSnapshot.getBoolean("protected");
                                if (isProtected) {
                                    deadView(true);
                                } else {
                                    deadView(false);
                                    FirestoreRecyclerOptions<PlayerModel> options = new FirestoreRecyclerOptions.Builder<PlayerModel>()
                                            .setQuery(queryBitten, PlayerModel.class).build();

                                    deadPlayerAdapter = new DayPlayerRoleRecViewAdapter(options, getContext());
                                    deadRecView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                                    deadRecView.setAdapter(deadPlayerAdapter);
                                    deadPlayerAdapter.startListening();

                                    Map<String, Object> updates = new HashMap<>();
                                    updates.put("dead", true);

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
                                                    Log.w(TAG, "Error updating document", e);
                                                }
                                            });

                                }
                            }

                        } else {
                            deadView(true);
                        }
                        Log.d(TAG, "DocumentSnapshots successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });


    }
    

    private void initView(View view) {
        nameRoomDayTxt = view.findViewById(R.id.nameRoomDayTxt);
        dayTxt = view.findViewById(R.id.dayTxt);
        layoutClick = view.findViewById(R.id.layoutClick);
        deadRecView = view.findViewById(R.id.deadRecView);
        deadScr = view.findViewById(R.id.deadScr);
        noOneDeadScr = view.findViewById(R.id.noOneDeadScr);
    }
}