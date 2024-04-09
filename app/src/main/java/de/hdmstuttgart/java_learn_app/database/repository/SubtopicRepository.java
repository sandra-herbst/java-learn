package de.hdmstuttgart.java_learn_app.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;

import de.hdmstuttgart.java_learn_app.database.ChallengeDatabase;
import de.hdmstuttgart.java_learn_app.database.dao.SubtopicDao;
import de.hdmstuttgart.java_learn_app.database.entity.SubtopicEntity;

public class SubtopicRepository {

    // Background Thread
    private final Executor executor;
    private SubtopicDao subtopicDao;

    private LiveData<List<SubtopicEntity>> allSubtopics;
    private LiveData<List<String>> completedSubtopicTitles;


    public SubtopicRepository(Application application, Executor executor){
        this.executor = executor;
        ChallengeDatabase database = ChallengeDatabase.getDBInstance(application);
        subtopicDao = database.subtopicDao();

        completedSubtopicTitles = subtopicDao.getCompletedSubtopicTitles();
    };

    public SubtopicRepository(Application application, Executor executor, int topicId){
        this.executor = executor;
        ChallengeDatabase database = ChallengeDatabase.getDBInstance(application);
        subtopicDao = database.subtopicDao();

        allSubtopics = subtopicDao.getAllSubtopicsOfTopic(topicId);
        completedSubtopicTitles = subtopicDao.getCompletedSubtopicTitles();
    }

    public void insert(SubtopicEntity subtopic){
        executor.execute(() -> subtopicDao.insert(subtopic));
    }

    public void delete(SubtopicEntity subtopic){
        executor.execute(() -> subtopicDao.delete(subtopic));
    }

    public void update(SubtopicEntity subtopic){
        executor.execute(() -> subtopicDao.update(subtopic));
    }

    public LiveData<List<SubtopicEntity>> getAllSubtopicsOfTopic() {
        return allSubtopics;
    }

    public LiveData<List<String>> getCompletedSubtopicTitles() {
        return completedSubtopicTitles;
    }

}
