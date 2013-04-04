package ru.lagner.dailynotifier;

import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
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
		sch = new Scheduler(context);
		
		SettingsProvider.setLastActivityTime(context);
		
		if (SettingsProvider.isFirstTime(context)) {
			sch.start();
		}
		
		txt_lastActivity = (TextView) findViewById(R.id.txt_lastActivity);
		txt_startTime = (TextView) findViewById(R.id.txt_startTime);
		txt_period = (TextView) findViewById(R.id.txt_period);
		txt_prenotify = (TextView) findViewById(R.id.txt_preNotify);
		txt_isEnabled = (TextView) findViewById(R.id.txt_isEnabled);
		
		btn_start = (Button) findViewById(R.id.btn_start);
		btn_stop = (Button) findViewById(R.id.btn_stop);
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
	private TextView txt_lastActivity;
	private TextView txt_startTime;
	private TextView txt_period;
	private TextView txt_prenotify;
	private TextView txt_isEnabled;
	private Button btn_start;
	private Button btn_stop;
	private Scheduler sch;
	
	private static final String logTag = MainActivity.class.getSimpleName();
}
