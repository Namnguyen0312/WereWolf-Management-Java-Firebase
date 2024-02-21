package code.org.werewolfmanagement;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import code.org.werewolfmanagement.model.RoomModel;
import code.org.werewolfmanagement.utils.AndroidUtil;
import code.org.werewolfmanagement.utils.MediaPlayerUtil;

public class NightFragment extends Fragment {

    private LinearLayout nightClick;
    private TextView nameRoomNightTxt, nightTxt, callTxt;
    private RoomModel roomModel;
    private int countNight, countCall;

    private MediaPlayerUtil nightMedia;

    private static final String TAG = "NightFragment";

    public NightFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_night, container, false);

        nightMedia = MediaPlayerUtil.getInstance();

        initView(view);

        getDataFromDayFragment();


        if (countCall == 0) {
            nightMedia.stopMedia();
            nightMedia.playMedia(requireContext(), R.raw.night);
        }

        setNameRoom();

        setNight();

        setCall();

        Log.d(TAG, "onCreateView: countcall " + countCall);
        nightClick.setOnClickListener(v -> {
            Log.d(TAG, "onCreateView: countcall " + countCall);
            setArgument();
        });

        return view;
    }

    private void setArgumentToDayFragment(){
        Bundle bundle = AndroidUtil.passModelByArgument(roomModel, countNight);
        bundle.putInt("countCall", countCall);
        NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
        navController.navigate(R.id.navigateFromNightFragmentToDayFragment, bundle);
    }

    private void setArgument() {
        Bundle bundle = AndroidUtil.passModelByArgument(roomModel, countNight);
        bundle.putInt("countCall", countCall);
        if (countCall == 0) {
            NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
            navController.navigate(R.id.navigateToShieldFragment, bundle);
        }
        else if (countCall == 1) {
            NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
            navController.navigate(R.id.navigateToWolfFragment, bundle);
        }
        else if (countCall == 2) {
            NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
            navController.navigate(R.id.navigateToSeerFragment, bundle);
        }
    }

    private void setCall() {
        if (countCall == 0) {
            if (roomModel.getValueOfShield() != 0) {
                callTxt.setText("Shield, Please Wake Up");
            } else countCall++;
        }

        if (countCall == 1) {
            if (roomModel.getValueOfWolf() != 0) {
                callTxt.setText("Wolf, Please Wake Up");
            } else countCall++;
        }

        if (countCall == 2) {
            if (roomModel.getValueOfSeer() != 0) {
                callTxt.setText("Seer, Please Wake Up");
            } else countCall++;
        }

        if (countCall == 3) {
            setArgumentToDayFragment();
        }

    }

    private void setNight() {
        nightTxt.setText("Night " + countNight);
    }

    private void setNameRoom() {
        String name = roomModel.getName();
        nameRoomNightTxt.setText(name);
    }

    private void getDataFromDayFragment() {
        countNight = getArguments().getInt("count");
        countCall = getArguments().getInt("countCall");
        roomModel = AndroidUtil.getModelByArgument(getArguments());
    }


    public void initView(View view) {
        nameRoomNightTxt = view.findViewById(R.id.nameRoomNightTxt);
        nightTxt = view.findViewById(R.id.nightTxt);
        nightClick = view.findViewById(R.id.nightClick);
        callTxt = view.findViewById(R.id.callTxt);
    }

}