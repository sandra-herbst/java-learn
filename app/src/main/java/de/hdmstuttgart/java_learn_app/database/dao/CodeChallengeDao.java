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
public interface CodeChallengeDao {

    @Query("SELECT * FROM codechallengeentity WHERE subtopicId = :subtopicId ORDER BY orderNumber ASC")
    List<CodeChallengeEntity> getAllCodeChallengesOfSubtopic(int subtopicId);

    @Query("SELECT COUNT(codeChallengeId) FROM codechallengeentity WHERE isCompleted = 1 AND subtopicId = :subtopicId")
    LiveData<Integer> getCompletedCodeCountOfSubtopic(int subtopicId);

    @Insert
    void insert(CodeChallengeEntity codeChallenge);

    @Delete
    void delete(CodeChallengeEntity codeChallenge);

    @Update
    void update(CodeChallengeEntity codeChallenge);
}
