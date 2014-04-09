package com.exmp.eyetouch_v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.exmp.eyetouch_v1.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

public class BinderData extends BaseAdapter implements Filterable {
	

	// XML node keys // parent node
    static final String KEY_ID = "id";
    static final String KEY_CITY = "title";
    static final String KEY_ICON = "icon";
    
	
	LayoutInflater inflater;
	ImageView thumb_image;
	List<HashMap<String,String>> weatherDataCollection;
	List<HashMap<String,String>> mOriginalValues;
	ViewHolder holder;
	String screenType = "SMALL";
	public BinderData() {
		// TODO Auto-generated constructor stub
		
	}
	
	public BinderData(Activity act, List<HashMap<String,String>> map,String type) {
		
		this.weatherDataCollection = map;
		screenType = type;
		inflater = (LayoutInflater) act
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}
	

	public int getCount() {
		// TODO Auto-generated method stub
//		return idlist.size();
		return weatherDataCollection.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		 
		View vi=convertView;
	    if(convertView==null){
	     
	      vi = inflater.inflate(R.layout.list_row, null);
	      
	      holder = new ViewHolder();
	      
	      //holder.hsv = (HorizontalScrollView)vi.findViewById(R.id.hsv);
	      holder.tvCity = (TextView)vi.findViewById(R.id.tvCity); // city name
	     
	      if(screenType.equals("LAEGE"))
	    	 holder.tvCity.setTextSize(TypedValue.COMPLEX_UNIT_DIP, (float) 35);

	      holder.tvWeatherImage =(ImageView)vi.findViewById(R.id.list_image); // thumb image
	      
	 
	      vi.setTag(holder);
	    }
	    else{
	    	
	    	holder = (ViewHolder)vi.getTag();
	    }

	      // Setting all values in listview
	      holder.tvCity.setText(weatherDataCollection.get(position).get(KEY_CITY));
	      //holder.tvWeather.setText(weatherDataCollection.get(position).get(KEY_CONDN));
	      
	      //Setting an image
	      String uri = "drawable/"+ weatherDataCollection.get(position).get(KEY_ICON);
	      int imageResource = vi.getContext().getApplicationContext().getResources().getIdentifier(uri, null, vi.getContext().getApplicationContext().getPackageName());
	      Drawable image = vi.getContext().getResources().getDrawable(imageResource);
	      holder.tvWeatherImage.setImageDrawable(image);
	      
	      return vi;
	}
	
	/*
	 * 
	 * */
	static class ViewHolder{
		
		public HorizontalScrollView hsv;

		TextView tvCity;
		
		//TextView tvWeather;
		ImageView tvWeatherImage;
		
	}

	//Implementation for Custom Filter
	
	Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
         FilterResults filterResults = new FilterResults();   
         List<HashMap<String,String>> tempList=new ArrayList<HashMap<String,String>>();
         //constraint is the result from text you want to filter against. 
         //objects is your data set you will filter from
 
         //        constraint = constraint.toString().toLowerCase();
         
         if (mOriginalValues == null) {
             mOriginalValues = new ArrayList<HashMap<String,String>>(weatherDataCollection); // saves the original data in mOriginalValues
         }
         
         if(constraint == null || constraint.length() == 0){
        	 filterResults.count = mOriginalValues.size();
             filterResults.values = mOriginalValues;
         }
         else{
        	 if(mOriginalValues!=null) {
             int length = mOriginalValues.size();
             int i=0;
                while(i<length){
                	HashMap<String,String> item = mOriginalValues.get(i);
                    //do whatever you wanna do here
                    //adding result set output array     
                	if(item.get(KEY_CITY).toString().toLowerCase().startsWith(constraint.toString().toLowerCase()))
                    tempList.add(item);

                    i++;
                }
                //following two lines is very important
                //as publish result can only take FilterResults objects
                filterResults.values = tempList;
                filterResults.count = tempList.size();
          }
         }
          return filterResults;
      }

      @SuppressWarnings("unchecked")
      @Override
      protected void publishResults(CharSequence contraint, FilterResults results) {
          weatherDataCollection = (ArrayList<HashMap<String,String>>) results.values;
          if (results.count > 0) {
           notifyDataSetChanged();
          } else {
              notifyDataSetInvalidated();
          }  
      }
     };
	
	@Override
	public Filter getFilter() {
		return myFilter;
	}
	
	
}
