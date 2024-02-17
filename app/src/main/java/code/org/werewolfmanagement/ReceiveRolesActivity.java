package code.org.werewolfmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import code.org.werewolfmanagement.model.PlayerModel;
import code.org.werewolfmanagement.model.RoomModel;
import code.org.werewolfmanagement.utils.AndroidUtil;
import code.org.werewolfmanagement.utils.FirebaseUtil;

public class ReceiveRolesActivity extends AppCompatActivity {

    private TextView nameRoomTxt, playerTxt, roleNameTxt;
    private EditText namePlayerEdtTxt;
    private ImageView roleImg;
    private Button nextPlayerBtn;
    private ProgressBar receiveRoleProgressBar;
    private RoomModel roomModel;

    private int countPlayerId = 1;
    private String roomId;
    private Random random = new Random();
    private String randomValue;
    private String namePlayer;

    private int countWolf = 0, countVillager = 0, countShield = 0;

    private Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_roles);

        initView();

        setNameRoom();

        setRoles();

        setInProgress(false);

        receiveRole();

    }

    /**
     * Đặt tên phòng ứng với tên đã nhập
     */
    private void setNameRoom() {
        roomModel = AndroidUtil.getRoomModelFromIntent(getIntent());
        roomId = roomModel.getRoomId();
        String name = roomModel.getName();
        nameRoomTxt.setText(name);
        playerTxt.setText("Player " + countPlayerId);

    }


    /**
     * Thực hiện tạo role sau khi nhấn nextPlayerBtn
     */
    private void receiveRole() {
        nextPlayerBtn.setOnClickListener(v -> {
            if (namePlayerEdtTxt.getText().toString().equals("")) {
                namePlayerEdtTxt.setError("Please Enter Name");
            } else {
                setPlayersToFirebase();
            }
        });
    }

    /**
     * Tạo ngẫu nhiên một Role trong các Role đã chọn trước đó ứng với từng player theo stt
     */
    private void setRoles() {

        animation = AndroidUtil.getAnimation(getApplicationContext(), R.anim.fade);

        if (countPlayerId >= roomModel.getNumberOfPlayer()) {
            nextPlayerBtn.setText("Play");
        }

        playerTxt.startAnimation(animation);
        String[] rolesArray = getApplicationContext().getResources().getStringArray(R.array.roles);

        ArrayList<String> rolesList = new ArrayList<>(java.util.Arrays.asList(rolesArray));

        if (roomModel.getValueOfWolf() == countWolf) {
            rolesList.remove("Wolf");
        }
        if (roomModel.getValueOfVillager() == countVillager) {
            rolesList.remove("Villager");
        }
        if (roomModel.getValueOfShield() == countShield) {
            rolesList.remove("Shield");
        }

        randomValue = rolesList.get(random.nextInt(rolesList.size()));

        if (randomValue.equals("Wolf")) {
            roleImg.setImageResource(R.drawable.werewolf_icon);
            roleNameTxt.setText("Wolf");
            roleImg.startAnimation(animation);
            roleNameTxt.startAnimation(animation);
            countWolf++;

        } else if (randomValue.equals("Villager")) {
            roleImg.setImageResource(R.drawable.villager_icon);
            roleNameTxt.setText("Villager");
            roleImg.startAnimation(animation);
            roleNameTxt.startAnimation(animation);
            countVillager++;
        } else {
            roleImg.setImageResource(R.drawable.shield_icon);
            roleNameTxt.setText("Shield");
            roleImg.startAnimation(animation);
            roleNameTxt.startAnimation(animation);
            countShield++;
        }
    }

    /**
     * Tạo từng player theo stt ứng với các role
     * thêm từng player vào Firebase Firestore
     */
    private void setPlayersToFirebase() {
        setInProgress(true);
        FirebaseUtil.getPlayerReference(roomId).add(new PlayerModel(countPlayerId, namePlayerEdtTxt.getText().toString(), randomValue, false, false, false, false)).addOnCompleteListener(task -> {
            setInProgress(false);
        });

        if (countPlayerId == roomModel.getNumberOfPlayer()) {
            navigateToInGameActivity();
        }else {
            setRoles();
            namePlayerEdtTxt.setText("");
            countPlayerId++;
            playerTxt.setText("Player " + countPlayerId);
        }


    }

    /**
     * Chuyển tới InGameActivity
     */
    private void navigateToInGameActivity() {
        Intent intent = new Intent(getApplicationContext(), InGameActivity.class);
        AndroidUtil.passRoomModelAsIntent(intent, roomModel, roomId);
        startActivity(intent);
        overridePendingTransition(R.anim.fade, R.anim.slide_out);
    }

    /**
     * Chỉnh visibitly cho receiveRoleProgressBar và nextPlayerBtn
     *
     * @param inProgress kiểm tra xem có đang trong quá trình push lên Firebase hay không?
     */
    private void setInProgress(boolean inProgress) {
        if (inProgress) {
            receiveRoleProgressBar.setVisibility(View.VISIBLE);
            nextPlayerBtn.setVisibility(View.GONE);
        } else {
            nextPlayerBtn.setVisibility(View.VISIBLE);
            receiveRoleProgressBar.setVisibility(View.GONE);
        }
    }

    private void initView() {
        nameRoomTxt = findViewById(R.id.nameRoomTxt);
        playerTxt = findViewById(R.id.playerTxt);
        roleNameTxt = findViewById(R.id.roleNameTxt);
        roleImg = findViewById(R.id.roleImg);
        nextPlayerBtn = findViewById(R.id.nextPlayerBtn);
        receiveRoleProgressBar = findViewById(R.id.progressBar);
        namePlayerEdtTxt = findViewById(R.id.namePlayerEdtTxt);
    }



}