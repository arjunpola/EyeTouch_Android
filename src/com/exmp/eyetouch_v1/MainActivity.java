package com.exmp.eyetouch_v1;



import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import android.util.LruCache;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.exmp.eyetouch_v1.DetailView;
import com.exmp.eyetouch_v1.EyeAnatomy;
import com.exmp.eyetouch_v1.EyeDiagnosis;
import com.exmp.eyetouch_v1.SubDetail;
import com.exmp.eyetouch_v1.TheTeam;
import com.exmp.eyetouch_v1.R;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);
		
		SpannableString s = new SpannableString("Eye Touch");
	    s.setSpan(new TypefaceSpan(this,"Prototype.ttf"), 0, s.length(),
	            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	 
	    // Update the action bar title with the TypefaceSpan instance
	    ActionBar actionBar = getActionBar();
	    actionBar.setTitle(s);		
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
		public TypefaceSpan(Context context, String typefaceName) {
		mTypeface = sTypefaceCache.get(typefaceName);
		 
		if (mTypeface == null) {
		mTypeface = Typeface.createFromAsset(context.getApplicationContext()
		.getAssets(), String.format("fonts/%s", typefaceName));
		 
		// Cache the loaded Typeface
		sTypefaceCache.put(typefaceName, mTypeface);
		}
		}		 
		@Override
		public void updateDrawState(TextPaint tp) {
		tp.setTypeface(mTypeface);
		// Note: This flag is required for proper typeface rendering
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		 // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.activity_main_xml, menu);
	    return super.onCreateOptionsMenu(menu);
	        
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 switch (item.getItemId()) {
        	 
         case R.id.contact_us:
        	 Intent j = new Intent(this,ContactUs.class);
        	 startActivity(j);	 
        	 return true;
             
         default:
             return false;
		 }
	   
	    }
	

	
	
	public void detailNav(View v)
	{
		Intent i = new Intent(this,DetailView.class);
		i.putExtra("INFO",v.getContentDescription().toString());
		i.putExtra("DINFO","Generic");
		startActivity(i);
	}
	
	public void subNav(View v)
	{
		Intent i = new Intent(this,SubDetail.class);
		i.putExtra("INFO",v.getContentDescription().toString());
		startActivity(i);
	}
	public void mediaList(View v)
	{
		Intent i = new Intent(this,MediaView.class);
//		Log.d("enterd media list method", "enterd media list method");
		//i.putExtra("INFO",v.getContentDescription().toString());
		//i.putExtra("DINFO","Generic");
		startActivity(i);
	}
	public void subNavAnatomy(View v)
	{
		Intent i = new Intent(this,EyeAnatomy.class);
		//i.putExtra("INFO",v.getContentDescription().toString());
		startActivity(i);
	}
	public void subNavDiagnosis(View v)
	{
		Intent i = new Intent(this,EyeDiagnosis.class);
		//i.putExtra("INFO",v.getContentDescription().toString());
		startActivity(i);
	}
	public void theTeam(View v)
	{
		Intent i = new Intent(this,TheTeam.class);
		//i.putExtra("INFO",v.getContentDescription().toString());
		startActivity(i);
	}
	
	
	//Stores all the Constants and other important information related to the state of the app.
	public static class info{
		
		final String ip="10.0.0.3:8080";
		
	}

}

