package de.hdmstuttgart.java_learn_app.ui.registration;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.List;

import de.hdmstuttgart.java_learn_app.database.ThreadExecutor;
import de.hdmstuttgart.java_learn_app.database.entity.SubtopicEntity;
import de.hdmstuttgart.java_learn_app.database.repository.SubtopicRepository;
import de.hdmstuttgart.java_learn_app.serialize.SerializableManager;
import de.hdmstuttgart.java_learn_app.serialize.User;
import de.hdmstuttgart.java_learn_app.serialize.UserHandler;
import de.hdmstuttgart.java_learn_app.util.InvalidInputException;

public class UserViewModel extends AndroidViewModel {

    private static final String TAG = "UserViewModel";
    private User user;
    private MutableLiveData<String> usernameData;
    private MutableLiveData<Integer> userCoinData;
    private MutableLiveData<Integer> userExpData;

    public UserViewModel(@NonNull Application application) {
        super(application);
        this.user = UserHandler.getInstance(application).getUser();

        usernameData = new MutableLiveData<>(user.getUsername());
        userCoinData = new MutableLiveData<>(user.getCoins());
        userExpData = new MutableLiveData<>(user.getExperience());

        Log.d(TAG, "Constructor: " + user);
    }

    public void setUsername(String username) throws InvalidInputException {
        if (validateUsername(username)){
            Log.d(TAG, "setUsername: " + username);
            usernameData.postValue(username);
            user.setUsername(username);
            saveUser();
        } else {
            throw new InvalidInputException("Input is too short. Username has to have at least 3 and not more than 15 letters.");
        }
    }

    public LiveData<String> getUsernameLiveData() {
        return usernameData;
    }

    public LiveData<Integer> getUserCoinLiveData() {
        return userCoinData;
    }

    public LiveData<Integer> getUserExpLiveData() {
        return userExpData;
    }

    public void removeCoins(int coins){
        userCoinData.postValue(userCoinData.getValue() - coins);
        user.removeCoins(coins);
        saveUser();
    }

    public void addCoins(int coins){
        userCoinData.postValue(userCoinData.getValue() + coins);
        user.addCoins(coins);
        saveUser();
    }

    public void addExperience(int exp){
        userExpData.postValue(userExpData.getValue() + exp);
        user.addExperience(exp);
        saveUser();
    }

    public int getCoins(){
        return user.getCoins();
    }

    private void saveUser() {
        try {
            SerializableManager.saveProgress(user, getApplication());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteUser() {
        try {
            SerializableManager.deleteProgress(getApplication());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean validateUsername(String username){
        return username.length() >= 3 && username.length() <= 15;
    }
}
