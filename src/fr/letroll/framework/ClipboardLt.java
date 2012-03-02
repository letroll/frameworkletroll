package fr.letroll.framework;

import android.content.Context;
import android.text.ClipboardManager;
import android.widget.EditText;

public class ClipboardLt {

    public static void copyToClipBoard(Context context, String monText) {
        ClipboardManager ClipMan = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipMan.setText(monText);
    }

    public static void pasteFromClipBoard(Context context, EditText e1) {
        ClipboardManager ClipMan = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        e1.setText(ClipMan.getText());
    }
    
    public static String getTextFromClipBoard(Context context) {
        ClipboardManager ClipMan = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return ClipMan.getText().toString();
    }

}
