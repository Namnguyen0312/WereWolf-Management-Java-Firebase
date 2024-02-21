package code.org.werewolfmanagement;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import code.org.werewolfmanagement.model.RoomModel;
import code.org.werewolfmanagement.utils.AndroidUtil;

public class RoleWhenSeerFragment extends Fragment {
    private TextView nameRoomNightTxt, nightTxt, roleTxt;
    private LinearLayout nightClick;
    private RoomModel roomModel;
    private int countNight, countCall;
    private String playerName, playerRole;


    public RoleWhenSeerFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_role_when_seer, container, false);

        initView(view);

        getDataFromNightFragment();

        setNameRoom();

        setNight();

        if(playerRole.equals("Wolf")){
            roleTxt.setText(playerName + " is " + playerRole);
            roleTxt.setTextColor(ContextCompat.getColor(requireContext(),R.color.red));
        }else {
            roleTxt.setText(playerName + " is Villager");
            roleTxt.setTextColor(ContextCompat.getColor(requireContext(),R.color.my_secondary));
        }

        nightClick.setOnClickListener(v -> {
            setArgument();
        });

        return view;
    }

    private void setArgument() {
        Bundle bundle = AndroidUtil.passModelByArgument(roomModel, countNight);
        bundle.putInt("countCall", countCall);
        NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
        navController.navigate(R.id.navigateFromRoleWhenSeerFragmentToNightFragment, bundle);
    }

    private void setNameRoom() {
        String name = roomModel.getName();
        nameRoomNightTxt.setText(name);
    }

    private void setNight() {
        nightTxt.setText("Night " + countNight);
    }


    private void getDataFromNightFragment() {
        countNight = getArguments().getInt("count");
        countCall = getArguments().getInt("countCall");
        playerName = getArguments().getString("playerName");
        playerRole = getArguments().getString("playerRole");
        roomModel = AndroidUtil.getModelByArgument(getArguments());
    }


    private void initView(View view) {
        roleTxt = view.findViewById(R.id.roleTxt);
        nameRoomNightTxt = view.findViewById(R.id.nameRoomNightTxt);
        nightTxt = view.findViewById(R.id.nightTxt);
        nightClick = view.findViewById(R.id.nightClick);
    }

}