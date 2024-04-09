package de.hdmstuttgart.java_learn_app.networking;

/**
 * holds the schema for the request body
 */
public class CompilerRequest {

    public String code, methodName;

    public CompilerRequest(String code, String methodName) {
        this.code = code;
        this.methodName = methodName;
    }
}
