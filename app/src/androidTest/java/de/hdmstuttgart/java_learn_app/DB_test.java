package de.hdmstuttgart.java_learn_app;
import android.os.Debug;
import android.util.Log;

import androidx.arch.core.executor.TaskExecutor;
import androidx.lifecycle.LiveData;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import de.hdmstuttgart.java_learn_app.database.ThreadExecutor;
import de.hdmstuttgart.java_learn_app.database.entity.CodeChallengeEntity;
import de.hdmstuttgart.java_learn_app.database.entity.QuizChallengeEntity;
import de.hdmstuttgart.java_learn_app.database.repository.ChallengeRepository;
import de.hdmstuttgart.java_learn_app.ui.MainActivity;

@RunWith(AndroidJUnit4.class)

public class DB_test {

    private ChallengeRepository challengeRepository;

    @Rule
    public ActivityScenarioRule<MainActivity> ar = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public  void getRepository(){
        ar.getScenario().onActivity(activity -> {
            challengeRepository = new ChallengeRepository(activity.getApplication(), new ThreadExecutor(), 0);
        });
    }

    @Test
    public void isCorrectIdQuizChallenge(){
        List<QuizChallengeEntity> quizChallenges = challengeRepository.getAllQuizChallengesOfSubtopic();

        Assert.assertTrue(idCheck(0, quizChallenges.get(0).quizChallengeId, 0, quizChallenges.get(0).subtopicId));
        Assert.assertTrue(idCheck(1, quizChallenges.get(1).quizChallengeId, 0, quizChallenges.get(1).subtopicId));

        Assert.assertFalse(idCheck(1, quizChallenges.get(1).quizChallengeId, 1, quizChallenges.get(1).subtopicId));

    }

    @Test
    public void isCorrectIdCodeChallenge(){
        List<CodeChallengeEntity> codeChallenges = challengeRepository.getAllCodeChallengesOfSubtopic();

        Assert.assertTrue(idCheck(0, codeChallenges.get(0).codeChallengeId, 0, codeChallenges.get(0).subtopicId));
        Assert.assertTrue(idCheck(1, codeChallenges.get(1).codeChallengeId, 0, codeChallenges.get(1).subtopicId));
        Assert.assertTrue(idCheck(2, codeChallenges.get(2).codeChallengeId, 0, codeChallenges.get(2).subtopicId));

        Assert.assertFalse(idCheck(2, codeChallenges.get(2).codeChallengeId, 4, codeChallenges.get(2).subtopicId));

    }

    private boolean idCheck(int expected1, int given1, int expected2, int given2){
        return expected1 == given1 && expected2 == given2;
    }
}
