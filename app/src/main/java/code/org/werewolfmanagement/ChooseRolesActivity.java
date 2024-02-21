package code.org.werewolfmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.Timestamp;

import code.org.werewolfmanagement.adapter.ChooseRoleRecViewAdapter;
import code.org.werewolfmanagement.model.RoleModel;
import code.org.werewolfmanagement.model.RoomModel;
import code.org.werewolfmanagement.utils.AndroidUtil;
import code.org.werewolfmanagement.utils.FirebaseUtil;
import code.org.werewolfmanagement.utils.RoleUtil;

public class ChooseRolesActivity extends AppCompatActivity {

    private TextView maxPlayerTxt;
    private ImageView backBtn;
    private Button playBtn;
    private RecyclerView chooseRoleRecView;
    private ChooseRoleRecViewAdapter adapter;
    private ProgressBar chooseRoleProgressBar;
    private int numberOfPlayerInt;
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

        roomModel = new RoomModel();
        roomModel.setNumberOfPlayer(numberOfPlayerInt);

        backBtn.setOnClickListener(v -> {
            setBack();
        });


        adapter = new ChooseRoleRecViewAdapter(roomModel);
        chooseRoleRecView.setAdapter(adapter);
        chooseRoleRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter.setRoles(RoleUtil.getInstance().getRoleModels());


        playBtn.setOnClickListener(v -> {
            setRoom();
        });

    }

    private void setBack(){
        Intent intent = new Intent(this, CreateRoomActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * Tạo Room Model mới
     * Kiểm tra xem số lượng role thêm vào có bằng số lượng người chơi không?
     * Nếu bằng, thêm model vào Firebase FireStore
     */
    private void setRoom() {

        roomModel = new RoomModel(nameRoom, Timestamp.now(), numberOfPlayerInt, adapter.getValueWolf(), adapter.getValueVillager(), adapter.getValueShield(), adapter.getValueSeer());
        if (AndroidUtil.isEqualSumAllRoles(adapter.getValueWolf(), adapter.getValueVillager(), adapter.getValueShield(), adapter.getValueSeer(), numberOfPlayerInt) &&
            !(AndroidUtil.isWolfMoreThanVillagers(adapter.getValueWolf(), adapter.getValueVillager(), adapter.getValueShield(), adapter.getValueSeer())) && adapter.getValueWolf() != 0) {
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
        playBtn = findViewById(R.id.playBtn);
        backBtn = findViewById(R.id.backBtn);
        chooseRoleProgressBar = findViewById(R.id.progressBar);
        maxPlayerTxt = findViewById(R.id.maxPlayerTxt);
        chooseRoleRecView = findViewById(R.id.chooseRoleRecView);
    }


}