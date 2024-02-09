package code.org.werewolfmanagement.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import code.org.werewolfmanagement.model.RoomModel;

public class AndroidUtil {

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static int parseInt(String string){
        return Integer.parseInt(string);
    }
//    public static void passRoomModelAsIntent(Intent intent, RoomModel model){
//        intent.putExtra("name", model.getName());
//        intent.putExtra("roomId", model.getRoomId());
//        intent.putExtra("createdTimestamp", model.getCreatedTimestamp());
//        intent.putExtra("numberOfPlayer", model.getNumberOfPlayer());
//    }
//
//    public static RoomModel getRoomModelFromIntent(Intent intent){
//        RoomModel roomModel = new RoomModel();
//        roomModel.setName(intent.getStringExtra("name"));
//        roomModel.setRoomId(intent.getStringExtra("roomId"));
//        roomModel.setNumberOfPlayer(intent.getStringExtra("numberOfPlayer"));
//        return roomModel;
//    }

    public static boolean isMoreThanSumWolfAndVillager(int wolf, int villager, int numberOfPlayer){
        if((wolf + villager) < numberOfPlayer) return false;
        else return true;
    }

    public static boolean isEqualSumWolfAndVillager(int wolf, int villager, int numberOfPlayer){
        if((wolf + villager) == numberOfPlayer) return true;
        return false;
    }

    /**
     * Dem so luong role khac nhau
     * @return
     */
    public static int valueRole(int countWolf, int countVillager){
        if(countWolf > 0 && countVillager > 0) return 2;
        else if((countWolf == 0 && countVillager >  0) || (countWolf > 0 && countVillager ==  0)) return 1;
        else return 0;
    }

}
