package de.hdmstuttgart.java_learn_app.database.entity;

import androidx.lifecycle.LiveData;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.List;

@Entity
public class TopicEntity {

    @PrimaryKey
    public int topicId;

    public String title;
    public String description;
    public String imageURL;

    public TopicEntity(int topicId, String title, String description, String imageURL) {
        this.topicId = topicId;
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
    }
}


