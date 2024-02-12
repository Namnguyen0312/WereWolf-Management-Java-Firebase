package code.org.werewolfmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
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
    private LinearLayout nightCallScr, nightActivityScr;
    private RoomModel roomModel;
    private String roomId;
    private int countNight = 0;
    private WolfFragment wolfFragment;
    private Bundle bundleFromInGameAct,bundleToWolfFragment;

    public NightFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_night, container, false);

        initView(view);

        getDataFromInGameActivity();

        setNameRoom();

        setNight();

        setDataToFragmentChild();

        setNightCall();

        return view;
    }

    private void setNightCall(){
        setInActivity(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setInActivity(true);
                AndroidUtil.getFragmentManagerAndSetAnim(getChildFragmentManager(), R.id.nightActivityScr, wolfFragment, R.anim.fade_in, R.anim.fade_out);
            }
        },3000);
    }

    private void setDataToFragmentChild(){
        bundleToWolfFragment = new Bundle();
        bundleToWolfFragment.putSerializable("roomModel", roomModel);
        wolfFragment.setArguments(bundleToWolfFragment);
    }

    private void setInActivity(boolean inActivity){
        if (!inActivity) {
            nightCallScr.setVisibility(View.VISIBLE);
            nightActivityScr.setVisibility(View.GONE);
        } else {
            nightCallScr.setVisibility(View.GONE);
            nightActivityScr.setVisibility(View.VISIBLE);
        }
    }

    private void setNight() {
        nightTxt.setText("Night " + countNight);
    }

    private void setNameRoom() {
        String name = roomModel.getName();
        nameRoomNightTxt.setText(name);
    }

    private void getDataFromInGameActivity(){
        bundleFromInGameAct = getArguments();
        roomModel = (RoomModel) bundleFromInGameAct.getSerializable("roomModel");
        roomId = roomModel.getRoomId();
    }

    public void initView(View view) {
        nameRoomNightTxt = view.findViewById(R.id.nameRoomNightTxt);
        nightTxt = view.findViewById(R.id.nightTxt);
        nightCallScr = view.findViewById(R.id.nightCallScr);
        nightActivityScr = view.findViewById(R.id.nightActivityScr);
        wolfFragment = new WolfFragment();
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