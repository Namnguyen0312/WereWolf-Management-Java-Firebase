package code.org.werewolfmanagement.model;

public class RoleModel {
    private String role;
    private int valueWolf;
    private int valueVillager;
    private int valueShield;
    private int valueSeer;

    public RoleModel(String role, int valueWolf, int valueVillager, int valueShield, int valueSeer) {
        this.role = role;
        this.valueWolf = valueWolf;
        this.valueVillager = valueVillager;
        this.valueShield = valueShield;
        this.valueSeer = valueSeer;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getValueWolf() {
        return valueWolf;
    }

    public void setValueWolf(int valueWolf) {
        this.valueWolf = valueWolf;
    }

    public int getValueVillager() {
        return valueVillager;
    }

    public void setValueVillager(int valueVillager) {
        this.valueVillager = valueVillager;
    }

    public int getValueShield() {
        return valueShield;
    }

    public void setValueShield(int valueShield) {
        this.valueShield = valueShield;
    }

    public int getValueSeer() {
        return valueSeer;
    }

    public void setValueSeer(int valueSeer) {
        this.valueSeer = valueSeer;
    }
}
