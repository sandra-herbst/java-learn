package de.hdmstuttgart.java_learn_app.ui.nav_code.subtopic;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.hdmstuttgart.java_learn_app.database.ThreadExecutor;
import de.hdmstuttgart.java_learn_app.database.entity.SubtopicEntity;
import de.hdmstuttgart.java_learn_app.database.repository.SubtopicRepository;

public class SubtopicViewModel extends AndroidViewModel {

    private SubtopicRepository repository;
    private LiveData<List<SubtopicEntity>> allSubtopics;

    // Application is needed to access Database
    public SubtopicViewModel(@NonNull Application application, int topicId) {
        super(application);
        repository = new SubtopicRepository(application , new ThreadExecutor(), topicId);
        allSubtopics = repository.getAllSubtopicsOfTopic();
    }

    public void insert(SubtopicEntity subtopic){
        repository.insert(subtopic);
    }

    public void delete(SubtopicEntity subtopic){
        repository.delete(subtopic);
    }

    public void update(SubtopicEntity subtopic){
        repository.update(subtopic);
    }

    public LiveData<List<SubtopicEntity>> getAllSubtopicsOfTopic() {
        return allSubtopics;
    }
}
