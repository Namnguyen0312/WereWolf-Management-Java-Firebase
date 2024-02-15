package code.org.werewolfmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import code.org.werewolfmanagement.model.RoomModel;
import code.org.werewolfmanagement.utils.AndroidUtil;

public class DayFragment extends Fragment {

    private TextView nameRoomDayTxt, dayTxt;
    private RoomModel roomModel;
    private String roomId;
    private LinearLayout layoutClick;
    private int countDay;

    private static final String TAG = "DayFragment";

    public DayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        initView(view);

        setNameRoom();

        setDay();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                setArgument();
//            }
//        },3000);


        layoutClick.setOnClickListener(v -> {
            setArgument();
        });



        return view;
    }


    private void setDay() {
        dayTxt.setText("Day " + countDay);
    }

    private void setNameRoom() {
        roomModel = AndroidUtil.getModelByArgument(getArguments());
        countDay = getArguments().getInt("count") + 1;

        roomId = roomModel.getRoomId();
        String name = roomModel.getName();
        nameRoomDayTxt.setText(name);
    }

    private void setArgument() {
        Bundle bundle = AndroidUtil.passModelByArgument(roomModel, countDay);
        bundle.putInt("countCall", 0);
        NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
        navController.navigate(R.id.navigateToNightFragment, bundle);
    }

    public void initView(View view) {
        nameRoomDayTxt = view.findViewById(R.id.nameRoomDayTxt);
        dayTxt = view.findViewById(R.id.dayTxt);
        layoutClick = view.findViewById(R.id.layoutClick);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        countDay++;
        Log.d("FragmentcountDay", "Fragment created. countDay: " + countDay);
    }

    @Override
    public void onResume() {
        super.onResume();
        countDay = countDay;
        Log.d("FragmentcountDay", "Fragment resumed. countDay: " + countDay);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("FragmentcountDay", "Fragment paused. countDay: " + countDay);
    }
}