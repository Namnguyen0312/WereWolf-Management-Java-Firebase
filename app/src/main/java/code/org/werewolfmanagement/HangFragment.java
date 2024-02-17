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

public class HangFragment extends Fragment {

    private TextView nameRoomDayTxt, dayTxt, hangedPlayerTxt;
    private RoomModel roomModel;
    private LinearLayout layoutClick;
    private int countDay;
    private String namePlayer;
    private String roomId;

    private static final String TAG = "HangFragment";

    public HangFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hang, container, false);

        initView(view);

        getData();

        setNameRoom();

        setDay();

        setHangedPlayer();

        resetPlayer();


        layoutClick.setOnClickListener(v -> {
            if(roomModel.getValueOfWolf() == 0){
                setArgumentToEndGameFragmentWithWolfLose();
            } else if (AndroidUtil.isWolfMoreThanVillagers(roomModel.getValueOfWolf(), roomModel.getValueOfVillager(), roomModel.getValueOfShield())) {
                setArgumentToEndGameFragmentWithWolfWin();
            } else {
                setArgumentToNightFragment();
            }
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



    private void setHangedPlayer() {
        hangedPlayerTxt.setText(namePlayer);
    }

    private void setDay() {
        dayTxt.setText("Day " + countDay);
    }

    private void setNameRoom() {
        String name = roomModel.getName();
        nameRoomDayTxt.setText(name);
    }

    private void setArgumentToNightFragment() {
        Bundle bundle = AndroidUtil.passModelByArgument(roomModel, countDay);
        bundle.putInt("countCall", 0);
        NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
        navController.navigate(R.id.navigateFromHangFragmentToNightFragment, bundle);
    }

    private void setArgumentToEndGameFragmentWithWolfLose() {
        Bundle bundle = AndroidUtil.passModelByArgument(roomModel, countDay);
        bundle.putInt("countCall", 0);
        bundle.putBoolean("isWolfWin", false);
        NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
        navController.navigate(R.id.navigateFromHangFragmentToEndGameFragment, bundle);
    }
    private void setArgumentToEndGameFragmentWithWolfWin() {
        Bundle bundle = AndroidUtil.passModelByArgument(roomModel, countDay);
        bundle.putInt("countCall", 0);
        bundle.putBoolean("isWolfWin", true);
        NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
        navController.navigate(R.id.navigateFromHangFragmentToEndGameFragment, bundle);
    }

    

    private void getData() {
        countDay = getArguments().getInt("count");
        namePlayer = getArguments().getString("namePlayer");
        roomModel = AndroidUtil.getModelByArgument(getArguments());
        roomId = roomModel.getRoomId();
    }

    private void initView(View view) {
        nameRoomDayTxt = view.findViewById(R.id.nameRoomDayTxt);
        dayTxt = view.findViewById(R.id.dayTxt);
        hangedPlayerTxt = view.findViewById(R.id.hangedPlayerTxt);
        layoutClick = view.findViewById(R.id.layoutClick);
    }



}