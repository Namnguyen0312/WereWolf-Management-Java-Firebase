package code.org.werewolfmanagement.utils;

import java.util.ArrayList;

import code.org.werewolfmanagement.model.RoleModel;

public class RoleUtil {

    private static RoleUtil instance;

    private static ArrayList<RoleModel> roleModels;

    private RoleUtil(){
        roleModels = new ArrayList<>();
        initData();
    }

    private void initData() {
        roleModels.add(new RoleModel("Wolf", 0, 0, 0, 0));
        roleModels.add(new RoleModel("Villager", 0, 0, 0, 0));
        roleModels.add(new RoleModel("Shield", 0, 0, 0, 0));
        roleModels.add(new RoleModel("Seer", 0, 0, 0, 0));
    }

    public static RoleUtil getInstance() {
        instance = new RoleUtil();
        return instance;
    }

    public static ArrayList<RoleModel> getRoleModels() {
        return roleModels;
    }

}
