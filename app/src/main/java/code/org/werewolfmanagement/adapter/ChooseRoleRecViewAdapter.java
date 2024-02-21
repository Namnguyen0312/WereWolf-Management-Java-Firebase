package code.org.werewolfmanagement.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import code.org.werewolfmanagement.R;
import code.org.werewolfmanagement.model.RoleModel;
import code.org.werewolfmanagement.model.RoomModel;
import code.org.werewolfmanagement.utils.AndroidUtil;

public class ChooseRoleRecViewAdapter extends RecyclerView.Adapter<ChooseRoleRecViewAdapter.ViewHolder> {

    private ArrayList<RoleModel> roles = new ArrayList<>();
    private RoomModel roomModel;
    private int countWolf, countVillager, countShield, countSeer;

    private static final String TAG = "ChooseRoleRecViewAdapte";
    public ChooseRoleRecViewAdapter(RoomModel roomModel) {
        this.roomModel = roomModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_role_rec_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (roles.get(position).getRole().equals("Wolf")){
            holder.roleImg.setImageResource(R.drawable.werewolf_icon);
            holder.nameRoleTxt.setText(roles.get(position).getRole());

            holder.removeBtn.setOnClickListener(v -> {
                RoleModel role = roles.get(position);
                countWolf = role.getValueWolf();
                if(countWolf != 0 ){
                    countWolf--;
                    holder.countTxt.setText(String.valueOf(countWolf));
                    role.setValueWolf(countWolf);
                }
            });

            holder.addBtn.setOnClickListener(v -> {
                RoleModel role = roles.get(position);
                countWolf = role.getValueWolf();
                if (AndroidUtil.isMoreThanAllRoles(countWolf, countVillager, countShield, countSeer,roomModel.getNumberOfPlayer())) {
                } else {
                    countWolf++;
                    holder.countTxt.setText(String.valueOf(countWolf));
                    role.setValueWolf(countWolf);
                    Log.d(TAG, "onBindViewHolder: wolf " + role.getValueWolf());
                }
            });
        } else if (roles.get(position).getRole().equals("Villager")) {
            holder.roleImg.setImageResource(R.drawable.villager_icon);
            holder.nameRoleTxt.setText(roles.get(position).getRole());

            holder.removeBtn.setOnClickListener(v -> {
                RoleModel role = roles.get(position);
                countVillager = role.getValueVillager();
                if(countVillager != 0 ){
                    countVillager--;
                    holder.countTxt.setText(String.valueOf(countVillager));
                    role.setValueVillager(countVillager);
                }
            });

            holder.addBtn.setOnClickListener(v -> {
                RoleModel role = roles.get(position);
                countVillager = role.getValueVillager();
                if (AndroidUtil.isMoreThanAllRoles(countWolf, countVillager, countShield, countSeer,roomModel.getNumberOfPlayer())) {
                } else {
                    countVillager++;
                    holder.countTxt.setText(String.valueOf(countVillager));
                    role.setValueVillager(countVillager);
                }
            });
        } else if (roles.get(position).getRole().equals("Shield")) {
            holder.roleImg.setImageResource(R.drawable.shield_icon);
            holder.nameRoleTxt.setText(roles.get(position).getRole());

            holder.removeBtn.setOnClickListener(v -> {
                RoleModel role = roles.get(position);
                countShield = role.getValueShield();
                if(countShield != 0 ){
                    countShield--;
                    holder.countTxt.setText(String.valueOf(countShield));
                    role.setValueShield(countShield);
                }
            });

            holder.addBtn.setOnClickListener(v -> {
                RoleModel role = roles.get(position);
                countShield = role.getValueShield();
                if (AndroidUtil.isMoreThanAllRoles(countWolf, countVillager, countShield, countSeer,roomModel.getNumberOfPlayer()) || countShield == 1) {
                } else {
                    countShield++;
                    holder.countTxt.setText(String.valueOf(countShield));
                    role.setValueShield(countShield);
                }
            });
        } else if (roles.get(position).getRole().equals("Seer")) {
            holder.roleImg.setImageResource(R.drawable.seer_icon);
            holder.nameRoleTxt.setText(roles.get(position).getRole());

            holder.removeBtn.setOnClickListener(v -> {
                RoleModel role = roles.get(position);
                countSeer = role.getValueSeer();
                if(countSeer != 0 ){
                    countSeer--;
                    holder.countTxt.setText(String.valueOf(countSeer));
                    role.setValueSeer(countSeer);
                }
            });

            holder.addBtn.setOnClickListener(v -> {
                RoleModel role = roles.get(position);
                countSeer = role.getValueSeer();
                if (AndroidUtil.isMoreThanAllRoles(countWolf, countVillager, countShield, countSeer,roomModel.getNumberOfPlayer()) || countSeer == 1) {
                } else {
                    countSeer++;
                    holder.countTxt.setText(String.valueOf(countSeer));
                    role.setValueSeer(countSeer);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return roles.size();
    }

    public void setRoles(ArrayList<RoleModel> roles) {
        this.roles = roles;
    }

    public int getValueWolf(){
        return countWolf;
    }
    public int getValueVillager(){
        return countVillager;
    }
    public int getValueShield(){
        return countShield;
    }
    public int getValueSeer(){
        return countSeer;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView roleImg, removeBtn, addBtn;
        private TextView nameRoleTxt, countTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roleImg = itemView.findViewById(R.id.roleImg);
            removeBtn = itemView.findViewById(R.id.removeBtn);
            addBtn = itemView.findViewById(R.id.addBtn);
            nameRoleTxt = itemView.findViewById(R.id.nameRoleTxt);
            countTxt = itemView.findViewById(R.id.countTxt);
        }
    }

}
