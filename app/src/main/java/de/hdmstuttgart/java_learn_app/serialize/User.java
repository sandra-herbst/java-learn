package de.hdmstuttgart.java_learn_app.serialize;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Serializable User Data that can be saved on the systems device.
 */
public class User implements Serializable {

    private String username;
    private int coins;
    private int experience;
    private int profileImgId;

    public User() {
        this.coins = 0;
        this.experience = 0;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addCoins(int coins) {
        this.coins += coins;
    }

    public void removeCoins(int coins) {
        this.coins -= coins;
    }

    public void addExperience(int experience) {
        this.experience += experience;
    }

    public String getUsername() {
        return username;
    }

    public int getCoins() {
        return coins;
    }

    public int getExperience() {
        return experience;
    }

    public int getProfileImgId() {
        return profileImgId;
    }

    public void setProfileImgId(int profileImgId) {
        this.profileImgId = profileImgId;
    }

    @Override
    public String toString() {
        return  "Username: " + username +
                ", Coins: " + coins +
                ", Experience: " + experience;
    }
}
