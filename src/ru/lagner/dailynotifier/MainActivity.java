package ru.lagner.dailynotifier;

import java.util.Date;
import java.util.Timer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	
	public static final String SETTINGS = "appSettings";
	public static final String LAST_ACTIVITY = "lastactivity";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				
		context = getApplicationContext();
		// put now to the last activity time
		updateLastActivityTime(context);		
	}
	
	@Override
	protected void onPause()
	{
		super.onStop();
		updateLastActivityTime(getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	public static void updateLastActivityTime(Context context)
	{
		SharedPreferences pref = context.getSharedPreferences(SETTINGS, MODE_PRIVATE);
		Date now = new Date();
		
		Editor editor = pref.edit();
		editor.putLong(LAST_ACTIVITY, now.getTime());
		editor.commit();
		
		Log.i(logTag, "Last activity time was updated to " + now.toString());
	}
	
	
	// Private
	
	private Context context;
	private Timer timer;
	private static final String logTag = "MainActivity";
}
