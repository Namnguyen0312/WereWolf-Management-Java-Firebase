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
    private ProgressBar createRoomProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        initView();

        nextBtn.setOnClickListener(v -> {
            navigateChooseRole();
        });
    }

//    private void setRoom() {
//        setInProgress(true);
//        String roomId = FirebaseUtil.getChatRoomId();
//
//        String nameRoom = nameRoomEdt.getText().toString();
////        if(nameRoom.isEmpty()){
////            nameRoomEdt.setError("Please Enter Name");
////        }
//
//        String number = numberEdt.getText().toString();
//
////        if(number.isEmpty()){
////            numberEdt.setError("Number of  player at least 3 people");
////        }
//
//
//        if (roomModel != null) {
//            roomModel.setName(nameRoom);
//            roomModel.setNumberOfPlayer(number);
//        } else {
//            roomModel = new RoomModel(nameRoom, roomId, Timestamp.now(), number);
//        }
//
//        FirebaseUtil.getRoomReference(roomId).set(roomModel).addOnCompleteListener(task -> {
//            setInProgress(false);
//            if (task.isSuccessful()) {
//                // TODO: Navigate to choose roles
//                navigateChooseRole();
//
//            }
//        });
//
//    }

    private void navigateChooseRole() {
        Intent intent = new Intent(this, ChooseRolesActivity.class);
        intent.putExtra("name", nameRoomEdt.getText().toString());
        intent.putExtra("numberOfPlayer", numberEdt.getText().toString());
        startActivity(intent);
    }

//    private void setInProgress(boolean inProgress) {
//        if (inProgress) {
//            createRoomProgressBar.setVisibility(View.VISIBLE);
//            nextBtn.setVisibility(View.GONE);
//        } else {
//            createRoomProgressBar.setVisibility(View.GONE);
//            nextBtn.setVisibility(View.VISIBLE);
//        }
//    }

    public void initView() {
        nameRoomEdt = findViewById(R.id.nameRoomEdt);
        numberEdt = findViewById(R.id.numberEdt);
        nextBtn = findViewById(R.id.nextBtn);
        createRoomProgressBar = findViewById(R.id.progressBar);
    }
}