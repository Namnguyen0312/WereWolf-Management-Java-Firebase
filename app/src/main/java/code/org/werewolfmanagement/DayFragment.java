package code.org.werewolfmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import code.org.werewolfmanagement.model.RoomModel;
import code.org.werewolfmanagement.utils.AndroidUtil;

public class DayFragment extends Fragment {

    private TextView nameRoomDayTxt, dayTxt;
    private LinearLayout dayCallScr, dayActivityScr;
    private RoomModel roomModel;
    private String roomId;
    private int countDay = 0;

    public DayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        initView(view);

        setNameRoom();

        setDay();

        return view;
    }

    private void setDay() {
        dayTxt.setText("Day " + countDay);
    }

    private void setNameRoom() {
        Bundle bundle = getArguments();
        roomModel = (RoomModel) bundle.getSerializable("roomModel");
        roomId = roomModel.getRoomId();
        String name = roomModel.getName();
        nameRoomDayTxt.setText(name);
    }

    public void initView(View view) {
        nameRoomDayTxt = view.findViewById(R.id.nameRoomDayTxt);
        dayTxt = view.findViewById(R.id.dayTxt);
        dayCallScr = view.findViewById(R.id.dayCallScr);
        dayActivityScr = view.findViewById(R.id.dayActivityScr);

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