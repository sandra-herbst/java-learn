package de.hdmstuttgart.java_learn_app.ui.challenge.quiz_challenge;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdmstuttgart.java_learn_app.R;

public class QuizChallengeAdapter extends RecyclerView.Adapter<QuizChallengeAdapter.AnswersViewHolder> {

    private List<String> answers;
    private CheckBox lastCheckedItem = null;
    private int solutionPosition;
    private int checkedPosition = -1;

    public QuizChallengeAdapter(List<String> answers, int solutionPosition) {
        this.answers = answers;
        this.solutionPosition = solutionPosition;
    }

    @NonNull
    @Override
    public AnswersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AnswersViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AnswersViewHolder holder, int position) {
        String answer = answers.get(position);

        holder.quiz_answerText.setText(answer);

        // Only one checkbox at a time can be selected
        holder.quiz_checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                if (lastCheckedItem != null) lastCheckedItem.setChecked(false);
                lastCheckedItem = (CheckBox) buttonView;

                checkedPosition = position;

            } else {
                lastCheckedItem = null;
                checkedPosition = -1;
            }
        });
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public boolean hasSolutionChecked(){
        return solutionPosition == checkedPosition;
    }

    public int getCheckedPosition() {
        return checkedPosition;
    }

    public class AnswersViewHolder extends RecyclerView.ViewHolder{

        public ConstraintLayout quiz_layout_image;
        public TextView quiz_answerText;
        public CheckBox quiz_checkBox;

        public AnswersViewHolder(@NonNull View itemView) {
            super(itemView);

            quiz_layout_image = itemView.findViewById(R.id.quiz_layout_image);
            quiz_answerText = itemView.findViewById(R.id.quiz_answerText);
            quiz_checkBox = itemView.findViewById(R.id.quiz_checkBox);
        }
    }
}
