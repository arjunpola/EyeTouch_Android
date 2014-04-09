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
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import android.util.LruCache;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.AdapterView.OnItemClickListener;

import com.exmp.eyetouch_v1.BinderData;
import com.exmp.eyetouch_v1.DetailView;
import com.exmp.eyetouch_v1.SearchQuery;
import com.exmp.eyetouch_v1.SubDetail;
import com.exmp.eyetouch_v1.R;

/**
 * 
 * @author arjunpola
 *
 */

/*
 * Anatomy			= A
 * Report			= R
 * Drugs			= D
 * Disease Index	= DI
 * Classifications	= C
 * History          = H
 * 
 */

public class SubDetail extends Activity {
	
	
		// XML node keys
	    static final String KEY_ID = "id";
	    static final String KEY_CITY = "title";
	    static final String KEY_ICON = "icon";
	   public  String screenType = "SMALL";
	    
	    // List items 
	    ListView list;
	    String iconType;
	    EditText inputSearch;
	    BinderData bindingData,originalData;
	    JSONArray headers,icons,rHeaders,rIcons;
	    
	    List<String> jsonValues,jsonIcons;
	    
List<HashMap<String,String>> weatherDataCollection;
	  
	    //Android Code
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	        
	       jsonValues = new ArrayList<String>();
	       jsonIcons = new ArrayList<String>(); 
	       
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
				screenType = "LARGE";
}
		    
	        //getActionBar().setDisplayHomeAsUpEnabled(true);
	        	        
	        
	        Bundle b = (Bundle) getIntent().getExtras();
	        iconType = b.getString("INFO");
	       // Log.v("INFO", iconType);
//	        String queryU = "http://122.167.37.169:8080/Eye_Touch/gdrugs/getIndex?collection=".concat(iconType);
	     //   Log.v("INFO", queryU);
//	        new FetchRemoteContent().execute("http://122.167.37.169:8080/Eye_Touch/trial.php",iconType);
//	        new FetchRemoteContent().execute("https://www.google.com/calendar/feeds/sjm6mdaoghv5kipc2p80jrgsc0%40group.calendar.google.com/public/basic?alt=json");
//	        new FetchRemoteContent().execute("http://122.167.37.169:8080/Eye_Touch/gdrugs/searchdrugs?drug=Acetyl");
	//         new  FetchRemoteContent().execute(queryU);
//	        AsyncTask<String, Void, Void> GJD = new getJSON().execute(iconType);
//	        Toast.makeText(this, iconType, Toast.LENGTH_SHORT).show();
//			while(FRC.getStatus() == AsyncTask.Status.RUNNING)
//				;
				        
	        
	        try {
	        	getJsonData(iconType);
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
	        

	    }
	    
	    public void updateUI()
	    {
			try {
				

		        weatherDataCollection = new ArrayList<HashMap<String,String>>();
		        
	            
	            //Log.v("Icons",icons.toString());
				HashMap<String,String> map = null;
				
				for (int i = 0; i < headers.length(); i++) {
					 
					   	map = new HashMap<String,String>(); 				
						
					   	map.put(KEY_ID,String.valueOf(i+1));
						map.put(KEY_CITY,headers.getString(i));
						map.put(KEY_ICON,icons.getString(i));

						weatherDataCollection.add(map);
				}
				
			list = (ListView) findViewById(R.id.list);
				
				
				
				
		
				bindingData = new BinderData(this,weatherDataCollection,screenType);
				originalData = bindingData;
							
	


				list.setAdapter(bindingData);

				// Click event for single list row
				list.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						Intent i = new Intent();
						i.setClass(SubDetail.this, DetailView.class);

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
//				Log.e("Error", "Loading exception");
			}
	    }
	    
	    public void onResume()
	    {
	    	super.onResume();
	    	try {
				sortHeaders();
			} catch (JSONException e) {
				e.printStackTrace();
			}
	    	SearchQuery sq = ((SearchQuery)getApplicationContext());
			String query = sq.getSearchText();
			bindingData.getFilter().filter(query);
			sq.setSearchText("");
	    }
			
						
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
            
            for(int i=0;i<headers.length();i++)
            	jsonValues.add(headers.getString(i));
            
            for(int i=0;i<icons.length();i++)
            	jsonIcons.add(icons.getString(i));
            
            	sortHeaders();
	    	//Log.v("JSON", jsonObj.toString());
	    }
	    
	    public void sortHeaders() throws JSONException
	    {
	    	//Sorting Headers            
            Collections.sort(jsonValues);
             headers = new JSONArray(jsonValues);
             
             //Sorting icons
             Collections.sort(jsonIcons);
              icons = new JSONArray(jsonIcons);
              
//              Log.v("Complete List", headers.toString());
              
              updateUI();
	    }
	    
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.options, menu);
	        
	        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
	            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	            SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
	            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	            searchView.setIconifiedByDefault(false);
	        }
	        
	        MenuItem menuItem = menu.findItem(R.id.search);
	        
	        MenuItemCompat.setOnActionExpandListener(menuItem, new OnActionExpandListener() {
		        
	        	public boolean onMenuItemActionCollapse(MenuItem item) {
		            // Do something when collapsed
	        		bindingData.getFilter().filter("");
		            return true;  // Return true to collapse action view
		        }
		        

		        @Override
		        public boolean onMenuItemActionExpand(MenuItem item) {
		            return true;  // Return true to expand action view
		        }
		    });
	        
	        return true;
	    }
	    
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	            case R.id.search:
//	            	Log.v("Item", "Clicked");
	                onSearchRequested();
	                return true;
	            default:
	                return false;
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
//TODO Auto-generated method stub
p.setTypeface(mTypeface);
//Note: This flag is required for proper typeface rendering
p.setFlags(p.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
}
}

///////////////////End of the typeface class/////////////////////////////////////////////////////////////////

//public class getJSON extends AsyncTask<String,Void,Void>
//{
//	JSONArray lH,lI;
//
//	@Override
//	protected Void doInBackground(String... params) {
//		
//    	InputStream is = null;
//		try {
//			
//			is = getApplicationContext().getAssets().open("subNavNew.json");
//			int size = is.available();
//			byte[] buff = new byte[size];
//			is.read(buff);
//	    	is.close();
//	    	
//	    	String jsonData = new String(buff);
//	    	JSONObject jsonObj = new JSONObject(jsonData).optJSONObject(params[0]);
//	    	lH = jsonObj.getJSONArray("Headers");
//	        lI = jsonObj.getJSONArray("icons");
//	        
//	        
//	          
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	protected void onPostExecute() throws JSONException
//	{
//		
////        for (int i = 0; i < headers.length(); i++)
////           jsonValues.add(headers.getString(i));
////
////         
////         //Sorting icons
////         for (int i = 0; i < icons.length(); i++)
////            jsonIcons.add(icons.getString(i));
//		
//	}
//	
//}

//public class FetchRemoteContent extends AsyncTask<String,Void,JSONArray>
//{
//
//	private ProgressDialog progressDialog = new ProgressDialog(SubDetail.this);
//    InputStream inputStream = null;
//    String result = "";
//    JSONArray rH,rI;
//    
//    protected void onPreExecute() {
//        progressDialog.setMessage("Downloading your data...");
//        progressDialog.show();
//       
//        progressDialog.setOnCancelListener(new OnCancelListener() {
//
//			@Override
//			public void onCancel(DialogInterface dialog) {
//				FetchRemoteContent.this.cancel(true);
//				
//			}
//        });
//    }
//	
//    @Override
//	protected JSONArray doInBackground(String... params) {
//		String url_select = params[0];
//		
//        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
//
//     try {
//         // Set up HTTP post
//
//         // HttpClient is more then less deprecated. Need to change to URLConnection
//         HttpClient httpClient = new DefaultHttpClient();
//
//         HttpPost httpPost = new HttpPost(url_select);
//         httpPost.setEntity((HttpEntity) new UrlEncodedFormEntity(param));
//         HttpResponse httpResponse = httpClient.execute(httpPost);
//         HttpEntity httpEntity = httpResponse.getEntity();
//
//         // Read content & Log
//         inputStream = httpEntity.getContent();
//     } catch (UnsupportedEncodingException e1) {
//         Log.e("UnsupportedEncodingException", e1.toString());
//         e1.printStackTrace();
//     } catch (ClientProtocolException e2) {
//         Log.e("ClientProtocolException", e2.toString());
//         e2.printStackTrace();
//     } catch (IllegalStateException e3) {
//         Log.e("IllegalStateException", e3.toString());
//         e3.printStackTrace();
//     } catch (IOException e4) {
//         Log.e("IOException", e4.toString());
//         e4.printStackTrace();
//     }
//     // Convert response to string using String Builder
//     
//     try {
//        
//    	 BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//         StringBuilder sBuilder = new StringBuilder();
//
//         String line = null;
//         while ((line = bReader.readLine()) != null) {
//             sBuilder.append(line);
//         }
//
//         inputStream.close();
//         result = sBuilder.toString();
//         
//         Log.v("Remote Headers",result);
//         
//         while(result == null)
//        	 ;
//         JSONObject jObj = new JSONObject(result);
//         rH = jObj.getJSONArray("Headers");
//         //rI = jObj.getJSONArray("icons");
//
//     } catch (Exception e) {
//         Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
//     }
//		return rH;
//	}
//	
//	 protected void onPostExecute(JSONArray rH) {
//	        //parse JSON data
//	       
//
//	        			
//	        			this.progressDialog.dismiss();
//	        			
//	        			try{
//	        				
//		        			rHeaders = rH;
//		        			for(int i=0;i<rHeaders.length();i++)
//		        			{
//		        				jsonValues.add(rHeaders.getString(i));
//		        				
//		        				if(rIcons == null)
//		        					jsonIcons.add("transparent_strip");
//		        				else
//		        					jsonIcons.add(rIcons.getString(i));
//		        			}
//		        			
//	        			HashMap<String,String> map = null;
//	    				weatherDataCollection.clear();
//	    				
//	    				for (int i = 0; i < jsonValues.size(); i++) {
//	    					 
//	    					   	map = new HashMap<String,String>(); 				
//	    						
//	    					   	map.put(KEY_ID,String.valueOf(i+1));
//	    						map.put(KEY_CITY,jsonValues.get(i));
//	    						map.put(KEY_ICON,jsonIcons.get(i));
//
//	    						weatherDataCollection.add(map);
//	    				}
//	    				
//	    				bindingData = new BinderData(SubDetail.this,weatherDataCollection,screenType);
//	    				originalData = bindingData;
//	    				list.setAdapter(bindingData);
//
//	        			}
//	        			catch (Exception e){
//	        				e.printStackTrace();
//	        			}
//	        			
//	    } // protected void onPostExecute(Void v)
//	
//}

}
