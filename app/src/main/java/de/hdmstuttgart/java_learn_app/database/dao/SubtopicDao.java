package de.hdmstuttgart.java_learn_app.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.hdmstuttgart.java_learn_app.database.entity.SubtopicEntity;
import de.hdmstuttgart.java_learn_app.database.entity.TopicEntity;

@Dao
public interface SubtopicDao {

    @Query("SELECT * FROM subtopicentity WHERE topicId = :topicId ORDER BY subtopicId ASC")
    LiveData<List<SubtopicEntity>> getAllSubtopicsOfTopic(int topicId);

    @Query("SELECT title FROM subtopicentity WHERE isCompleted = 1 ORDER BY subtopicId ASC")
    LiveData<List<String>> getCompletedSubtopicTitles();

    @Query("SELECT * FROM subtopicentity WHERE subtopicId = :subtopicId")
    SubtopicEntity getSubtopicBySubtopicId(int subtopicId);

    @Insert
    void insert(SubtopicEntity subtopic);

    @Delete
    void delete(SubtopicEntity topic);

    @Update
    void update(SubtopicEntity topic);
}
