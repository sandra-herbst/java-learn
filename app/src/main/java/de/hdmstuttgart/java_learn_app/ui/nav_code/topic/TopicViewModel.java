package de.hdmstuttgart.java_learn_app.ui.nav_code.topic;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.hdmstuttgart.java_learn_app.database.ThreadExecutor;
import de.hdmstuttgart.java_learn_app.database.entity.TopicEntity;
import de.hdmstuttgart.java_learn_app.database.repository.TopicRepository;

public class TopicViewModel extends AndroidViewModel {

    private TopicRepository repository;
    private LiveData<List<TopicEntity>> allTopics;

    // Application is needed to access Database
    public TopicViewModel(@NonNull Application application) {
        super(application);
        repository = new TopicRepository(application, new ThreadExecutor());
        allTopics = repository.getAllTopics();
    }

    public void insert(TopicEntity topic){
        repository.insert(topic);
    }

    public void delete(TopicEntity topic){
        repository.delete(topic);
    }

    public void update(TopicEntity topic){
        repository.update(topic);
    }

    public LiveData<List<TopicEntity>> getAllTopics() {
        return allTopics;
    }
}
