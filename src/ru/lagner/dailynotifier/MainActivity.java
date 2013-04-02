package ru.lagner.dailynotifier;

import java.util.Calendar;
import java.util.Date;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public static final String SETTINGS = "appSettings";
	public static final String LAST_ACTIVITY = "lastactivity";
	public static final String POINTS = "totalPoints";
	public static final String FIRST_TIME = "isFirstTime";
	public static final String DAILY_NOTIFIER_ID = "ru.lagner.dailynotifier";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txt_lastActivity = (TextView)findViewById(R.id.txt_showTime);
				
		context = getApplicationContext();
		// put now to the last activity time
		updateLastActivityTime(context);
				
		if (checkIfItFirst())
		{
			Log.i(logTag, "First app start. Set daily notifier");
			resetDailyAlarm(context);
		}
		
		updateTextView();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		updateTextView();
	}
	
	
	@Override
	protected void onPause()
	{
		super.onPause();
		updateLastActivityTime(getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override 
	public boolean onOptionsItemSelected(MenuItem item)
	{
		super.onOptionsItemSelected(item);
		
		int value = item.getItemId();
		Log.i(logTag, "Options item selected: " + value);
		
		switch(value)
		{
		case (R.id.reset_timer):
			resetAll();
			return true;			
		}
		
		return false;
	}
	
	
	public static void updateLastActivityTime(Context context)
	{
		SharedPreferences pref = context.getSharedPreferences(SETTINGS, MODE_PRIVATE);
		Date now = new Date();
		
		Editor editor = pref.edit();
		editor.putLong(LAST_ACTIVITY, now.getTime());
		editor.commit();
		
		Log.i(logTag, "last activity time was updated to " + now.toString());
	}
	
	public static void resetDailyAlarm(Context context)
	{
		Log.i(logTag, "reset daily alarm");
		
		Intent intent = new Intent(DAILY_NOTIFIER_ID);
		PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

		AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		// cancel previous alarm
		alarm.cancel(pIntent);
		
		// and setup new. Start time now
		Calendar calendar = Calendar.getInstance();		
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pIntent);
	}
	
	public static void resetPoints(Context context)
	{
		Log.i(logTag, "reset points to 0");
		SharedPreferences pref = context.getSharedPreferences(SETTINGS, MODE_PRIVATE);
		
		Editor e = pref.edit();
		e.putInt(POINTS, 0);
		e.commit();
	}
	
	/// if last activity was earler then interval,
	/// returns false, 
	/// otherwise returns true
	public static boolean checkLastActivity(Context context)
	{
		Log.i(logTag, "check last activity");
		
		SharedPreferences pref = context.getSharedPreferences(SETTINGS, MODE_PRIVATE);
		
		if (!pref.contains(LAST_ACTIVITY))
		{
			updateLastActivityTime(context);
			return false;
		}
		
		long last = pref.getLong(LAST_ACTIVITY, 0);
		Date now = new Date();
		
		if ((now.getTime() - last) < interval)
		{
			return true;
		}
		
		return false;
	}	
	
	public static int increasePoints(Context context) {		
		SharedPreferences pref = context.getSharedPreferences(SETTINGS, MODE_PRIVATE);
		int points = pref.getInt(POINTS, 0);
		points += profit;
		
		Editor editor = pref.edit();
		editor.putInt(POINTS, points);
		editor.commit();
		
		return points;
	}
	
	
	public static void notifyUser(Context context, String message) {
		
		String title = context.getResources().getString(R.string.app_name);
		
		Intent intent = new Intent(context, MainActivity.class);
		
		NotificationManager mn = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
			.setSmallIcon(R.drawable.ic_launcher)
			.setAutoCancel(true)
			.setTicker(message)
			.setContentText(message)
			.setContentIntent(PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT))
			.setWhen(System.currentTimeMillis())
			.setContentTitle(title)
			.setDefaults(Notification.DEFAULT_ALL);
			
		Notification notif = builder.getNotification();
		mn.notify(0, notif);		
	}
	
	
	private boolean checkIfItFirst()
	{
		SharedPreferences pref = getPreferences(MODE_PRIVATE);
		// Check if it first start of program
		if (!pref.contains(FIRST_TIME))
		{			
			Editor editor = pref.edit();
			editor.putBoolean(FIRST_TIME, false);
			editor.commit();
			return true;
		}		
		return false;
	}
	
	private void resetAll()
	{
		Log.i(logTag, "Reset all timers");	
		updateLastActivityTime(context);
		resetDailyAlarm(context);
		resetPoints(context);
		updateTextView();
	}
	
	private void updateTextView()
	{
		SharedPreferences pref = context.getSharedPreferences(SETTINGS, MODE_PRIVATE);
		
		long value = pref.getLong(LAST_ACTIVITY, 0);		
		Date date = new Date(value);
		
		txt_lastActivity.setText(date.toString());
	}
	
	
	
	// Private
	
	private TextView txt_lastActivity;
	private Context context;
	// notify interval in miliseconds
	private static final long interval = 3*60*1000;
	private static final int profit = 100;
	private static final String logTag = MainActivity.class.getSimpleName();
}
