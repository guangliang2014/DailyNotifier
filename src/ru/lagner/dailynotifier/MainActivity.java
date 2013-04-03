package ru.lagner.dailynotifier;

import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
				
		context = getApplicationContext();
		
		SettingsProvider.setLastActivityTime(context);
		
		if (SettingsProvider.isFirstTime(context))
			resetDailyAlarm(context);			
		
		txt_lastActivity = (TextView) findViewById(R.id.txt_showTime);
		Date lastActivity = new Date(SettingsProvider.getLastActivityTime(context));
		txt_lastActivity.setText(lastActivity.toString());
		
		txt_points = (TextView) findViewById(R.id.txt_points);
		int points = SettingsProvider.getPoints(context);
		txt_points.setText(String.valueOf(points));
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		SettingsProvider.setLastActivityTime(context);
	}
		
	@Override
	protected void onPause()
	{
		super.onPause();
		SettingsProvider.setLastActivityTime(context);
	}
	
	
	public static void resetDailyAlarm(Context context) {
		Log.i(logTag, "reset daily alarm");
		
		String updateId = SettingsProvider.NOTIFIER_UPDATE_ID;
		String notifyId = SettingsProvider.NOTIFIER_NOTIFY_ID;
		
		Intent uIntent = new Intent(updateId);
		Intent nIntent = new Intent(notifyId);
				
		PendingIntent uPendIntent = PendingIntent.getBroadcast(context, 0, uIntent, 0);
		PendingIntent nPendIntent = PendingIntent.getBroadcast(context, 0, nIntent, 0);
		
		AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		// cancel previous alarm
		alarm.cancel(uPendIntent);
		alarm.cancel(nPendIntent);
		
		// and setup new. Start time now
		long period = SettingsProvider.PERIOD;
		// for points update
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + period, period, uPendIntent);
		
		// for notify 
		long trigger = System.currentTimeMillis() + (long)(period * SettingsProvider.NOTIFY_FACTOR);
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, trigger, period, nPendIntent);
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
			
		Notification notif = builder.build();
		mn.notify(0, notif);		
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
		
		return false;
	}
	
	
	
	// Private
	
	private Context context;
	private TextView txt_points;
	private TextView txt_lastActivity;
	private static final String logTag = MainActivity.class.getSimpleName();
}
