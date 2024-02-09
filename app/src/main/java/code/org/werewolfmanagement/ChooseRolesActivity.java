package code.org.werewolfmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.Timestamp;

import code.org.werewolfmanagement.model.RoomModel;
import code.org.werewolfmanagement.utils.AndroidUtil;
import code.org.werewolfmanagement.utils.FirebaseUtil;

public class ChooseRolesActivity extends AppCompatActivity {

    private TextView countWolfTxt, countVillagerTxt, maxPlayerTxt;
    private ImageView removeWolfBtn, addWolfBtn, removeVillagerBtn, addVillagerBtn;
    private Button playBtn;
    private ProgressBar chooseRoleProgressBar;
    private int countWolfInt, countVillagerInt, numberOfPlayerInt;
    private String nameRoom, numberOfPlayer;
    private RoomModel roomModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_roles);

        initView();

        setInProgress(false);


        nameRoom = getIntent().getExtras().getString("name");
        numberOfPlayer = getIntent().getExtras().getString("numberOfPlayer");
        numberOfPlayerInt = AndroidUtil.parseInt(numberOfPlayer);

        maxPlayerTxt.setText("Roles (Max " + numberOfPlayer + " Roles)");

        removeWolfBtn.setOnClickListener(v -> {
            removeWolf();
        });

        addWolfBtn.setOnClickListener(v -> {
            addWolf();
        });

        removeVillagerBtn.setOnClickListener(v -> {
            removeVillager();
        });

        addVillagerBtn.setOnClickListener(v -> {
            addVillager();
        });

        playBtn.setOnClickListener(v -> {
            setRoom();
        });

    }

    private void setRoom() {

        roomModel = new RoomModel(nameRoom, Timestamp.now(), numberOfPlayerInt, countWolfInt, countVillagerInt);
        if (AndroidUtil.isEqualSumWolfAndVillager(countWolfInt, countVillagerInt, numberOfPlayerInt)) {
            setInProgress(true);
            FirebaseUtil.getRoomReference().add(roomModel).addOnCompleteListener(task -> {
                setInProgress(false);
                if (task.isSuccessful()) {
                    FirebaseUtil.getRoomId(task.getResult().getId());
                    // TODO: Navigate to receive roles
                    Intent intent = new Intent(this, ReceiveRolesActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            AndroidUtil.showToast(this, "Not enough roles");
        }


    }

    private void removeWolf() {
        countWolfInt = AndroidUtil.parseInt(countWolfTxt.getText().toString());
        if (countWolfInt == 0) {
        } else {
            countWolfInt--;
            countWolfTxt.setText(String.valueOf(countWolfInt));
        }
    }

    private void addWolf() {
        countWolfInt = AndroidUtil.parseInt(countWolfTxt.getText().toString());
        if (AndroidUtil.isMoreThanSumWolfAndVillager(countWolfInt, countVillagerInt, numberOfPlayerInt)) {
        } else {
            countWolfInt++;
            countWolfTxt.setText(String.valueOf(countWolfInt));
        }
    }

    private void removeVillager() {
        countVillagerInt = AndroidUtil.parseInt(countVillagerTxt.getText().toString());
        if (countVillagerInt == 0) {
        } else {
            countVillagerInt--;
            countVillagerTxt.setText(String.valueOf(countVillagerInt));
        }
    }

    private void addVillager() {
        countVillagerInt = AndroidUtil.parseInt(countVillagerTxt.getText().toString());
        if (AndroidUtil.isMoreThanSumWolfAndVillager(countWolfInt, countVillagerInt, numberOfPlayerInt)) {
        } else {
            countVillagerInt++;
            countVillagerTxt.setText(String.valueOf(countVillagerInt));
        }
    }

    private void initView() {
        countWolfTxt = findViewById(R.id.countWolfTxt);
        countVillagerTxt = findViewById(R.id.countVillagerTxt);
        playBtn = findViewById(R.id.playBtn);
        removeWolfBtn = findViewById(R.id.removeWolfBtn);
        addWolfBtn = findViewById(R.id.addWolfBtn);
        removeVillagerBtn = findViewById(R.id.removeVillagerBtn);
        addVillagerBtn = findViewById(R.id.addVillagerBtn);
        chooseRoleProgressBar = findViewById(R.id.progressBar);
        maxPlayerTxt = findViewById(R.id.maxPlayerTxt);
    }

    private void setInProgress(boolean inProgress) {
        if (inProgress) {
            chooseRoleProgressBar.setVisibility(View.VISIBLE);
            playBtn.setVisibility(View.GONE);
        } else {
            chooseRoleProgressBar.setVisibility(View.GONE);
            playBtn.setVisibility(View.VISIBLE);
        }
    }

}