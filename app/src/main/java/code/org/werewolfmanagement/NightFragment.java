package code.org.werewolfmanagement;

import android.os.Bundle;

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

public class NightFragment extends Fragment {

    private LinearLayout nightClick;
    private TextView nameRoomNightTxt, nightTxt, callTxt;
    private RoomModel roomModel;
    private int countNight, countCall;


    public NightFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_night, container, false);

        initView(view);

        getDataFromDayFragment();

        setNameRoom();

        setNight();

        setCall();

        nightClick.setOnClickListener(v -> {
            setArgument();
        });

        return view;
    }


    private void setArgument() {
        Bundle bundle = AndroidUtil.passModelByArgument(roomModel, countNight);
        bundle.putInt("countCall", countCall);
        if (roomModel.getValueOfShield() != 0){
            if (countCall == 0){
                NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
                navController.navigate(R.id.navigateToShieldFragment, bundle);
            }
        }
        if (roomModel.getValueOfWolf() != 0){
            if (countCall == 1) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
                navController.navigate(R.id.navigateToWolfFragment, bundle);
            }
        }


    }

    private void setCall(){
        if (roomModel.getValueOfShield() != 0){
            if(countCall == 0){
                callTxt.setText("Shield, Please Wake Up");
            }
        }
        else countCall++;
        if (roomModel.getValueOfWolf() != 0){
            if (countCall == 1) {
                callTxt.setText("Wolf, Please Wake Up");
            }
        }
        else countCall++;

    }

    private void setNight() {
        nightTxt.setText("Night " + countNight);
    }

    private void setNameRoom() {
        String name = roomModel.getName();
        nameRoomNightTxt.setText(name);
    }

    private void getDataFromDayFragment() {
        countNight = getArguments().getInt("count");
        countCall = getArguments().getInt("countCall");
        roomModel = AndroidUtil.getModelByArgument(getArguments());
    }


    public void initView(View view) {
        nameRoomNightTxt = view.findViewById(R.id.nameRoomNightTxt);
        nightTxt = view.findViewById(R.id.nightTxt);
        nightClick = view.findViewById(R.id.nightClick);
        callTxt = view.findViewById(R.id.callTxt);
    }

}