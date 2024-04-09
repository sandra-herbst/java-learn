package de.hdmstuttgart.java_learn_app.serialize;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import de.hdmstuttgart.java_learn_app.ui.MainActivity;

public class UserHandler {

    private User user;
    private static UserHandler INSTANCE;

    public static UserHandler getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = new UserHandler();
            INSTANCE.generateUser(context);
        }

        return INSTANCE;
    }

    /**
     * This method will create a user through the SerializableManager.
     * If the user has been saved once, their progress file will be reloaded respectively.
     * @return either an already existing user or a new one.
     */
    private void generateUser(Context context){

        try {

            user = SerializableManager.loadProgress(context);

        } catch (IOException | NullPointerException e){

            // No player data has been found, so a new player is being returned
            user = new User();

        } catch (ClassNotFoundException e){
            Log.d("UserFactory", "Failed to retrieve user, Class User does not exist.");
            e.printStackTrace();

        } catch (Exception e){
            Log.d("UserFactory", "Failed to retrieve user.");
            e.printStackTrace();

        }
    }

    public User getUser() {
        return user;
    }
}
