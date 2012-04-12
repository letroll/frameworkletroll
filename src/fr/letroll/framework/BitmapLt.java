package fr.letroll.framework;

import java.io.FileOutputStream;

import android.graphics.Bitmap;

public class BitmapLt {
	
	/**
	 * 
	 * @param bitmap
	 * @param path
	 * @return if saving bitmap to the path was successfull
	 */
	public static Boolean saveBitmap(Bitmap bitmap, String path) {
		try {
			FileOutputStream out = new FileOutputStream(path);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
