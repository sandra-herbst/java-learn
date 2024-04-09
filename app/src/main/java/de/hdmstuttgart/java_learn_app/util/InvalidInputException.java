package de.hdmstuttgart.java_learn_app.util;

import android.util.Log;

public class InvalidInputException extends Exception {

    public InvalidInputException(String msg) {
        super(msg);
        Log.e("EXCEPTION", "InvalidInputException: " + msg);
    }
}
