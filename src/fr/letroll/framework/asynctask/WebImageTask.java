package fr.letroll.framework.asynctask;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import fr.letroll.framework.BitmapLt;
import fr.letroll.framework.Web;

public class WebImageTask extends AsyncTask<String, Void, Bitmap>{
	private ImageView vue;
	private String path;
	
	public WebImageTask(ImageView mvue){
		vue=mvue;
	}
	
	public WebImageTask(String mpath){
		path=mpath;
	}
	
	@Override
    protected Bitmap doInBackground(String... params) {
		if(params.length==0)return null ;
		else{
			return Web.getImageBitmap(params[0]); 
		}
    }
	
	@Override
	protected void onPostExecute(Bitmap result) {
		if(result!=null){
			if(path==null)vue.setImageBitmap(result);
			else BitmapLt.saveBitmap(result, path);
		}
	    super.onPostExecute(result);
	}

}
