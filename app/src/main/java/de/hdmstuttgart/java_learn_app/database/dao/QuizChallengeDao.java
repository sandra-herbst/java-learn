package de.hdmstuttgart.java_learn_app.database.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.LinkedList;
import java.util.List;

import de.hdmstuttgart.java_learn_app.database.entity.CodeChallengeEntity;
import de.hdmstuttgart.java_learn_app.database.entity.QuizChallengeEntity;
import de.hdmstuttgart.java_learn_app.database.entity.SubtopicEntity;

@Dao
public interface QuizChallengeDao {

    @Query("SELECT * FROM quizchallengeentity WHERE subtopicId = :subtopicId ORDER BY orderNumber ASC")
    List<QuizChallengeEntity> getAllQuizChallengesOfSubtopic(int subtopicId);

    @Query("SELECT COUNT(quizChallengeId) FROM quizchallengeentity WHERE isCompleted = 1 AND subtopicId = :subtopicId")
    LiveData<Integer> getCompletedQuizCountOfSubtopic(int subtopicId);

    @Insert
    void insert(QuizChallengeEntity quizChallenge);

    @Delete
    void delete(QuizChallengeEntity quizChallenge);

    @Update
    void update(QuizChallengeEntity quizChallenge);
}
