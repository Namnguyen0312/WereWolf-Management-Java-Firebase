package code.org.werewolfmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import android.os.Bundle;
import code.org.werewolfmanagement.utils.AndroidUtil;

public class InGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        setArgument();

    }

    private void setArgument() {
        Bundle bundle = AndroidUtil.passModelByArgument(AndroidUtil.getRoomModelFromIntent(getIntent()), 0);
        bundle.putInt("countCall", 0);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navHostFragment.getNavController().navigate(R.id.dayFragment, bundle);
    }

}