package fr.letroll.framework;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;

public class SystemControl {
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
    
    public static void muteSound(Context context){
        AudioManager mAudioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
    }

}
