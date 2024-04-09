package de.hdmstuttgart.java_learn_app.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.hdmstuttgart.java_learn_app.database.entity.RewardEntity;
import de.hdmstuttgart.java_learn_app.database.entity.SubtopicEntity;
import de.hdmstuttgart.java_learn_app.database.entity.TopicEntity;

@Dao
public interface RewardDao {

    @Query("SELECT * FROM RewardEntity ORDER BY rewardId ASC")
    LiveData<List<RewardEntity>> getAllRewards();

    @Insert
    void insert(RewardEntity reward);

    @Delete
    void delete(RewardEntity reward);

    @Update
    void update(RewardEntity reward);
}
