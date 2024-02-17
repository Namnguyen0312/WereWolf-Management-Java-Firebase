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

public class DeadPlayerRoleRecViewAdapter extends FirestoreRecyclerAdapter<PlayerModel, DeadPlayerRoleRecViewAdapter.PlayerModelViewHolder> {

    private Context context;
    private OnItemClickListener listener;

    public DeadPlayerRoleRecViewAdapter(@NonNull FirestoreRecyclerOptions<PlayerModel> options, Context context, OnItemClickListener listener) {
        super(options);
        this.context = context;
        this.listener = listener;
    }

    public DeadPlayerRoleRecViewAdapter(@NonNull FirestoreRecyclerOptions<PlayerModel> options, Context context) {
        super(options);
        this.context = context;
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
        View view = LayoutInflater.from(context).inflate(R.layout.dead_player_role_rec_row, parent, false);
        return new PlayerModelViewHolder(view);
    }

    public class PlayerModelViewHolder extends RecyclerView.ViewHolder {
        private TextView noPlayerTxt;
        private ImageView roleImg;

        public PlayerModelViewHolder(@NonNull View itemView) {
            super(itemView);
            noPlayerTxt = itemView.findViewById(R.id.noPlayerTxt);
            roleImg = itemView.findViewById(R.id.roleImg);

        }
    }
}
