package com.exmp.eyetouch_v1;

import com.exmp.eyetouch_v1.SearchQuery;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

public class SearchResultsActivity extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
       
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

    	 if (Intent.ACTION_VIEW.equals(intent.getAction())) {
	            // handles a click on a search suggestion; launches activity to show word
//	            Intent wordIntent = new Intent(this, WordActivity.class);
//	            wordIntent.setData(intent.getData());
//	            startActivity(wordIntent);
	              	
	        } else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	            // handles a search query
	            String query = intent.getStringExtra(SearchManager.QUERY);
	            
	            SearchQuery sq = ((SearchQuery)getApplicationContext());
	            sq.setSearchText(query);
	            finish();
	        }
    }


}