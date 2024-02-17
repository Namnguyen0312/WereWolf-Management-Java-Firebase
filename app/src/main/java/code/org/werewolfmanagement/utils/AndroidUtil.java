package code.org.werewolfmanagement.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import code.org.werewolfmanagement.model.RoomModel;

public class AndroidUtil {

    public static int count = 0;

    public static Bundle passModelByArgument(RoomModel roomModel, int count){
        Bundle bundle = new Bundle();
        bundle.putString("name", roomModel.getName());
        bundle.putInt("numberOfPlayer", roomModel.getNumberOfPlayer());
        bundle.putInt("valueOfWolf", roomModel.getValueOfWolf());
        bundle.putInt("valueOfVillager", roomModel.getValueOfVillager());
        bundle.putInt("valueOfShield", roomModel.getValueOfShield());
        bundle.putString("roomId", roomModel.getRoomId());
        bundle.putInt("count", count);
        return bundle;
    }

    public static RoomModel getModelByArgument(Bundle bundle){
        RoomModel roomModel = new RoomModel();
        roomModel.setName(bundle.getString("name"));
        roomModel.setNumberOfPlayer(bundle.getInt("numberOfPlayer"));
        roomModel.setValueOfWolf(bundle.getInt("valueOfWolf"));
        roomModel.setValueOfVillager(bundle.getInt("valueOfVillager"));
        roomModel.setValueOfShield(bundle.getInt("valueOfShield"));
        roomModel.setRoomId(bundle.getString("roomId"));

        return roomModel;
    }

    public static Animation getAnimation(Context context, int anim) {
        return AnimationUtils.loadAnimation(context, anim);
    }

    public static void getFragmentManagerAndSetAnim(FragmentManager fragmentManager, String name, int containerId, Fragment fragment, int in, int out) {
        fragmentManager.beginTransaction().setCustomAnimations(in, out).replace(containerId, fragment).addToBackStack(name).commit();
    }

    public static void passRoomModelAsIntent(Intent intent, RoomModel model, String roomId) {
        intent.putExtra("name", model.getName());
        intent.putExtra("numberOfPlayer", model.getNumberOfPlayer());
        intent.putExtra("valueOfWolf", model.getValueOfWolf());
        intent.putExtra("valueOfVillager", model.getValueOfVillager());
        intent.putExtra("valueOfShield", model.getValueOfShield());
        intent.putExtra("roomId", roomId);
    }

    public static RoomModel getRoomModelFromIntent(Intent intent) {
        RoomModel roomModel = new RoomModel();
        roomModel.setName(intent.getStringExtra("name"));
        roomModel.setRoomId(intent.getStringExtra("roomId"));
        roomModel.setNumberOfPlayer(intent.getIntExtra("numberOfPlayer", 0));
        roomModel.setValueOfWolf(intent.getIntExtra("valueOfWolf", 0));
        roomModel.setValueOfVillager(intent.getIntExtra("valueOfVillager", 0));
        roomModel.setValueOfShield(intent.getIntExtra("valueOfShield", 0));
        return roomModel;
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static int parseInt(String string) {
        return Integer.parseInt(string);
    }

    public static boolean isWolfMoreThanVillagers(int wolf, int villager , int shield){
        if(wolf >= (villager + shield)) return true;
        else  return false;
    }

    public static boolean isMoreThanAllRoles(int wolf, int villager, int shield, int numberOfPlayer) {
        if ((wolf + villager + shield) < numberOfPlayer) return false;
        else return true;
    }

    public static boolean isEqualSumAllRoles(int wolf, int villager, int shield, int numberOfPlayer) {
        if ((wolf + villager + shield) == numberOfPlayer) return true;
        return false;
    }


    /**
     * Dem so luong role khac nhau
     *
     * @return
     */
    public static int valueRole(int countWolf, int countVillager) {
        if (countWolf > 0 && countVillager > 0) return 2;
        else if ((countWolf == 0 && countVillager > 0) || (countWolf > 0 && countVillager == 0))
            return 1;
        else return 0;
    }

}
