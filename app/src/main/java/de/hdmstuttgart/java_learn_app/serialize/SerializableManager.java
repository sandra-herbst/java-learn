package de.hdmstuttgart.java_learn_app.serialize;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import de.hdmstuttgart.java_learn_app.util.STATICS;

public class SerializableManager {

    /**
     * This method will save a users progress data.
     * @param user that is being saved.
     */
    public static void saveProgress(User user, Context context) throws IOException {

        // try block with resources closes stream automatically.
        try(FileOutputStream fos = context.openFileOutput(STATICS.userFilename, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);) {

            oos.writeObject(user);
            Log.d("SerializableManager", "saveProgress: user has been saved ..." + user);

        } catch (IOException e){
            throw new IOException();
        }
    }

    /**
     * This method deletes the fileName in src/main/resources/ directory
     */
    public static void deleteProgress(Context context) {
        context.deleteFile(STATICS.userFilename);
        Log.d("SerializableManager", "deleteProgress: user has been deleted");
    }

    /**
     * This method will load the game of a player with the given file name.
     * @return saved player object.
     * @throws IOException File doesn't exist.
     */
    public static User loadProgress(Context context) throws IOException, ClassNotFoundException {

        // try block with resources closes stream automatically.
        try(FileInputStream fis = context.openFileInput(STATICS.userFilename);
            ObjectInputStream ois = new ObjectInputStream(fis);) {

            User user = (User) ois.readObject();
            Log.d("SerializableManager", "loadProgress: The existing player state has been reloaded..." + user);
            return user;

        } catch (IOException e){
            throw new IOException();
        } catch (ClassNotFoundException e){
            throw new ClassNotFoundException();
        }
    }
}
