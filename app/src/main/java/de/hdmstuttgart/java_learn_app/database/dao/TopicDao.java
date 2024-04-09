package de.hdmstuttgart.java_learn_app.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.hdmstuttgart.java_learn_app.database.entity.TopicEntity;

@Dao
public interface TopicDao {

    @Query("SELECT * FROM TopicEntity ORDER BY topicId ASC")
    LiveData<List<TopicEntity>> getAllTopics();

    @Query("DELETE FROM TopicEntity")
    void deleteAllTopics();

    @Insert
    void insert(TopicEntity topic);

    @Delete
    void delete(TopicEntity topic);

    @Update
    void update(TopicEntity topic);
}
