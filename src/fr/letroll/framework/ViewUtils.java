package fr.letroll.framework;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

public class ViewUtils {

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void setQrcodeOfApplication(Context context, ImageView view) {
        try {
            view.setImageDrawable(Drawable.createFromStream(
                    (InputStream) new URL("https://chart.googleapis.com/chart?cht=qr&chs=256x256&chl=market://details?id=" + context.getPackageName()).getContent(), "src"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Drawable getQrcodeOfApplication(Context context){
        try {
            return Drawable.createFromStream((InputStream) new URL("https://chart.googleapis.com/chart?cht=qr&chs=256x256&chl=market://details?id=" + context.getPackageName()).getContent(), "src");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
