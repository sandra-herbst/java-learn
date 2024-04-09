package de.hdmstuttgart.java_learn_app.networking;

import androidx.lifecycle.LiveData;

public class CompilerAPIRepository {

    private APIClient apiClient;
    private LiveData<CompilerResponse> compilerResponseLiveData;

    public CompilerAPIRepository(APIClient apiClient) {
        this.apiClient = apiClient;
        this.compilerResponseLiveData = apiClient.getCompilerResponse();
    }

    public void sendCode(MethodType methodType, CompilerRequest compilerRequest){
        apiClient.sendCode(methodType, compilerRequest);
    }

    public LiveData<CompilerResponse> getCompilerResponseLiveData() {
        return compilerResponseLiveData;
    }
}
