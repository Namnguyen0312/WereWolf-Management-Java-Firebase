package code.org.werewolfmanagement.model;

import java.io.Serializable;

public class PlayerModel implements Serializable {
    private String playerId;
    private String role;

    public PlayerModel() {
    }

    public PlayerModel(String playerId, String role) {
        this.playerId = playerId;
        this.role = role;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
