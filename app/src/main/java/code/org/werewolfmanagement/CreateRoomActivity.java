package code.org.werewolfmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.Timestamp;

import code.org.werewolfmanagement.model.RoomModel;
import code.org.werewolfmanagement.utils.AndroidUtil;
import code.org.werewolfmanagement.utils.FirebaseUtil;

public class CreateRoomActivity extends AppCompatActivity {

    private EditText nameRoomEdt, numberEdt;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        initView();

        nextBtn.setOnClickListener(v -> {
            navigateChooseRole();
        });
    }

    /**
     * Truyền dữ liệu nhập vào gồm:tên phòng, số lượng người chơi
     * Chuyển tới ChooseRolesActivity
     */
    private void navigateChooseRole() {
        Intent intent = new Intent(this, ChooseRolesActivity.class);
        if (nameRoomEdt.getText().toString().equals("")) {
            nameRoomEdt.setError("Name room can't empty");
        } else {
            intent.putExtra("name", nameRoomEdt.getText().toString());
            if (numberEdt.getText().toString().equals("") || AndroidUtil.parseInt(numberEdt.getText().toString()) < 5) {
                numberEdt.setError("The number of players is not less than 5");
            } else {
                intent.putExtra("numberOfPlayer", numberEdt.getText().toString());
                startActivity(intent);
            }
        }

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }


    public void initView() {
        nameRoomEdt = findViewById(R.id.nameRoomEdt);
        numberEdt = findViewById(R.id.numberEdt);
        nextBtn = findViewById(R.id.nextBtn);
    }
}