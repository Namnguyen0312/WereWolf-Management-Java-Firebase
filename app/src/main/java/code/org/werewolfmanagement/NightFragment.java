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

public class NightFragment extends Fragment {

    private TextView nameRoomNightTxt, nightTxt;
    private LinearLayout dayCallScr, dayActivityScr;
    private RoomModel roomModel;
    private String roomId;
    private int countNight = 0;

    public NightFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_night, container, false);

        initView(view);

        setNameRoom();

        setNight();

        return view;
    }

    private void setNight() {
        nightTxt.setText("Night " + countNight);
    }

    private void setNameRoom() {
        Bundle bundle = getArguments();
        roomModel = (RoomModel) bundle.getSerializable("roomModel");
        roomId = roomModel.getRoomId();
        String name = roomModel.getName();
        nameRoomNightTxt.setText(name);
    }

    public void initView(View view) {
        nameRoomNightTxt = view.findViewById(R.id.nameRoomNightTxt);
        nightTxt = view.findViewById(R.id.nightTxt);
        dayCallScr = view.findViewById(R.id.dayCallScr);
        dayActivityScr = view.findViewById(R.id.dayActivityScr);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        countNight++;
        Log.d("FragmentcountNight", "Fragment created. countNight: " + countNight);
    }

    @Override
    public void onResume() {
        super.onResume();
        countNight = countNight;
        Log.d("FragmentcountNight", "Fragment resumed. countNight: " + countNight);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("FragmentcountNight", "Fragment paused. countNight: " + countNight);
    }
}