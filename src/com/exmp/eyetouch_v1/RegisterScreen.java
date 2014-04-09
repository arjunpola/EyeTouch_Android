package com.exmp.eyetouch_v1;
import com.exmp.eyetouch_v1.R;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import android.util.LruCache;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class RegisterScreen extends Activity {
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		SpannableString s = new SpannableString("Register");
		s.setSpan(new TypefaceSpan(this,"Prototype.ttf"), 0, s.length(),
	            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    // Update the action bar title with the TypefaceSpan instance
	    ActionBar actionBar = getActionBar();
	    actionBar.setTitle(s);	
        
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "Prototype.ttf");
        
        TextView tv = (TextView)findViewById(R.id.name);
        tv.setTypeface(tf);
        
        tv = (TextView)findViewById(R.id.email);
        tv.setTypeface(tf);
        
        tv = (TextView)findViewById(R.id.editPhone);
        tv.setTypeface(tf);
        
        Button b = (Button)findViewById(R.id.submit);
        b.setTypeface(tf);
     	
		Spinner spinner = (Spinner)findViewById(R.id.spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.purpose, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinner.setAdapter(adapter);
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
////////////////End of the typeface class/////////////////////////////////////////////////////////////////

}
