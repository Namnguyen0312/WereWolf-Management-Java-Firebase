package code.org.werewolfmanagement.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import code.org.werewolfmanagement.model.RoomModel;

public class AndroidUtil {

    public static void passRoomModelAsIntent(Intent intent, RoomModel model, String roomId){
        intent.putExtra("name", model.getName());
        intent.putExtra("numberOfPlayer", model.getNumberOfPlayer());
        intent.putExtra("valueOfWolf", model.getValueOfWolf());
        intent.putExtra("valueOfVillager", model.getValueOfVillager());
        intent.putExtra("valueOfShield", model.getValueOfShield());
        intent.putExtra("roomId", roomId);
    }

    public static RoomModel getRoomModelFromIntent(Intent intent){
        RoomModel roomModel = new RoomModel();
        roomModel.setName(intent.getStringExtra("name"));
        roomModel.setRoomId(intent.getStringExtra("roomId"));
        roomModel.setNumberOfPlayer(intent.getIntExtra("numberOfPlayer", 0));
        roomModel.setValueOfWolf(intent.getIntExtra("valueOfWolf", 0));
        roomModel.setValueOfVillager(intent.getIntExtra("valueOfVillager",0));
        roomModel.setValueOfShield(intent.getIntExtra("valueOfShield",0));
        return roomModel;
    }

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static int parseInt(String string){
        return Integer.parseInt(string);
    }


    public static boolean isMoreThanAllRoles(int wolf, int villager, int shield,int numberOfPlayer){
        if((wolf + villager + shield) < numberOfPlayer) return false;
        else return true;
    }

    public static boolean isEqualSumAllRoles(int wolf, int villager, int shield,int numberOfPlayer){
        if((wolf + villager + shield) == numberOfPlayer) return true;
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
