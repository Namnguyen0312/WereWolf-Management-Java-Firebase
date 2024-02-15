package code.org.werewolfmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
    private static final String TAG = "InGameActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);


//        initView();


        setArgument();

    }

//
//    private void setDayOne(){
//        AndroidUtil.getFragmentManagerAndSetAnim(getSupportFragmentManager(), "DayFragment",R.id.dayScr, dayFragment, R.anim.fade_in, R.anim.fade_out);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                AndroidUtil.getFragmentManagerAndSetAnim(getSupportFragmentManager(), "NightFragment",R.id.dayScr, nightFragment, R.anim.fade_in, R.anim.fade_out);
//            }
//        }, 3000);
//
//    }


    private void setArgument() {
        Bundle bundle = AndroidUtil.passModelByArgument(AndroidUtil.getRoomModelFromIntent(getIntent()), 0);
        bundle.putInt("countCall", 0);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navHostFragment.getNavController().navigate(R.id.dayFragment, bundle);
    }


//    private void initView() {
//
//        dayFragment = new DayFragment();
//
//    }
}