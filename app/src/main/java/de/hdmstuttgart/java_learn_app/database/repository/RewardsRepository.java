package de.hdmstuttgart.java_learn_app.database.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;

import de.hdmstuttgart.java_learn_app.database.ChallengeDatabase;
import de.hdmstuttgart.java_learn_app.database.dao.RewardDao;
import de.hdmstuttgart.java_learn_app.database.dao.TopicDao;
import de.hdmstuttgart.java_learn_app.database.entity.RewardEntity;
import de.hdmstuttgart.java_learn_app.database.entity.TopicEntity;

public class RewardsRepository {

    // Background Thread
    private final Executor executor;
    private RewardDao rewardDao;
    private LiveData<List<RewardEntity>> allRewards;

    public RewardsRepository(Executor executor, Application application) {
        this.executor = executor;

        ChallengeDatabase database = ChallengeDatabase.getDBInstance(application);
        rewardDao = database.rewardDao();
        allRewards = rewardDao.getAllRewards();
    }

    public void insert(RewardEntity reward){
        executor.execute(() -> rewardDao.insert(reward));
    }

    public void delete(RewardEntity reward){
        executor.execute(() -> rewardDao.delete(reward));
    }

    public void update(RewardEntity reward){
        executor.execute(() -> rewardDao.update(reward));
    }

    public LiveData<List<RewardEntity>> getAllRewards() {
        return allRewards;
    }
}
