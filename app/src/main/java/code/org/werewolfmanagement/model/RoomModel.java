package code.org.werewolfmanagement.model;

import com.google.firebase.Timestamp;


public class RoomModel {
    private String name;
    private Timestamp createdTimestamp;
    private int numberOfPlayer;
    private int valueOfWolf, valueOfVillager, valueOfShield, valueOfSeer;
    private String roomId;

    public RoomModel() {
    }

    public RoomModel(String name, Timestamp createdTimestamp, int numberOfPlayer, int valueOfWolf, int valueOfVillager, int valueOfShield, int valueOfSeer) {
        this.name = name;
        this.createdTimestamp = createdTimestamp;
        this.numberOfPlayer = numberOfPlayer;
        this.valueOfWolf = valueOfWolf;
        this.valueOfVillager = valueOfVillager;
        this.valueOfShield = valueOfShield;
        this.valueOfSeer = valueOfSeer;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    public void setNumberOfPlayer(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
    }

    public int getValueOfWolf() {
        return valueOfWolf;
    }

    public void setValueOfWolf(int valueOfWolf) {
        this.valueOfWolf = valueOfWolf;
    }

    public int getValueOfVillager() {
        return valueOfVillager;
    }

    public void setValueOfVillager(int valueOfVillager) {
        this.valueOfVillager = valueOfVillager;
    }

    public int getValueOfShield() {
        return valueOfShield;
    }

    public void setValueOfShield(int valueOfShield) {
        this.valueOfShield = valueOfShield;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getValueOfSeer() {
        return valueOfSeer;
    }

    public void setValueOfSeer(int valueOfSeer) {
        this.valueOfSeer = valueOfSeer;
    }
}

