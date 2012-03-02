package fr.letroll.framework;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

public class IntentLt {

    public static void openSite(Context context, String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        Uri u = Uri.parse(url);
        i.setData(u);
        context.startActivity(i);
    }

    public static void openMarketApp(Context context) {
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName()));
        context.startActivity(goToMarket);
    }
    
    public static void openMarketApp(Context context,String mpackage) {
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + mpackage));
        context.startActivity(goToMarket);
    }

    public static void openActivityString(Context context, String className) {
        try {
            Intent openNewIntent = new Intent(context, Class.forName(className));
            context.startActivity(openNewIntent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
}
