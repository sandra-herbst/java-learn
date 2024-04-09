package de.hdmstuttgart.java_learn_app.database.entity;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.List;

@Entity
public class SubtopicEntity {

    @PrimaryKey
    public int subtopicId;
    public int topicId;

    public String title;
    public String imageURL;
    public boolean isCompleted;

    public SubtopicEntity(int subtopicId, int topicId, String title, String imageURL, boolean isCompleted) {
        this.subtopicId = subtopicId;
        this.topicId = topicId;
        this.title = title;
        this.imageURL = imageURL;
        this.isCompleted = isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean getCompleted() {return isCompleted;}


}
