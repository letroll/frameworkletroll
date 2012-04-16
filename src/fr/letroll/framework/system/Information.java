package fr.letroll.framework.system;

import java.util.ArrayList;
import java.util.List;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

public class Information {
//	private final static String tag = "Information";

	/**
	 * @return pixel value for dip mesure
	 */
	public static float getPixelFromDip(int dp, Context context) {
		Resources r = context.getResources();
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
	}

	/**
	 * @return dip value for pixel mesure
	 */
	public static float getDipFromPixel(int px, Context context) {
		Resources r = context.getResources();
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, r.getDisplayMetrics());
	}

	/**
	 * @return true if sdcard mounted
	 */
	public static boolean isSdPresent() {
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * @param context
	 * @return return an arraylist<String> who contain the user's accounts
	 */
	public static ArrayList<String> getMailsUser(Context context) {
		Account[] accounts = AccountManager.get(context).getAccounts();
		ArrayList<String> possibleEmail = new ArrayList<String>();
		for (Account account : accounts) {
			possibleEmail.add(account.name);
		}
		return possibleEmail;
	}

	/**
	 * @param context
	 * @return true if we are connected
	 * 	PERMISSION:
	 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
	 */
	public static boolean IsConnectedToNetwork(Context context) {
	    ConnectivityManager cm =
	            (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    	try{
	        return cm.getActiveNetworkInfo().isConnectedOrConnecting();
	    	}catch (Exception e) {
				// call if cm was null... so there aren't any connection:
	    		return false;
			}
	}

	public static boolean IsConnectedToWifi(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			for (NetworkInfo inf : info) {
				if (inf.getTypeName().contains("WIFI"))
					if (inf.isConnected())
						return true;
			}
		}
		return false;
	}

	public static boolean isWirelessLocationEnabled(Context context) {
		ContentResolver cr = context.getContentResolver();
		String enabledProviders = Settings.Secure.getString(cr, Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (!TextUtils.isEmpty(enabledProviders)) {
			// not the fastest way to do that :)
			String[] providersList = TextUtils.split(enabledProviders, ",");
			for (String provider : providersList) {
				if (LocationManager.NETWORK_PROVIDER.equals(provider)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @return VersionName
	 */
	public static String getVersionName(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "0";
	}

	/**
	 * @param context
	 * @param String
	 *            packagename
	 * @return return the version of package name given in argument
	 */
	public static int getVersion(Context context) {
		try {
			PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			return pInfo.versionCode;
		} catch (NameNotFoundException e) {
			return 0;
		}
	}

	/**
	 * @param context
	 * @param String
	 *            packagename
	 * @return return the version of package name given in argument
	 */
	public static int getVersionCode(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0;

	}

	public static String getdeviceId(Context context) {
		return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}

	public static int getScreenwidth(Context context) {
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		return display.getWidth();
	}

	public static int getScreenHeight(Context context) {
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		return display.getHeight();
	}

	public static int getScreenOrientation(Context context) {
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		return display.getOrientation();
	}

	public static int getOSVersion() {
		return Integer.parseInt(android.os.Build.VERSION.SDK);
	}

	public static boolean isInstalled(Context context, Intent intent) {
		List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		return (list.size() > 0) ? true : false;
	}

	public static boolean isEmulator() {
		return Build.MANUFACTURER.equals("unknown");
	}

	/**
	 * 
	 * @param context
	 * 
	 *            1: normal 2: vibrate 3: silent 0: error
	 */
	public static int getSoundMode(Context context) {
		AudioManager audMangr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		switch (audMangr.getMode()) {
		case AudioManager.RINGER_MODE_NORMAL:
			return 1;
		case AudioManager.RINGER_MODE_VIBRATE:
			return 2;
		case AudioManager.RINGER_MODE_SILENT:
			return 3;
		default:
			return 0;
		}
	}

}
