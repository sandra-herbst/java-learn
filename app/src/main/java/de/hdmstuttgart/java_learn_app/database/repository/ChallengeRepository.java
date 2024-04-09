package de.hdmstuttgart.java_learn_app.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

import de.hdmstuttgart.java_learn_app.database.ChallengeDatabase;
import de.hdmstuttgart.java_learn_app.database.dao.CodeChallengeDao;
import de.hdmstuttgart.java_learn_app.database.dao.QuizChallengeDao;
import de.hdmstuttgart.java_learn_app.database.dao.SubtopicDao;
import de.hdmstuttgart.java_learn_app.database.entity.CodeChallengeEntity;
import de.hdmstuttgart.java_learn_app.database.entity.QuizChallengeEntity;
import de.hdmstuttgart.java_learn_app.database.entity.SubtopicEntity;

public class ChallengeRepository {

    // Background Thread
    private final Executor executor;
    private QuizChallengeDao quizChallengeDao;
    private CodeChallengeDao codeChallengeDao;
    private SubtopicDao subtopicDao;
    private List<CodeChallengeEntity> allCodeChallenges;
    private List<QuizChallengeEntity> allQuizChallenges;
    private SubtopicEntity subtopicOfChallenges;

    public ChallengeRepository(Application application, Executor executor, int subtopicId){
        this.executor = executor;

        ChallengeDatabase database = ChallengeDatabase.getDBInstance(application);
        quizChallengeDao = database.quizChallengeDao();
        codeChallengeDao = database.codeChallengeDao();
        subtopicDao = database.subtopicDao();

        subtopicOfChallenges = subtopicDao.getSubtopicBySubtopicId(subtopicId);
        allCodeChallenges = codeChallengeDao.getAllCodeChallengesOfSubtopic(subtopicId);
        allQuizChallenges = quizChallengeDao.getAllQuizChallengesOfSubtopic(subtopicId);
    }

    public void updateQuizChallenge(QuizChallengeEntity quizChallenge){
        executor.execute(() -> quizChallengeDao.update(quizChallenge));
    }

    public void updateCodeChallenge(CodeChallengeEntity codeChallenge){
        executor.execute(() -> codeChallengeDao.update(codeChallenge));
    }

    public void updateSubtopic(SubtopicEntity subtopicEntity){
        executor.execute(() -> subtopicDao.update(subtopicEntity));
    }

    public SubtopicEntity getSubtopicOfChallenges() {
        return subtopicOfChallenges;
    }

    public List<CodeChallengeEntity> getAllCodeChallengesOfSubtopic() {
        return allCodeChallenges;
    }

    public List<QuizChallengeEntity> getAllQuizChallengesOfSubtopic() {
        return allQuizChallenges;
    }


}
