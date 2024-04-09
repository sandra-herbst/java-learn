package de.hdmstuttgart.java_learn_app.ui.nav_profile;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import de.hdmstuttgart.java_learn_app.R;


public class FinishedChallengeAdapter extends RecyclerView.Adapter<FinishedChallengeAdapter.FinishedSubtopicViewHolder>{

    private List<String> subtopics = new ArrayList<>();

    @NonNull
    @Override
    public FinishedChallengeAdapter.FinishedSubtopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FinishedSubtopicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.subtopic_finished_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FinishedSubtopicViewHolder holder, int position) {
        String subtopic = subtopics.get(position);
        holder.subtopic_titleText.setText(subtopic);
    }


    @Override
    public int getItemCount() {
        return subtopics.size();
    }

    public void setCompletedSubtopics(List<String> subtopics){
        this.subtopics = subtopics;
        notifyDataSetChanged();
    }

    public static class FinishedSubtopicViewHolder extends RecyclerView.ViewHolder{

        public TextView subtopic_titleText;

        public FinishedSubtopicViewHolder(@NonNull View itemView) {
            super(itemView);
            subtopic_titleText = itemView.findViewById(R.id.topic_titleText);
        }
    }
}
