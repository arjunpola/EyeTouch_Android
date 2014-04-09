package com.exmp.eyetouch_v1;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.exmp.eyetouch_v1.BinderData;
import com.exmp.eyetouch_v1.DetailView;
import com.exmp.eyetouch_v1.EyeAnatomy;
import com.exmp.eyetouch_v1.R;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import android.util.LruCache;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class EyeAnatomy extends Activity {
	// XML node keys
    static final String KEY_ID = "id";
    static final String KEY_CITY = "title";
    static final String KEY_ICON = "icon";
    
    // List items 
    ListView list;
    String iconType;
    EditText inputSearch;
    BinderData bindingData;
    JSONArray headers,icons;
    String screenType = "SMALL";
    
    List<HashMap<String,String>> weatherDataCollection;
  
    //Android Code
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eye_anatomy);
        
        SpannableString s = new SpannableString("Eye Anatomy");
	    s.setSpan(new TypefaceSpan(this,"Prototype.ttf"), 0, s.length(),
	            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	 
	    // Update the action bar title with the TypefaceSpan instance
	    ActionBar actionBar = getActionBar();
	    actionBar.setTitle(s);	
        
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "Prototype.ttf");
        TextView tv = (TextView) findViewById(R.id.exploretext);
        tv.setTypeface(tf);
        
		if ((getResources().getConfiguration().screenLayout & 
			    Configuration.SCREENLAYOUT_SIZE_MASK) == 
			        Configuration.SCREENLAYOUT_SIZE_LARGE) {
			    // on a large screen device ...
			screenType = "LARGE";

			}

        /////////////////////////////////end of buttons
        


        inputSearch  = (EditText)findViewById(R.id.inputSearch);
        
      
        //iconType = b.getString("INFO");
        iconType = "A";
///////////////////////////////EYE-FRONT BUTTON Begins////////////////////////////////////
        ImageButton eye_front=(ImageButton)findViewById(R.id.eye_front);
        eye_front.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	iconType = "Af";
//                Log.v("ImageButton", "Clicked!");
                try {
        			getJsonData(iconType);
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		} catch (JSONException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
                
             
                
                try {
       		     weatherDataCollection = new ArrayList<HashMap<String,String>>();
       			HashMap<String,String> map = null;
       						
       			for (int i = 0; i < headers.length(); i++) {
       				 
       				   	map = new HashMap<String,String>(); 				
       					map.put(KEY_ID,String.valueOf(i+1));
       					map.put(KEY_CITY,headers.getString(i));
       					map.put(KEY_ICON,icons.getString(i));
                          	weatherDataCollection.add(map);
       			}
       			
       			

       	
       			bindingData = new BinderData(EyeAnatomy.this,weatherDataCollection,screenType);
             		list = (ListView) findViewById(R.id.list);
       			list.setAdapter(bindingData);
       			list.setOnItemClickListener(new OnItemClickListener() {

       	        public void onItemClick(AdapterView<?> parent, View view,
       						int position, long id) {

       					Intent i = new Intent();
       					i.setClass(EyeAnatomy.this, DetailView.class);

       					// parameters
       					i.putExtra("position", String.valueOf(position + 1));
       					i.putExtra("INFO",iconType);
       					i.putExtra("DINFO",weatherDataCollection.get(position).get(KEY_CITY));
       					
       					startActivity(i);
       				}
       			});

       		}
       		catch (Exception ex) {
//       			Log.e("Error", "Loading exception");
       		}
                
                
                
            }
           });
      ////////////////////////////////////front button ends/////////////////////////////////
        ImageButton eye_up=(ImageButton)findViewById(R.id.eye_up);
        eye_up.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	iconType = "Au";
//                Log.v("ImageButton", "Clicked!");
                try {
        			getJsonData(iconType);
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		} catch (JSONException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
                
             
                
                try {
       		     weatherDataCollection = new ArrayList<HashMap<String,String>>();
       	        //Log.v("Icons",icons.toString());
       			HashMap<String,String> map = null;
       						
       			for (int i = 0; i < headers.length(); i++) {
       				 
       				   	map = new HashMap<String,String>(); 				
       					map.put(KEY_ID,String.valueOf(i+1));
       				   	//Log.v("MID",String.valueOf(i+1));
       					map.put(KEY_CITY,headers.getString(i));
       					//Log.v("MCITY",headers.getString(i));
       					map.put(KEY_ICON,icons.getString(i));
       					//Log.v("MICON",icons.getString(i));
                          	weatherDataCollection.add(map);
       			}
       	
       			bindingData = new BinderData(EyeAnatomy.this,weatherDataCollection,screenType);
             		list = (ListView) findViewById(R.id.list);
       			list.setAdapter(bindingData);
       			// Click event for single list row
       			list.setOnItemClickListener(new OnItemClickListener() {

       	        public void onItemClick(AdapterView<?> parent, View view,
       						int position, long id) {

       					Intent i = new Intent();
       					i.setClass(EyeAnatomy.this, DetailView.class);

       					// parameters
       					i.putExtra("position", String.valueOf(position + 1));
       					// start the sample activity
       					//i.putExtra("DINFO",weatherDataCollection.get(position).get(KEY_CITY));
       					i.putExtra("INFO",iconType);
       					i.putExtra("DINFO",weatherDataCollection.get(position).get(KEY_CITY));
       					
       					startActivity(i);
       				}
       			});

       		}
       		catch (Exception ex) {
//       			Log.e("Error", "Loading exception");
       		}
                
                
                
            }
           });
/////////////////////////////////////////////////////////////up ends//////////////////////////////////////////
        ImageButton eye_down=(ImageButton)findViewById(R.id.eye_down);
        eye_down.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	iconType = "Ad";
//                Log.v("ImageButton", "Clicked!");
                try {
        			getJsonData(iconType);
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		} catch (JSONException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
                
             
                
                try {
       		     weatherDataCollection = new ArrayList<HashMap<String,String>>();
       	        //Log.v("Icons",icons.toString());
       			HashMap<String,String> map = null;
       						
       			for (int i = 0; i < headers.length(); i++) {
       				 
       				   	map = new HashMap<String,String>(); 				
       					map.put(KEY_ID,String.valueOf(i+1));
       				   	//Log.v("MID",String.valueOf(i+1));
       					map.put(KEY_CITY,headers.getString(i));
       					//Log.v("MCITY",headers.getString(i));
       					map.put(KEY_ICON,icons.getString(i));
       					//Log.v("MICON",icons.getString(i));
                          	weatherDataCollection.add(map);
       			}
       	
       			bindingData = new BinderData(EyeAnatomy.this,weatherDataCollection,screenType);
             		list = (ListView) findViewById(R.id.list);
       			list.setAdapter(bindingData);
       			// Click event for single list row
       			list.setOnItemClickListener(new OnItemClickListener() {

       	        public void onItemClick(AdapterView<?> parent, View view,
       						int position, long id) {

       					Intent i = new Intent();
       					i.setClass(EyeAnatomy.this, DetailView.class);

       					// parameters
       					i.putExtra("position", String.valueOf(position + 1));
       					// start the sample activity
       					//i.putExtra("DINFO",weatherDataCollection.get(position).get(KEY_CITY));
       					i.putExtra("INFO",iconType);
       					i.putExtra("DINFO",weatherDataCollection.get(position).get(KEY_CITY));
       					
       					startActivity(i);
       				}
       			});

       		}
       		catch (Exception ex) {
//       			Log.e("Error", "Loading exception");
       		}
                
                
                
            }
           });
/////////////////////////////////////////////////////////////////////////////down ends////////////////////////
        ImageButton eye_back=(ImageButton)findViewById(R.id.eye_back);
        eye_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	iconType = "Ab";
//                Log.v("ImageButton", "Clicked!");
                try {
        			getJsonData(iconType);
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		} catch (JSONException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
                
             
                
                try {
       		     weatherDataCollection = new ArrayList<HashMap<String,String>>();
       	        //Log.v("Icons",icons.toString());
       			HashMap<String,String> map = null;
       						
       			for (int i = 0; i < headers.length(); i++) {
       				 
       				   	map = new HashMap<String,String>(); 				
       					map.put(KEY_ID,String.valueOf(i+1));
       				   	//Log.v("MID",String.valueOf(i+1));
       					map.put(KEY_CITY,headers.getString(i));
       					//Log.v("MCITY",headers.getString(i));
       					map.put(KEY_ICON,icons.getString(i));
       					//Log.v("MICON",icons.getString(i));
                          	weatherDataCollection.add(map);
       			}
       	
       			bindingData = new BinderData(EyeAnatomy.this,weatherDataCollection,screenType);
             		list = (ListView) findViewById(R.id.list);
       			list.setAdapter(bindingData);
       			// Click event for single list row
       			list.setOnItemClickListener(new OnItemClickListener() {

       	        public void onItemClick(AdapterView<?> parent, View view,
       						int position, long id) {

       					Intent i = new Intent();
       					i.setClass(EyeAnatomy.this, DetailView.class);

       					// parameters
       					i.putExtra("position", String.valueOf(position + 1));
       					// start the sample activity
       					//i.putExtra("DINFO",weatherDataCollection.get(position).get(KEY_CITY));
       					i.putExtra("INFO",iconType);
       					i.putExtra("DINFO",weatherDataCollection.get(position).get(KEY_CITY));
       					
       					startActivity(i);
       				}
       			});

       		}
       		catch (Exception ex) {
//       			Log.e("Error", "Loading exception");
       		}
                
                
                
            }
           });
 //////////////////////////////////////////////////////////////back ends/////////////////////////////////////
        
        
        iconType = "A";
//        Toast.makeText(this, iconType, Toast.LENGTH_SHORT).show();     
        
//        try {
//			getJsonData(iconType);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        
        
        
		try {
		     weatherDataCollection = new ArrayList<HashMap<String,String>>();
	        //Log.v("Icons",icons.toString());
			HashMap<String,String> map = null;
						
			for (int i = 0; i < headers.length(); i++) {
				 
				   	map = new HashMap<String,String>(); 				
					map.put(KEY_ID,String.valueOf(i+1));
				   	//Log.v("MID",String.valueOf(i+1));
					map.put(KEY_CITY,headers.getString(i));
					//Log.v("MCITY",headers.getString(i));
					map.put(KEY_ICON,icons.getString(i));
					//Log.v("MICON",icons.getString(i));
                   	weatherDataCollection.add(map);
			}
	
			bindingData = new BinderData(this,weatherDataCollection,screenType);
      		list = (ListView) findViewById(R.id.list);
			list.setAdapter(bindingData);
			// Click event for single list row
			list.setOnItemClickListener(new OnItemClickListener() {

	        public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					Intent i = new Intent();
					i.setClass(EyeAnatomy.this, DetailView.class);

					// parameters
					i.putExtra("position", String.valueOf(position + 1));
					// start the sample activity
					//i.putExtra("DINFO",weatherDataCollection.get(position).get(KEY_CITY));
					i.putExtra("INFO",iconType);
					i.putExtra("DINFO","Generic");
					
					startActivity(i);
				}
			});

		}
		catch (Exception ex) {
//			Log.e("Error", "Loading exception");
		}
    }
    
//    public void onResume()
//    {
//    	super.onResume();
//    	SearchQuery sq = ((SearchQuery)getApplicationContext());
//		String query = sq.getSearchText();
//		bindingData.getFilter().filter(query);
//		sq.setSearchText("");
//    }
		
					
		//Enabling Search Filter
		/*
        inputSearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
				// When user changed the Text
				SubDetail.this.bindingData.getFilter().filter(cs);	
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub							
			}
		});
    }*/
    
    //Code to Parse Local Json File (/assets/subNav.json)
    public void getJsonData(String key) throws IOException, JSONException
    {
    	InputStream is = this.getAssets().open("subNavNew.json");
    	int size = is.available();
    	byte[] buff = new byte[size];
    	is.read(buff);
    	is.close();
    	String jsonData = new String(buff);
    	
    	JSONObject jsonObj = new JSONObject(jsonData).optJSONObject(key);
    	
    	headers = jsonObj.getJSONArray("Headers");
        icons = jsonObj.getJSONArray("icons");
        
        //Sorting Headers
        List<String> jsonValues = new ArrayList<String>();
        for (int i = 0; i < headers.length(); i++)
           jsonValues.add(headers.getString(i));
        Collections.sort(jsonValues);
         headers = new JSONArray(jsonValues);
         
         //Sorting icons
         List<String> jsonIcons = new ArrayList<String>();
         for (int i = 0; i < icons.length(); i++)
            jsonIcons.add(icons.getString(i));
         Collections.sort(jsonIcons);
          icons = new JSONArray(jsonIcons);
    	
    	//Log.v("JSON", jsonObj.toString());
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        
        return super.onCreateOptionsMenu(menu);
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

///////////////////End of the typeface class/////////////////////////////////////////////////////////////////

}
