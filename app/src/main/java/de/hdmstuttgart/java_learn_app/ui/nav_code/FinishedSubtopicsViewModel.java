package de.hdmstuttgart.java_learn_app.ui.nav_code;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import de.hdmstuttgart.java_learn_app.database.ThreadExecutor;
import de.hdmstuttgart.java_learn_app.database.repository.SubtopicRepository;

public class FinishedSubtopicsViewModel extends AndroidViewModel {

    private SubtopicRepository repository;
    private LiveData<List<String>> completedSubtopicTitles;

    // Application is needed to access Database
    public FinishedSubtopicsViewModel(@NonNull Application application) {
        super(application);
        repository = new SubtopicRepository(application , new ThreadExecutor());
        completedSubtopicTitles = repository.getCompletedSubtopicTitles();
    }

    public LiveData<List<String>> getCompletedSubtopicTitles()  { return completedSubtopicTitles; }
}
