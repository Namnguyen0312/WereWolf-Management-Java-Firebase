package code.org.werewolfmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import code.org.werewolfmanagement.R;
import code.org.werewolfmanagement.model.PlayerModel;

public class ShieldProtectedRoleRecViewAdapter extends FirestoreRecyclerAdapter<PlayerModel, ShieldProtectedRoleRecViewAdapter.PlayerModelViewHolder> {

    private Context context;
    private OnItemClickListener listener;


    public ShieldProtectedRoleRecViewAdapter(@NonNull FirestoreRecyclerOptions<PlayerModel> options, Context context, OnItemClickListener listener) {
        super(options);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull PlayerModelViewHolder holder, int position, @NonNull PlayerModel model) {
        holder.noPlayerTxt.setText(model.getNamePlayer());
        if (model.getRole().equals("Wolf")) {
            holder.roleImg.setImageResource(R.drawable.werewolf_icon);
        }
        if (model.getRole().equals("Shield")) {
            holder.roleImg.setImageResource(R.drawable.shield_icon);
        }
        if (model.getRole().equals("Villager")) {
            holder.roleImg.setImageResource(R.drawable.villager_icon);
        }

        if (model.isProtectedLastNight()) {
            holder.playerItemView.setEnabled(false);
            holder.playerItemView.setBackgroundResource(R.drawable.protected_player_rounded_corner);
        } else {
            holder.playerItemView.setEnabled(true);
            holder.playerItemView.setBackgroundResource(R.drawable.night_player_rounded_corner);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public PlayerModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.night_player_role_rec_row, parent, false);
        return new PlayerModelViewHolder(view);
    }


    public class PlayerModelViewHolder extends RecyclerView.ViewHolder {
        private TextView noPlayerTxt;
        private ImageView roleImg;

        private View playerItemView;

        public PlayerModelViewHolder(@NonNull View itemView) {
            super(itemView);
            noPlayerTxt = itemView.findViewById(R.id.noPlayerTxt);
            roleImg = itemView.findViewById(R.id.roleImg);
            playerItemView = itemView;
        }
    }
}
