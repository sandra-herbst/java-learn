package de.hdmstuttgart.java_learn_app.ui.nav_code.subtopic;

import android.app.AlertDialog;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdmstuttgart.java_learn_app.R;
import de.hdmstuttgart.java_learn_app.database.entity.SubtopicEntity;

public class SubtopicAdapter extends RecyclerView.Adapter<SubtopicAdapter.SubtopicViewHolder> {

    private List<SubtopicEntity> subtopics = new ArrayList<>();
    private SubtopicViewModel subtopicViewModel;

    public SubtopicAdapter(SubtopicViewModel subtopicViewModel) {
        this.subtopicViewModel = subtopicViewModel;
    }

    @NonNull
    @Override
    public SubtopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SubtopicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.subtopic_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SubtopicViewHolder holder, int position) {
        SubtopicEntity subtopic = subtopics.get(position);
        holder.subtopic_titleText.setText(subtopic.title);

        if(subtopic.isCompleted) {
            holder.subtopic_image.setBackgroundResource(R.color.finished_section);
            holder.star_img.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(v -> {
            if(subtopic.isCompleted) {
                new AlertDialog.Builder(holder.itemView.getContext())
                        .setTitle(R.string.submit)
                        .setMessage(R.string.repeatCompletedSubtopic)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, (dialog,which) -> {
                            Navigation.findNavController(holder.itemView).navigate(SubtopicFragmentDirections.actionSubtopicFragmentToChallengeGraph(subtopics.get(position).subtopicId));
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            } else {
                Navigation.findNavController(holder.itemView).navigate(SubtopicFragmentDirections.actionSubtopicFragmentToChallengeGraph(subtopics.get(position).subtopicId));
            }
        });
    }

    @Override
    public int getItemCount() {
        return subtopics.size();
    }

    public void setSubtopics(List<SubtopicEntity> subtopics){
        this.subtopics = subtopics;
        notifyDataSetChanged();
    }

    public static class SubtopicViewHolder extends RecyclerView.ViewHolder{

        public ConstraintLayout subtopic_image;
        public TextView subtopic_titleText;
        private ImageView star_img;

        public SubtopicViewHolder(@NonNull View itemView) {
            super(itemView);

            subtopic_image = itemView.findViewById(R.id.subtopic_image);
            subtopic_titleText = itemView.findViewById(R.id.subtopic_titleText);
            star_img = itemView.findViewById(R.id.subtopic_star);
        }
    }
}
