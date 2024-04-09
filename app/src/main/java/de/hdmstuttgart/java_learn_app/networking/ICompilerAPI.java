package de.hdmstuttgart.java_learn_app.networking;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * REST conform endpoint to send data to server.
 */
public interface ICompilerAPI {

    @Headers({
            "Content-Type: application/json",
            "Authorization: Basic ZXNjYXBlOmphbnNs"
    })
    @POST("compile/{id}")
    Call<CompilerResponse> getCompiledCode(@Path("id") int methodId, @Body CompilerRequest body);
}
