package code.org.werewolfmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import code.org.werewolfmanagement.R;
import code.org.werewolfmanagement.model.PlayerModel;

public class WolfPlayerRecViewAdapter extends FirestoreRecyclerAdapter<PlayerModel, WolfPlayerRecViewAdapter.PlayerModelViewHolder> {

    private Context context;

    public WolfPlayerRecViewAdapter(@NonNull FirestoreRecyclerOptions<PlayerModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull PlayerModelViewHolder holder, int position, @NonNull PlayerModel model) {
        holder.noPlayerTxt.setText("Player " + model.getPlayerId());
    }

    @NonNull
    @Override
    public PlayerModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wolf_player_rec_row, parent, false);
        return new PlayerModelViewHolder(view);
    }

    public class PlayerModelViewHolder extends RecyclerView.ViewHolder{
        private TextView noPlayerTxt;

        public PlayerModelViewHolder(@NonNull View itemView) {
            super(itemView);
            noPlayerTxt = itemView.findViewById(R.id.noPlayerTxt);
        }
    }
}
