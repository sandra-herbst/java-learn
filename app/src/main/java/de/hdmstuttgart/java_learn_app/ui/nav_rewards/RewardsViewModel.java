package de.hdmstuttgart.java_learn_app.ui.nav_rewards;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.hdmstuttgart.java_learn_app.database.ThreadExecutor;
import de.hdmstuttgart.java_learn_app.database.entity.RewardEntity;
import de.hdmstuttgart.java_learn_app.database.repository.RewardsRepository;

public class RewardsViewModel extends AndroidViewModel {

    private RewardsRepository repository;
    private LiveData<List<RewardEntity>> allRewards;

    public RewardsViewModel(@NonNull Application application) {
        super(application);
        repository = new RewardsRepository(new ThreadExecutor(), application);
        allRewards = repository.getAllRewards();
    }


    public void insert(RewardEntity reward){
        repository.insert(reward);
    }

    public void delete(RewardEntity reward){
        repository.delete(reward);
    }

    public void update(RewardEntity reward){
        repository.update(reward);
    }

    public LiveData<List<RewardEntity>> getAllRewards() {
        return allRewards;
    }
}
