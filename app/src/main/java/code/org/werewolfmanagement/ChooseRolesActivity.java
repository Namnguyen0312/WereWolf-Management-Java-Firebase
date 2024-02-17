package code.org.werewolfmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

    private TextView countWolfTxt, countVillagerTxt, countShieldTxt, maxPlayerTxt;
    private ImageView removeWolfBtn, addWolfBtn, removeVillagerBtn, addVillagerBtn, removeShieldBtn, addShieldBtn;
    private Button playBtn;
    private ProgressBar chooseRoleProgressBar;
    private int countWolfInt, countVillagerInt, countShieldInt, numberOfPlayerInt;
    private String nameRoom, numberOfPlayer, roomId;
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

        removeShieldBtn.setOnClickListener(v -> {
            removeShield();
        });

        addShieldBtn.setOnClickListener(v -> {
            addShield();
        });

        playBtn.setOnClickListener(v -> {
            setRoom();
        });

    }

    /**
     * Tạo Room Model mới
     * Kiểm tra xem số lượng role thêm vào có bằng số lượng người chơi không?
     * Nếu bằng, thêm model vào Firebase FireStore
     */
    private void setRoom() {

        roomModel = new RoomModel(nameRoom, Timestamp.now(), numberOfPlayerInt, countWolfInt, countVillagerInt, countShieldInt);
        if (AndroidUtil.isEqualSumAllRoles(countWolfInt, countVillagerInt, countShieldInt, numberOfPlayerInt) &&
            !(AndroidUtil.isWolfMoreThanVillagers(countWolfInt, countVillagerInt, countShieldInt))) {
            setInProgress(true);
            FirebaseUtil.getRoomReference().add(roomModel).addOnCompleteListener(task -> {
                setInProgress(false);
                if (task.isSuccessful()) {
                    roomId = task.getResult().getId();
                    navigateToReceiveRoles();
                }
            });
        } else {
            AndroidUtil.showToast(this, "Can't start game");
        }


    }

    /**
     * Chuyển tới RecevieRolesActivity
     */
    private void navigateToReceiveRoles() {
        Intent intent = new Intent(getApplicationContext(), ReceiveRolesActivity.class);
        AndroidUtil.passRoomModelAsIntent(intent, roomModel, roomId);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    /**
     * Giảm số lượng sói đi 1 sau khi nhấp vào dấu -
     * không thể giảm quá 0
     */
    private void removeWolf() {
        countWolfInt = AndroidUtil.parseInt(countWolfTxt.getText().toString());
        if (countWolfInt == 0) {
        } else {
            countWolfInt--;
            countWolfTxt.setText(String.valueOf(countWolfInt));
        }
    }

    /**
     * Tăng số lượng sói lên 1 sau khi nhấp vào dấu +
     */
    private void addWolf() {
        countWolfInt = AndroidUtil.parseInt(countWolfTxt.getText().toString());
        if (AndroidUtil.isMoreThanAllRoles(countWolfInt, countVillagerInt, countShieldInt, numberOfPlayerInt)) {
        } else {
            countWolfInt++;
            countWolfTxt.setText(String.valueOf(countWolfInt));
        }
    }

    /**
     * Giảm số lượng dân đi 1 sau khi nhấp vào dấu -
     * không thể giảm quá 0
     */
    private void removeVillager() {
        countVillagerInt = AndroidUtil.parseInt(countVillagerTxt.getText().toString());
        if (countVillagerInt == 0) {
        } else {
            countVillagerInt--;
            countVillagerTxt.setText(String.valueOf(countVillagerInt));
        }
    }

    /**
     * Tăng số lượng dân lên 1 sau khi nhấp vào dấu +
     */
    private void addVillager() {
        countVillagerInt = AndroidUtil.parseInt(countVillagerTxt.getText().toString());
        if (AndroidUtil.isMoreThanAllRoles(countWolfInt, countVillagerInt, countShieldInt, numberOfPlayerInt)) {
        } else {
            countVillagerInt++;
            countVillagerTxt.setText(String.valueOf(countVillagerInt));
        }
    }

    /**
     * Giảm số lượng khiên đi 1 sau khi nhấp vào dấu -
     * Không thể giảm quá 0
     */
    private void removeShield() {
        countShieldInt = AndroidUtil.parseInt(countShieldTxt.getText().toString());
        if (countShieldInt == 0) {
        } else {
            countShieldInt--;
            countShieldTxt.setText(String.valueOf(countShieldInt));
        }
    }

    /**
     * Tăng số lượng khiên lên 1 sau khi nhấp vào dấu +
     * tối đa 1 khiên trong game
     */
    private void addShield() {
        countShieldInt = AndroidUtil.parseInt(countShieldTxt.getText().toString());
        if (AndroidUtil.isMoreThanAllRoles(countWolfInt, countVillagerInt, countShieldInt, numberOfPlayerInt) || countShieldInt == 1) {
        } else {
            countShieldInt++;
            countShieldTxt.setText(String.valueOf(countShieldInt));
        }
    }

    /**
     * Chỉnh visibitly cho Progressbar và playBtn
     *
     * @param inProgress kiểm tra xem có đang trong quá trình push lên Firebase hay không?
     */
    private void setInProgress(boolean inProgress) {
        if (inProgress) {
            chooseRoleProgressBar.setVisibility(View.VISIBLE);
            playBtn.setVisibility(View.GONE);
        } else {
            chooseRoleProgressBar.setVisibility(View.GONE);
            playBtn.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        countWolfTxt = findViewById(R.id.countWolfTxt);
        countVillagerTxt = findViewById(R.id.countVillagerTxt);
        countShieldTxt = findViewById(R.id.countShieldTxt);
        playBtn = findViewById(R.id.playBtn);
        removeWolfBtn = findViewById(R.id.removeWolfBtn);
        addWolfBtn = findViewById(R.id.addWolfBtn);
        removeVillagerBtn = findViewById(R.id.removeVillagerBtn);
        addVillagerBtn = findViewById(R.id.addVillagerBtn);
        removeShieldBtn = findViewById(R.id.removeShieldBtn);
        addShieldBtn = findViewById(R.id.addShieldBtn);
        chooseRoleProgressBar = findViewById(R.id.progressBar);
        maxPlayerTxt = findViewById(R.id.maxPlayerTxt);
    }


}