package de.hdmstuttgart.java_learn_app.ui.challenge.code_challenge;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import de.hdmstuttgart.java_learn_app.networking.APIClient;
import de.hdmstuttgart.java_learn_app.networking.CompilerAPIRepository;
import de.hdmstuttgart.java_learn_app.networking.CompilerRequest;
import de.hdmstuttgart.java_learn_app.networking.CompilerResponse;
import de.hdmstuttgart.java_learn_app.networking.MethodType;

public class CodeChallengeViewModel extends AndroidViewModel {

    private CompilerAPIRepository repository;
    private LiveData<CompilerResponse> compilerResponseLiveData;

    public CodeChallengeViewModel(@NonNull Application application) {
        super(application);
        repository = new CompilerAPIRepository(new APIClient());
        compilerResponseLiveData = repository.getCompilerResponseLiveData();
    }

    public void sendCode(MethodType methodType, CompilerRequest compilerRequest){
        repository.sendCode(methodType, compilerRequest);
    }

    public LiveData<CompilerResponse> getCompilerResponseLiveData() {
        return compilerResponseLiveData;
    }
}
