package de.hdmstuttgart.java_learn_app.ui.nav_rewards;

import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdmstuttgart.java_learn_app.R;
import de.hdmstuttgart.java_learn_app.database.entity.RewardEntity;
import de.hdmstuttgart.java_learn_app.ui.registration.UserViewModel;

public class RewardsAdapter extends RecyclerView.Adapter<RewardsAdapter.RewardsViewHolder> {

    private List<RewardEntity> rewards = new ArrayList<>();
    private UserViewModel userViewModel;
    private RewardsViewModel rewardsViewModel;
    private static final String TAG = "RewardsAdapter";

    public RewardsAdapter(UserViewModel userViewModel, RewardsViewModel rewardsViewModel) {
        this.userViewModel = userViewModel;
        this.rewardsViewModel = rewardsViewModel;
    }

    @NonNull
    @Override
    public RewardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RewardsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RewardsViewHolder holder, int position) {
        RewardEntity reward = rewards.get(position);
        holder.desc.setText(reward.description);
        holder.cost.setText(String.valueOf(reward.cost));
        holder.title.setText(reward.title);
        //holder.img.setImageURI(Uri.parse(reward.imageURL));

        if (!reward.hasBought){

            holder.itemView.setOnClickListener(v -> {

                if (userViewModel.getCoins() >= reward.cost){
                    new AlertDialog.Builder(holder.itemView.getContext())
                            .setTitle("Confirmation")
                            .setMessage("Do you really want to buy " + reward.title + " ?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                Log.d(TAG, "onBindViewHolder: change reward style" );
                                reward.hasBought = true;
                                rewardsViewModel.update(reward);
                                userViewModel.removeCoins(reward.cost);
                                //holder.reward_background.setBackgroundResource(R.color.finished_section);
                                Toast.makeText(holder.itemView.getContext(), "You've bought " + reward.title + "!", Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                } else {
                    Toast.makeText(holder.itemView.getContext(), "You don`t have enough coins to buy this", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            holder.reward_background.setBackgroundResource(R.color.finished_section);
        }

    }

    @Override
    public int getItemCount() {
        return rewards.size();
    }

    public void setRewards(List<RewardEntity> rewards){
        this.rewards = rewards;
        notifyDataSetChanged();
    }

    public static class RewardsViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView desc;
        public TextView cost;
        public ImageView img;
        public ConstraintLayout reward_background;

        public RewardsViewHolder(@NonNull View itemView) {
            super(itemView);

            desc = itemView.findViewById(R.id.reward_desc);
            title = itemView.findViewById(R.id.reward_title);
            cost = itemView.findViewById(R.id.reward_cost);
            img = itemView.findViewById(R.id.reward_image);
            reward_background = itemView.findViewById(R.id.reward_background);
        }
    }
}
