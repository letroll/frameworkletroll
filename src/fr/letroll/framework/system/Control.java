package fr.letroll.framework.system;

import fr.letroll.framework.Notification;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;

public class Control {
	private static final String tag="frameworkletroll";
	
    public static void enableWifi(Context context){
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(true);
    }
    
    public static void disableWifi(Context context){
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(false);
    }
    
    public static void launchCall(Context context,String tel){
    	try {
    		Intent dialIntent = new Intent("android.intent.action.DIAL", Uri.parse("tel:" + tel));
    		context.startActivity(dialIntent);
    	} catch (Exception e) {
    		Notification.log(tag, "Failed to invoke call");
    		e.printStackTrace();
    	}
    }
    /**
     * ?? utiliser avec la permission : android.permission.MODIFY_AUDIO_SETTINGS
     * @param context
     */
    public static void muteSound(Context context){
        AudioManager mAudioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
    }
/**
 * 
 * @param context
 * @param mode
 * 
 * 1: normal
 * 2: vibrate
 * 3: silent
 */
    public static void changeSoundMode(Context context,int mode){
		AudioManager audMangr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		switch (mode) {
		case 1:
			audMangr.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
			break;
		case 2:
			audMangr.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
			break;
		case 3:
			audMangr.setRingerMode(AudioManager.RINGER_MODE_SILENT);
			break;
		default:
			break;
		}
    }
}
