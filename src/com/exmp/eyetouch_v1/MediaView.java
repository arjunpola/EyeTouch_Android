package com.exmp.eyetouch_v1;

import com.exmp.eyetouch_v1.SubDetail;
import com.exmp.eyetouch_v1.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.view.View;

public class MediaView extends Activity{
	

    ListView listView ;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_list);
        
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        
        // Defined Array values to show in ListView
        String[] values = new String[] { 
                                         "Photos", 
                                         "Videos" 
                                        };

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter); 
        
        // ListView Item Click Listener
        
        listView.setOnItemClickListener(new OnItemClickListener() {

              @Override
              public void onItemClick(AdapterView<?> parent, View view,
                 int position, long id) {
                
               // Show Alert 
                /*Toast.makeText(getApplicationContext(),
                  "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                  .show();*/
                if (position==0){
                	Intent i = new Intent(MediaView.this, SubDetail.class);
                	i.putExtra("INFO","M_P");
                	startActivity(i);
                }
                if (position==1){
                	Intent i = new Intent(MediaView.this, SubDetail.class);
                	i.putExtra("INFO","M_V");
                	startActivity(i);
                }
              }

         }); 
    }
}
