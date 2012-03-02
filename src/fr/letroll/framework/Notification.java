package fr.letroll.framework;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Notification {
    
    public static void toastc(Context context,String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
    
    public static void toastl(Context context,String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
    
    public static void log(String tag,String text){
        Log.e(tag, text);
    }
}
