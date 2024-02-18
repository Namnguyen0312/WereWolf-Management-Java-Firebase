package code.org.werewolfmanagement;

import android.app.ActivityOptions;
import android.content.Intent;
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
import code.org.werewolfmanagement.utils.MediaPlayerUtil;


public class EndGameFragment extends Fragment {

    private TextView nameRoomDayTxt, dayTxt, endTxt;
    private RoomModel roomModel;
    private LinearLayout layoutClick;
    private int countDay;
    private boolean isWolfWin;

    private MediaPlayerUtil endgameMedia;

    public EndGameFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_end_game, container, false);

        initView(view);

        getData();

        setNameRoom();

        setDay();

        if(isWolfWin){
            endTxt.setText("Wolf Win");
        }else {
            endTxt.setText("Villager Win");
        }

        endgameMedia = MediaPlayerUtil.getInstance();
        endgameMedia.stopMedia();
        endgameMedia.playMedia(getContext(), R.raw.firework);

        layoutClick.setOnClickListener(v -> {
            endgameMedia.stopMedia();
            navigateToMainActivity();
        });



        return view;
    }

    private void navigateToMainActivity(){
        ActivityOptions options = ActivityOptions.makeCustomAnimation(requireContext(), R.anim.fade_in, R.anim.fade_out);

        Intent intent = new Intent(requireContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        Bundle animationBundle = options.toBundle();
        intent.putExtras(animationBundle);

        startActivity(intent,animationBundle);
    }

    private void setDay() {
        dayTxt.setText("Day " + countDay);
    }

    private void setNameRoom() {
        String name = roomModel.getName();
        nameRoomDayTxt.setText(name);
    }

    private void getData() {
        countDay = getArguments().getInt("count");
        isWolfWin = getArguments().getBoolean("isWolfWin");
        roomModel = AndroidUtil.getModelByArgument(getArguments());
    }

    private void initView(View view) {
        nameRoomDayTxt = view.findViewById(R.id.nameRoomDayTxt);
        dayTxt = view.findViewById(R.id.dayTxt);
        layoutClick = view.findViewById(R.id.layoutClick);
        endTxt = view.findViewById(R.id.endTxt);
    }
}