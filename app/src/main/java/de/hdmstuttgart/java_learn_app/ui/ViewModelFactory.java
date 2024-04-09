package de.hdmstuttgart.java_learn_app.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import de.hdmstuttgart.java_learn_app.ui.challenge.ChallengeViewModel;
import de.hdmstuttgart.java_learn_app.ui.nav_code.subtopic.SubtopicViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private int databaseId;
    private Application application;

    public ViewModelFactory(int databaseId, Application application) {
        this.databaseId = databaseId;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ChallengeViewModel.class)){
            return (T) new ChallengeViewModel(application, databaseId);
        }
        else if (modelClass.isAssignableFrom(SubtopicViewModel.class))
            return (T) new SubtopicViewModel(application, databaseId);
        return null;
    }
}
