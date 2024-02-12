package code.org.werewolfmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

import code.org.werewolfmanagement.model.PlayerModel;
import code.org.werewolfmanagement.model.RoomModel;
import code.org.werewolfmanagement.utils.AndroidUtil;
import code.org.werewolfmanagement.utils.FirebaseUtil;

public class ReceiveRolesActivity extends AppCompatActivity {

    private TextView nameRoomTxt, playerTxt, roleNameTxt;
    private ImageView roleImg;
    private Button nextPlayerBtn;
    private ProgressBar receiveRoleProgressBar;
    private RoomModel roomModel;

    private int countPlayerId = 1;
    private String roomId;
    private Random random = new Random();
    private String randomValue;

    private int countWolf = 0, countVillager = 0, countShield = 0;

    private Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_roles);

        initView();

        setNameRoom();

        setInProgress(false);

        receiveRole();

    }

    private void setRoles() {
        // TODO: Set player one
        animation = AndroidUtil.getAnimation(getApplicationContext(), R.anim.fade);

        if (countPlayerId >= roomModel.getNumberOfPlayer()) {
            nextPlayerBtn.setText("Play");
        }

        playerTxt.setText("Player " + countPlayerId);
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


        /**
         * Dua du lieu player len firebase
         */
        setPlayersToFirebase();
        countPlayerId++;
    }

    private void setNameRoom() {
        roomModel = AndroidUtil.getRoomModelFromIntent(getIntent());
        roomId = roomModel.getRoomId();
        String name = roomModel.getName();
        nameRoomTxt.setText(name);
    }

    private void receiveRole() {
        /**
         * Nhan role player 1
         */
        setRoles();

        /**
         * Nhan cac role tiep theo
         */
        nextPlayerBtn.setOnClickListener(v -> {

            if (countPlayerId > roomModel.getNumberOfPlayer()) {
                // TODO: Chuyen toi man hinh choi game
                navigateToGame();
            } else {
                setRoles();
            }

        });

    }

    private void setPlayersToFirebase() {
        setInProgress(true);
        FirebaseUtil.getPlayerReference(roomId).add(new PlayerModel(countPlayerId, randomValue)).addOnCompleteListener(task -> {
            setInProgress(false);
        });
    }

    private void navigateToGame() {
        Intent intent = new Intent(getApplicationContext(), InGameActivity.class);
        AndroidUtil.passRoomModelAsIntent(intent, roomModel, roomId);
        startActivity(intent);
        overridePendingTransition(R.anim.fade, R.anim.slide_out);
    }

    private void initView() {
        nameRoomTxt = findViewById(R.id.nameRoomTxt);
        playerTxt = findViewById(R.id.playerTxt);
        roleNameTxt = findViewById(R.id.roleNameTxt);
        roleImg = findViewById(R.id.roleImg);
        nextPlayerBtn = findViewById(R.id.nextPlayerBtn);
        receiveRoleProgressBar = findViewById(R.id.progressBar);
    }

    private void setInProgress(boolean inProgress) {
        if (inProgress) {
            receiveRoleProgressBar.setVisibility(View.VISIBLE);
            nextPlayerBtn.setVisibility(View.GONE);
        } else {
            receiveRoleProgressBar.setVisibility(View.GONE);
            nextPlayerBtn.setVisibility(View.VISIBLE);
        }
    }
}