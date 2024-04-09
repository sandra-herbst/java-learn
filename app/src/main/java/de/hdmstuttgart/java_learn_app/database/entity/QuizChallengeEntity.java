package de.hdmstuttgart.java_learn_app.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class QuizChallengeEntity {

    @PrimaryKey
    public int quizChallengeId;
    public int subtopicId;

    public int orderNumber;
    public String title;
    public String description;
    public int solutionPosition;
    public ArrayList<String> answers;
    public int difficulty;
    public boolean isCompleted;

    public QuizChallengeEntity(int quizChallengeId, int subtopicId, int orderNumber, String title, String description, int solutionPosition, ArrayList<String> answers, int difficulty, boolean isCompleted) {
        this.quizChallengeId = quizChallengeId;
        this.subtopicId = subtopicId;
        this.orderNumber = orderNumber;
        this.title = title;
        this.description = description;
        this.solutionPosition = solutionPosition;
        this.answers = answers;
        this.difficulty = difficulty;
        this.isCompleted = isCompleted;
    }

    @Override
    public String toString() {
        return "QuizChallengeEntity{" +
                "quizChallengeId=" + quizChallengeId +
                ", title='" + title + '\'' +
                ", isCompleted=" + isCompleted +
                "}\n";
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
