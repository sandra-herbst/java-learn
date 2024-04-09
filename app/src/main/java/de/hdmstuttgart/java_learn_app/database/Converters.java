package de.hdmstuttgart.java_learn_app.database;

import android.util.Log;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * This converter takes care of java objects that are not primitive types.
 * For example, it can save an Arraylist into the database by parsing it to the JSON format and back.
 *
 * The converter is being used automatically by the room database.
 */
public class Converters {

    /**
     * This method takes the json representation of an arraylist back to an actual arraylist.
     * @param value JSON of room database
     * @return Arraylist converted from the JSON.
     */
    @TypeConverter
    public static ArrayList<String> fromString(String value){
        Type listType = new TypeToken<ArrayList<String>>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    /**
     * This method takes an arraylist as parameter and returns the json string representation
     * for the room database.
     * @param list arraylist to convert for DB.
     * @return JSON equivalent of arraylist.
     */
    @TypeConverter
    public static String fromArrayList(ArrayList<String> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
