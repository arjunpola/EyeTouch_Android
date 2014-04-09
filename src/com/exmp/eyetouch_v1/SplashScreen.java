package com.exmp.eyetouch_v1;

import com.exmp.eyetouch_v1.MainActivity;
import com.exmp.eyetouch_v1.SplashScreen;
import com.exmp.eyetouch_v1.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;


public class SplashScreen extends Activity{

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.splash_screen);
		
		
		
		
	    
	    Thread timer = new Thread(){
			public void run(){
			try{
				sleep(2500);
				
			} catch(InterruptedException e){
				e.printStackTrace();
			}finally{
				Intent openMain = new Intent(SplashScreen.this,MainActivity.class);
				startActivity(openMain);
			}
		}
			
		};
		timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();		
		finish(); 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

