package code.org.werewolfmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import code.org.werewolfmanagement.model.PlayerModel;
import code.org.werewolfmanagement.model.RoomModel;
import code.org.werewolfmanagement.utils.AndroidUtil;

public class InGameActivity extends AppCompatActivity {
    private TextView nameRoomDayTxt, nameRoomNightTxt;
    private LinearLayout dayScr, dayActivityScr, nightScr, nightActivityScr;
    private RoomModel roomModel;
    private PlayerModel playerModel;
    private String roomId;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        initView();

        setNameRoom();

        setDayOne();



    }

    private void setNameRoom() {
        roomModel = AndroidUtil.getRoomModelFromIntent(getIntent());
        roomId = roomModel.getRoomId();
        String name = roomModel.getName();
        nameRoomDayTxt.setText(name);
        nameRoomNightTxt.setText(name);
    }

    private void setDayScr(){
        dayScr.setVisibility(View.VISIBLE);
        nightScr.setVisibility(View.GONE);
        dayScr.startAnimation(animation);
    }

    private void setNightScr(){
        dayScr.setVisibility(View.GONE);
        nightScr.setVisibility(View.VISIBLE);
        nightScr.startAnimation(animation);

    }

    private void setDayOne(){
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);

        setDayScr();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                setNightScr();
            }
        }, 5000);
    }

    private void initView(){
        nameRoomDayTxt = findViewById(R.id.nameRoomDayTxt);
        nameRoomNightTxt = findViewById(R.id.nameRoomNightTxt);
        dayScr = findViewById(R.id.dayScr);
        dayActivityScr = findViewById(R.id.dayActivityScr);
        nightScr = findViewById(R.id.nightScr);
        nightActivityScr = findViewById(R.id.nightActivityScr);

    }
}