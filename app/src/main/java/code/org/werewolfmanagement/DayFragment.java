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

public class DayFragment extends Fragment {

    private TextView nameRoomDayTxt, dayTxt, dayCallTxt;
    private RoomModel roomModel;
    private LinearLayout layoutClick;
    private int countDay;


    public DayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        initView(view);

        getData();

        setNameRoom();

        setDay();

        if(countDay == 1){
            dayCallTxt.setText("The Villagers Have Descended Into Darkness");
            layoutClick.setOnClickListener(v -> {
                setArgumentToNightFragment();
            });
        }else {
            dayCallTxt.setText("The villagers have all stirred");
            layoutClick.setOnClickListener(v -> {
                setArgumentToDeadFragment();
            });
        }

        return view;
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
        navController.navigate(R.id.navigateToNightFragment, bundle);
    }

    private void setArgumentToDeadFragment() {
        Bundle bundle = AndroidUtil.passModelByArgument(roomModel, countDay);
        bundle.putInt("countCall", 0);
        NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
        navController.navigate(R.id.navigateFromDayFragmentToDeadFragment, bundle);
    }

    private void getData(){
        countDay = getArguments().getInt("count") + 1;
        roomModel = AndroidUtil.getModelByArgument(getArguments());
    }

    private void initView(View view) {
        nameRoomDayTxt = view.findViewById(R.id.nameRoomDayTxt);
        dayTxt = view.findViewById(R.id.dayTxt);
        dayCallTxt = view.findViewById(R.id.dayCallTxt);
        layoutClick = view.findViewById(R.id.layoutClick);
    }
}