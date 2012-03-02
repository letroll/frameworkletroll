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
    
    public static void sendMail(Context context, String mail, String subject, String text){
        /* Create the Intent */
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        /* Fill it with Data */
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { mail });
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
         emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);

        /* Send it off to the Activity-Chooser */
        context.startActivity(Intent.createChooser(emailIntent, "envoyer avec"));
    }
}
