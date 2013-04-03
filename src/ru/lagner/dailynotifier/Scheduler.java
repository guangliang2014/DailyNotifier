package ru.lagner.dailynotifier;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class Scheduler {
	
	public static final String ALARM_ID = "ru.lagner.dailynotify.alarm";
	public static final String NOTIFY_ID = "ru.lagner.dailynotify.notify";
	
	
	public Scheduler(Context context) {
		this.context = context;
		pref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
		am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	}
	
	
	public long getPeriod() {
		return pref.getLong(PERIOD, AlarmManager.INTERVAL_DAY);
	}
	
	public void setPeriod(long period) {
		if (period <= 0) {
			Log.w(logTag, "wrong period parameter. Skip it");
			return;
		}
		editor = pref.edit();
		editor.putLong(PERIOD, period);
		editor.apply();		
	}
	
	public long getPreNotifyTime() {
		return pref.getLong(PRENOTIFY, AlarmManager.INTERVAL_HOUR);
	}
	
	public void setPreNotifyTime(long t) {
		if (t <= 0) {
			Log.w(logTag, "wrong pre notify parameter. Skip it");
			return;
		}
		editor = pref.edit();
		editor.putLong(PRENOTIFY, t);
		editor.apply();
	}
	
	
	/// returns start time, or -1 
	/// if scheduler was not started
	public long getStartTime() {
		if (!isEnabled()) {
			return -1;
		}
		
		return pref.getLong(START_TIME, -1);
	}
	
	
	public boolean isEnabled() {
		return pref.getBoolean(ENABLED, false);
	}
	
	
	
	public boolean start() {
		if (isEnabled()) {
			Log.w(logTag, "notification already enabled");
			return false;
		}
		
		long startTime = System.currentTimeMillis() + getPeriod();		
		enableRepeating(ALARM_ID, startTime);
		
		setStartTime(startTime);
		
		startTime -= getPreNotifyTime();
		enableRepeating(NOTIFY_ID, startTime);
		
		editor = pref.edit();
		editor.putBoolean(ENABLED, true);
		editor.apply();

		Log.i(logTag, "Schaduler was successfully started");
		return true;
	}
	
	public boolean stop() {
		if (!isEnabled()) {
			Log.w(logTag, "notification already disabled");
			return false;
		}
		
		disableRepeating(ALARM_ID);
		disableRepeating(NOTIFY_ID);
		
		setStartTime(-1);
		
		editor = pref.edit();
		editor.putBoolean(ENABLED, false);
		editor.apply();
		
		Log.i(logTag, "Schaduler was successfully stoped");		
		return true;
	}
	
	public boolean restart() {
		return stop() && start();
	}
	
	
	
	
	private void enableRepeating(String id, long startTime) {
		Intent intent = new Intent(id);
		PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		
		am.setRepeating(AlarmManager.RTC_WAKEUP, startTime, getPeriod(), pIntent);
	}
	
	private void disableRepeating(String id) {
		Intent intent = new Intent(id);
		PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		
		am.cancel(pIntent);
	}
	
	private void setStartTime(long t) {
		editor = pref.edit();
		editor.putLong(START_TIME, t);
		editor.apply();
	}
	
	
	
	private Context context;
	private SharedPreferences pref;
	private AlarmManager am;
	private Editor editor;
	
	
	private static final String SETTINGS = "Scheduler";
	private static final String PERIOD = "Period";
	private static final String PRENOTIFY = "Prenotify";
	private static final String START_TIME = "StartTime";
	private static final String ENABLED = "isEnabled";
	
	private static final String logTag = Scheduler.class.getSimpleName();
}
