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

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

import code.org.werewolfmanagement.model.PlayerModel;
import code.org.werewolfmanagement.model.RoomModel;
import code.org.werewolfmanagement.utils.FirebaseUtil;

public class ReceiveRolesActivity extends AppCompatActivity {

    private TextView nameRoomTxt, playerTxt, roleNameTxt;
    private ImageView roleImg;
    private Button nextPlayerBtn;
    private ProgressBar receiveRoleProgressBar;
    private RoomModel roomModel;
    private PlayerModel playerModel;

    private int count = 1;
    private String roomId;
    private Random random = new Random();
    private String randomValue;

    private int countWolf = 1, countVillager = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_roles);
        initView();


        setInProgress(false);
        receiveRole();

    }

    private void setRoles(){
        // TODO: Set player one
        playerTxt.setText("Player " + count);
        String[] rolesArray = getApplicationContext().getResources().getStringArray(R.array.roles);


        ArrayList<String> rolesList = new ArrayList<>(java.util.Arrays.asList(rolesArray));

        if(roomModel.getValueOfWolf() < countWolf){
            rolesList.remove("Wolf");
            randomValue = rolesList.get(random.nextInt(rolesList.size()));
            Log.i("array", randomValue);
        } else if (roomModel.getValueOfVillager() < countVillager) {
            rolesList.remove("Villager");
            randomValue = rolesList.get(random.nextInt(rolesList.size()));
            Log.i("array", randomValue);
        } else {
            randomValue = rolesList.get(random.nextInt(rolesList.size()));
            Log.i("array", randomValue);
        }


        if (randomValue.equals("Wolf")) {
            roleImg.setImageResource(R.drawable.werewolf_icon);
            roleNameTxt.setText("Wolf");
            countWolf++;
            Log.i("array", String.valueOf(countWolf));

        } else {
            roleImg.setImageResource(R.drawable.villager_icon);
            roleNameTxt.setText("Villager");
            countVillager++;
            Log.i("array", String.valueOf(countVillager));

        }
        /**
         * Dua du lieu player len firebase
         */
        setPlayersToFirebase();
        count++;
    }




    private void receiveRole() {
        roomId = FirebaseUtil.roomId;
        FirebaseUtil.getRoomWithIdReference(roomId).get().addOnCompleteListener(task -> {
            roomModel = task.getResult().toObject(RoomModel.class);
            String name = roomModel.getName();
            nameRoomTxt.setText(name);
            /**
             * Nhan role player 1
             */
            setRoles();

            /**
             * Nhan cac role tiep theo
             */
            nextPlayerBtn.setOnClickListener(v -> {

                if(count > roomModel.getNumberOfPlayer()){
                    // TODO: Chuyen toi man hinh choi game
                    navigateToGame();
                }else{
                    setRoles();

                }

            });
        });

    }

    private void setPlayersToFirebase(){
        setInProgress(true);
        FirebaseUtil.getPlayerReference(FirebaseUtil.roomId).add(new PlayerModel("Player"+ count, randomValue)).addOnCompleteListener(task -> {
            setInProgress(false);
        });
    }

    private void navigateToGame() {
        Intent intent = new Intent(getApplicationContext(), InGameActivity.class);
        startActivity(intent);
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