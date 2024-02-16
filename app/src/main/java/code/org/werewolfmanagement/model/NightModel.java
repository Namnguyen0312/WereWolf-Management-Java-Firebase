package code.org.werewolfmanagement.model;

public class NightModel {
    private int noNight;
    private PlayerModel bite;
    private PlayerModel protect;

    public NightModel() {
    }

    public NightModel(int noNight, PlayerModel bite, PlayerModel protect) {
        this.noNight = noNight;
        this.bite = bite;
        this.protect = protect;
    }

    public int getNoNight() {
        return noNight;
    }

    public void setNoNight(int noNight) {
        this.noNight = noNight;
    }

    public PlayerModel getBite() {
        return bite;
    }

    public void setBite(PlayerModel bite) {
        this.bite = bite;
    }

    public PlayerModel getProtect() {
        return protect;
    }

    public void setProtect(PlayerModel protect) {
        this.protect = protect;
    }
}
