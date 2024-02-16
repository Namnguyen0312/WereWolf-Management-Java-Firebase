package code.org.werewolfmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        createBtn.setOnClickListener(v -> {
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