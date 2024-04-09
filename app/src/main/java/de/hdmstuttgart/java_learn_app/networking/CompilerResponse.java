package de.hdmstuttgart.java_learn_app.networking;

/**
 * holds the schema for the response body
 */
public class CompilerResponse {

    public String result, error;

    public String getResult() {
        return result;
    }

    public String getError() {
        return error;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "CompilerResponse{" +
                "result='" + result + '\'' +
                ", error='" + error + '\'' +
                '}';
    }

}
