package code.org.werewolfmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import code.org.werewolfmanagement.utils.MediaPlayerUtil;

public class MainActivity extends AppCompatActivity {

    private Button createBtn;
    private MediaPlayerUtil mainMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        mainMedia = MediaPlayerUtil.getInstance();

        mainMedia.playMedia(getApplicationContext(), R.raw.creat_room);

        createBtn.setOnClickListener(v -> {
            mainMedia.stopMedia();
            navigateCreateRoom();
        });
    }

    private void navigateCreateRoom() {
        Intent intent = new Intent(this, CreateRoomActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void initView() {
        createBtn = findViewById(R.id.createBtn);
    }
}