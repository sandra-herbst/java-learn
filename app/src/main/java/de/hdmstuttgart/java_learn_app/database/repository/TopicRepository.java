package de.hdmstuttgart.java_learn_app.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;

import de.hdmstuttgart.java_learn_app.database.ChallengeDatabase;
import de.hdmstuttgart.java_learn_app.database.dao.TopicDao;
import de.hdmstuttgart.java_learn_app.database.entity.TopicEntity;

public class TopicRepository {

    // Background Thread
    private final Executor executor;
    private TopicDao topicDao;
    private LiveData<List<TopicEntity>> allTopics;

    public TopicRepository(Application application, Executor executor){
        this.executor = executor;

        ChallengeDatabase database = ChallengeDatabase.getDBInstance(application);
        topicDao = database.topicDao();
        allTopics = topicDao.getAllTopics();
    }

    public void insert(TopicEntity topic){
        executor.execute(() -> topicDao.insert(topic));
    }

    public void delete(TopicEntity topic){
        executor.execute(() -> topicDao.delete(topic));
    }

    public void update(TopicEntity topic){
        executor.execute(() -> topicDao.update(topic));
    }

    public LiveData<List<TopicEntity>> getAllTopics() {
        return allTopics;
    }
}
