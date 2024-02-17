package code.org.werewolfmanagement.model;


public class PlayerModel{
    private int playerId;
    private String namePlayer;
    private String role;
    private boolean isProtected, isBitten, isDead, isProtectedLastNight;

    public PlayerModel() {
    }

    public PlayerModel(int playerId, String namePlayer, String role, boolean isProtected, boolean isBitten, boolean isDead, boolean isProtectedLastNight) {
        this.playerId = playerId;
        this.namePlayer = namePlayer;
        this.role = role;
        this.isProtected = isProtected;
        this.isBitten = isBitten;
        this.isDead = isDead;
        this.isProtectedLastNight = isProtectedLastNight;
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

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }

    public boolean isBitten() {
        return isBitten;
    }

    public void setBitten(boolean bitten) {
        isBitten = bitten;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isProtectedLastNight() {
        return isProtectedLastNight;
    }

    public void setProtectedLastNight(boolean protectedLastNight) {
        isProtectedLastNight = protectedLastNight;
    }

    public String getNamePlayer() {
        return namePlayer;
    }

    public void setNamePlayer(String namePlayer) {
        this.namePlayer = namePlayer;
    }
}
