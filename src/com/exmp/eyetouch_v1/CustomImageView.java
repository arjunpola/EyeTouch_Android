package com.exmp.eyetouch_v1;

import com.exmp.eyetouch_v1.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class CustomImageView extends Activity {
	
	String imgLink;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_image_view);
		WebView web = (WebView)findViewById(R.id.WebImage);
		
		WebSettings settings = web.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setBuiltInZoomControls(true);
		
		Bundle bundle = (Bundle) getIntent().getExtras();
		imgLink = bundle.getString("IMAGE_LINK");
		
		Log.v("IMAGE_LINK", imgLink);
		
		String data = "<body>" + "<img src=\""+imgLink+"\"/></body>";
		settings.setLoadWithOverviewMode(true);
		settings.setUseWideViewPort(true);
		
		web.loadDataWithBaseURL("file:///android_res/raw/",data , "text/html", "utf-8",null);		
	}
}
