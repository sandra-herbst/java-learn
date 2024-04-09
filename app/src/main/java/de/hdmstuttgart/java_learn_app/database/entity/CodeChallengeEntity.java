package de.hdmstuttgart.java_learn_app.database.entity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CodeChallengeEntity {

        @PrimaryKey
        public int codeChallengeId;
        public int subtopicId;

        public int orderNumber;
        public String title;
        public String description;
        public String solution;
        public String methodName;
        public String methodType; //evtl enum
        public String accessModifier; // evtl enum
        public int difficulty;
        public boolean isCompleted;

        public CodeChallengeEntity(int codeChallengeId, int subtopicId, int orderNumber, String title, String description, String solution, String methodName, String methodType, String accessModifier, int difficulty, boolean isCompleted) {
                this.codeChallengeId = codeChallengeId;
                this.subtopicId = subtopicId;
                this.orderNumber = orderNumber;
                this.title = title;
                this.description = description;
                this.solution = solution;
                this.methodName = methodName;
                this.methodType = methodType;
                this.accessModifier = accessModifier;
                this.difficulty = difficulty;
                this.isCompleted = isCompleted;
        }

        @Override
        public String toString() {
                return "CodeChallengeEntity{" +
                        "codeChallengeId=" + codeChallengeId +
                        ", title='" + title + '\'' +
                        ", isCompleted=" + isCompleted +
                        "}\n";
        }

        public void setCompleted(boolean completed) {
                isCompleted = completed;
        }
}
