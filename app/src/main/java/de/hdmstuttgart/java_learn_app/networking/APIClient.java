package de.hdmstuttgart.java_learn_app.networking;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class holds the base url to server root and return the client reference
 */
public class APIClient {

    private static final String BASE_URL = "https://java-learn-app.herokuapp.com/";

    // MutableLiveData in order to observe the response livedata from the UI Controller.
    private MutableLiveData<CompilerResponse> compilerResponse;
    private ICompilerAPI compilerApi;

    public APIClient(){
        compilerResponse = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.compilerApi = retrofit.create(ICompilerAPI.class);
    }


    /**
     * send the user-written java code to the server for a compiled response.
     * @param methodType return type of the method.
     * @param compilerRequest request body of the code.
     */
    public void sendCode(MethodType methodType, CompilerRequest compilerRequest) {

        Call<CompilerResponse> call = compilerApi.getCompiledCode(methodType.ordinal(), compilerRequest);

        call.enqueue(new Callback<CompilerResponse>() {
            @Override
            public void onResponse(Call<CompilerResponse> call, Response<CompilerResponse> response) {

                // If http code means that it has failed
                if (!response.isSuccessful()){
                    Log.d("Retrofit API Call", "onResponse failure: " + call + " response: " + response.code());

                    CompilerResponse errorResponse = new CompilerResponse();
                    errorResponse.setError("Couldn't reach server, code: " + response.code());
                    compilerResponse.postValue(errorResponse);
                } else {

                    // If http code is ok it will show the result.
                    compilerResponse.postValue(response.body());
                }
            }

            // Failed to contact server
            @Override
            public void onFailure(Call<CompilerResponse> call, Throwable t) {
                Log.e("Retrofit API Call", "onResponse failed: " + call + " error: " + t.getMessage());
                CompilerResponse response = new CompilerResponse();
                response.setError("Couldn't reach server");
                compilerResponse.postValue(response);
            }
        });
    }

    public LiveData<CompilerResponse> getCompilerResponse() {
        return compilerResponse;
    }
}
