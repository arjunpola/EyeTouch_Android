package com.exmp.eyetouch_v1;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import android.util.LruCache;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.exmp.eyetouch_v1.R;

public class TheTeam extends Activity{
	
	JSONObject detailInfo;
	JSONArray tot,dev,con;
    String screenType = "small";
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.the_team_dynamic);
		SpannableString s = new SpannableString("EyeTouch");
	    s.setSpan(new TypefaceSpan(this,"Prototype.ttf"), 0, s.length(),
	            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	 
	    // Update the action bar title with the TypefaceSpan instance
	    ActionBar actionBar = getActionBar();
	    actionBar.setTitle(s);
	    
	    if ((getResources().getConfiguration().screenLayout & 
			    Configuration.SCREENLAYOUT_SIZE_MASK) == 
			        Configuration.SCREENLAYOUT_SIZE_LARGE) {
			    // on a large screen device ...
			screenType = "large";
			}
	    
	    try {
			getJsonData();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    
	    tot = null;
        dev = null;
        con = null;
        try {
			tot = detailInfo.getJSONArray("Thought");
			dev = detailInfo.getJSONArray("Development");
			con = detailInfo.getJSONArray("Contributors");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    
	    WebView wv = (WebView)findViewById(R.id.team);
	    WebSettings ws = wv.getSettings();
	    ws.setJavaScriptEnabled(true);
	    ws.setBuiltInZoomControls(false);
	  
	    wv.addJavascriptInterface(new WebAppInterface(this),"Android");
		wv.setWebChromeClient(new WebChromeClient() {
			  public boolean onConsoleMessage(ConsoleMessage cm) {
//			    Log.d("MyApplication", cm.message() + " -- From line "
//			                         + cm.lineNumber() + " of "
//			                         + cm.sourceId() );
			    return true;
			  }
			});
		
		wv.setWebViewClient(new MyClient());
	    
	    wv.loadUrl("file:///android_res/raw/team.html");
	 
}
	
	
	
	public void getJsonData() throws IOException, JSONException
	{
		InputStream in = this.getAssets().open("team.json");
		int sz = in.available();
		byte[] temp = new byte[sz];
		in.read(temp);
		in.close();
		String jtext = new String(temp);
		
		detailInfo = new JSONObject(jtext).optJSONObject("Team");
//		Log.v("Team JSON",detailInfo.toString());
		
	}
	
	public class WebAppInterface {
	    Context mContext;

	    /** Instantiate the interface and set the context */
	    WebAppInterface(Context c) {
	        mContext = c;
	    }
	    
	    @JavascriptInterface
	    public void gallery() {
	        finish();
	    }
	}
	
public class MyClient extends WebViewClient {
	    
		public void onPageStarted(WebView view,String url,Bitmap favicon)
	    {
			super.onPageStarted(view, url, favicon);
			
	    	
	    }
	    
	    public void onPageFinished(WebView view, String url) {
	        super.onPageFinished(view, url);
	        
	        String ht = "javascript:window.Android_getInfo('"+tot+"','"+dev+"','"+con+"','"+screenType+"');";
	        view.loadUrl(ht);
	    }
	}
		
//////////////class for changing font of title/////////////////////////////////////
public class TypefaceSpan extends MetricAffectingSpan {
/** An <code>LruCache</code> for previously loaded typefaces. */
private final LruCache<String, Typeface> sTypefaceCache =
new LruCache<String, Typeface>(12);

private Typeface mTypeface;

/**
* Load the {@link Typeface} and apply to a {@link Spannable}.
*/
@SuppressLint("NewApi")
public TypefaceSpan(Context context, String typefaceName) {
mTypeface = sTypefaceCache.get(typefaceName);

if (mTypeface == null) {
mTypeface = Typeface.createFromAsset(context.getApplicationContext()
.getAssets(), String.format("fonts/%s", typefaceName));

//Cache the loaded Typeface
sTypefaceCache.put(typefaceName, mTypeface);
}
}		 
@Override
public void updateDrawState(TextPaint tp) {
tp.setTypeface(mTypeface);
//Note: This flag is required for proper typeface rendering
tp.setFlags(tp.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
}
@Override
public void updateMeasureState(TextPaint p) {
// TODO Auto-generated method stub
p.setTypeface(mTypeface);
// Note: This flag is required for proper typeface rendering
p.setFlags(p.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
}
}

///////////////////End of the typeface class/////////////////////////////////////////////////////////////////
}
