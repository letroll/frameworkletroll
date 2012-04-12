package fr.letroll.framework;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import org.apache.http.params.HttpProtocolParams;

public class Web {
	
	private static String tag = "Web";
	
	/**
	 * Download a file.
	 * 
	 * @param fileURL
	 *            path of the download
	 * @param path
	 *            path where you want to put the downloaded file
	 * @param fileName
	 *            file name assigned to the recovered file
	 * @param context
	 * @return True if download is successful
	 */

	public static Boolean DownloadFromUrl(String fileURL, String path, String fileName, Context context) {
		try {
			fileName = fileName.replace("%20", "_");
			URL url = new URL(fileURL);
			File file = new File(path);
			file.mkdirs();
			File output = new File(file, fileName);
			if (output.exists()) {
				return false;
			} else {
				output.createNewFile();
			}
			// long startTime = System.currentTimeMillis();
			// Log.e("DownloadFromUrl", "download begining");
			// Log.e("DownloadFromUrl", "download url:" + url);
			// Log.e("DownloadFromUrl", "downloaded file name:" + fileName);

			/* Open a connection to that URL. */
			URLConnection ucon = url.openConnection();
			ucon.setConnectTimeout(4000);
			ucon.setReadTimeout(5000);

			/*
			 * Define InputStreams to read from the URLConnection.
			 */
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);

			/*
			 * Read bytes to the Buffer until there is nothing more to read(-1).
			 */
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}

			/* Convert the Bytes read to a String. */
			FileOutputStream fos = new FileOutputStream(output);
			fos.write(baf.toByteArray());
			fos.close();
			bis.close();
			is.close();
			// Log.e("DownloadFromUrl",
			// "download ready in"
			// + ((System.currentTimeMillis() - startTime) / 1000)
			// + " sec");
		} catch (ConnectException e) {
			e.printStackTrace();
			return false;
		} catch (SocketException e) {
			e.printStackTrace();
			return false;
		} catch (MalformedURLException e) {
			Log.e("DownloadFromUrl bad url", "Error: " + e + "<BR>" + e.getMessage());
			Log.e("DownloadFromUrl bad url", "Error: " + fileURL);
			return false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param String
	 *            url path of html source
	 * @param nvps
	 *            NameValuePair
	 * @return html source as a string
	 */

	public static String GetHTML(String url, List<NameValuePair> nvps) {
		return GetHTML(url, nvps, "");
	}

	/**
	 * @param String
	 *            url path of html source
	 * @param nvps
	 *            NameValuePair
	 * @param useragent
	 * @return html source as a string
	 */

	public static String GetHTML(String url, List<NameValuePair> nvps, String useragent) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		if (useragent != "")
			httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, useragent);
		httpClient.getParams().setParameter("http.socket.timeout", new Integer(90000));
		HttpProtocolParams.setUserAgent(httpClient.getParams(), useragent);
		try {
			HttpResponse res;
			URI uri = new URI(url);
			if (nvps != null) {
				HttpPost methodpost = new HttpPost(uri);
				methodpost.addHeader("pragma", "no-cache");
				methodpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
				res = httpClient.execute(methodpost);
			} else {
				HttpGet methodget = new HttpGet(uri);
				methodget.addHeader("pragma", "no-cache");
				res = httpClient.execute(methodget);
			}
			InputStream data = res.getEntity().getContent();

			return FileLt.generateString(data);

		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 
	 * @param url
	 * @return Bitmap image
	 */
	public static Bitmap getImageBitmap(String url) {
		Bitmap bm = null;
		try {
			URL aURL = new URL(url);
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			bm = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
		} catch (IOException e) {
			Notification.log(tag, "Error getting bitmap:" + url);
			if (url.length() < 2)
				Notification.log(tag, "url vide...");
			e.printStackTrace();
			return null;
		}
		return bm;
	}
	
	/**
	 * @param Context
	 *            context
	 * @param Boolean
	 *            showlog
	 * @return version of the package as a string
	 */
	public static String GetVersionOnMarket(Context context) {
		return GetVersionOnMarket(context, false);
	}

	/**
	 * @param Context
	 *            context
	 * @return version of the package as a string
	 */
	public static String GetVersionOnMarket(Context context, Boolean showlog) {
		String tag = context.getClass().getSimpleName();
		if(showlog)Notification.log(tag, ""+context.getPackageName());
		String url = "https://play.google.com/store/apps/details?id=" + context.getPackageName();
		
		if (showlog)
			Notification.log(tag, url);
		String source = GetHTML(url, null);
		if (showlog)
			Notification.log(tag, source);
		try {
			int index = source.indexOf("softwareVersion");
			source = source.substring(index + 17, source.length());
			index = source.indexOf("<");
			source = source.substring(0, index);
		} catch (Exception e) {
			return "";
		}
		return source;
	}

}
