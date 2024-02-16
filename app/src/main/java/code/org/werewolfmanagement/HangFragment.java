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

    private TextView nameRoomDayTxt, dayTxt;
    private RoomModel roomModel;
    private LinearLayout layoutClick;
    private int countDay;
    private RecyclerView hangRecView;
    private DayPlayerRoleRecViewAdapter allPlayerAdapter;
    private String roomId;

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

        setUpHangPlayerRecView();

        return view;
    }

    private void setUpHangPlayerRecView() {
        Query query = FirebaseUtil.getPlayerReference(roomId)
                .whereEqualTo("dead", false)
                .orderBy("playerId", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<PlayerModel> options = new FirestoreRecyclerOptions.Builder<PlayerModel>()
                .setQuery(query, PlayerModel.class).build();

        allPlayerAdapter = new DayPlayerRoleRecViewAdapter(options, getContext());
        hangRecView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        hangRecView.setAdapter(allPlayerAdapter);
        allPlayerAdapter.startListening();

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
        navController.navigate(R.id.navigateToNightFragment, bundle);
    }

    private void getData() {
        countDay = getArguments().getInt("count");
        roomModel = AndroidUtil.getModelByArgument(getArguments());
        roomId = roomModel.getRoomId();
    }

    private void initView(View view) {
        nameRoomDayTxt = view.findViewById(R.id.nameRoomDayTxt);
        dayTxt = view.findViewById(R.id.dayTxt);
        layoutClick = view.findViewById(R.id.layoutClick);
        hangRecView = view.findViewById(R.id.hangRecView);
    }
}