package code.org.werewolfmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import code.org.werewolfmanagement.adapter.WolfPlayerRecViewAdapter;
import code.org.werewolfmanagement.model.PlayerModel;
import code.org.werewolfmanagement.model.RoomModel;
import code.org.werewolfmanagement.utils.FirebaseUtil;

public class WolfFragment extends Fragment {

    private RecyclerView wolfPlayerRecView;
    private WolfPlayerRecViewAdapter adapter;
    private Bundle bundleFromNightFragment;
    private RoomModel roomModel;
    private PlayerModel playerModel;
    private String roomId;

    public WolfFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wolf, container, false);

        initView(view);

        getDataFromNightFragment();

        setUpWolfPlayerRecView();

        return view;
    }

    private void setUpWolfPlayerRecView(){
        Query query = FirebaseUtil.getPlayerReference(roomId)
                .whereEqualTo("role", "Wolf")
                .orderBy("playerId", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<PlayerModel> options = new FirestoreRecyclerOptions.Builder<PlayerModel>()
                .setQuery(query, PlayerModel.class).build();

        adapter = new WolfPlayerRecViewAdapter(options, getContext());
        wolfPlayerRecView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        wolfPlayerRecView.setAdapter(adapter);
        adapter.startListening();
    }

    private void getDataFromNightFragment(){
        bundleFromNightFragment = getArguments();
        roomModel = (RoomModel) bundleFromNightFragment.getSerializable("roomModel");
        roomId = roomModel.getRoomId();
    }

    private void initView(View view){
        wolfPlayerRecView = view.findViewById(R.id.wolfPlayerRecView);
    }
}