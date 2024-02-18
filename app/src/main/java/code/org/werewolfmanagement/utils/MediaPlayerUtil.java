package code.org.werewolfmanagement.utils;

import android.content.Context;
import android.media.MediaPlayer;


public class MediaPlayerUtil {
    private static MediaPlayerUtil instance;
    private MediaPlayer mediaPlayer;

    private MediaPlayerUtil() {
        mediaPlayer = new MediaPlayer();
    }

    public static MediaPlayerUtil getInstance() {
        if (instance == null) {
            instance = new MediaPlayerUtil();
        }
        return instance;
    }

    public void playMedia(Context context, int raw) {
        mediaPlayer = MediaPlayer.create(context, raw);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public void stopMedia() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }



}
