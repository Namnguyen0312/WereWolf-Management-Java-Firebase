package code.org.werewolfmanagement.model;

import java.io.Serializable;

public class PlayerModel implements Serializable {
    private int playerId;
    private String role;

    public PlayerModel() {
    }

    public PlayerModel(int playerId, String role) {
        this.playerId = playerId;
        this.role = role;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
