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
    private DayFragment dayFragment;
    private NightFragment nightFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        initView();

        setDataToFragment();

        AndroidUtil.getFragmentManagerAndSetAnim(getSupportFragmentManager(), R.id.dayScr, dayFragment, R.anim.fade_in, R.anim.fade_out);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AndroidUtil.getFragmentManagerAndSetAnim(getSupportFragmentManager(), R.id.dayScr, nightFragment, R.anim.fade_in, R.anim.fade_out);
            }
        }, 3000);


    }

    private void setDataToFragment() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("roomModel", AndroidUtil.getRoomModelFromIntent(getIntent()));
        dayFragment.setArguments(bundle);
        nightFragment.setArguments(bundle);

    }


    private void initView() {

        dayFragment = new DayFragment();
        nightFragment = new NightFragment();


    }
}