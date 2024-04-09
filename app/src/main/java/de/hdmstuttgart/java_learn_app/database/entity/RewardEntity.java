package de.hdmstuttgart.java_learn_app.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RewardEntity {

    @PrimaryKey
    public int rewardId;

    public String title;
    public String imageURL;
    public String description;
    public int cost;
    public boolean hasBought;

    public RewardEntity(int rewardId, String title, String imageURL, String description, int cost, boolean hasBought) {
        this.rewardId = rewardId;
        this.title = title;
        this.imageURL = imageURL;
        this.description = description;
        this.cost = cost;
        this.hasBought = hasBought;
    }
}
