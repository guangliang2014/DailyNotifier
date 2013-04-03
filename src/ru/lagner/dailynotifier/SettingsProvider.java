package ru.lagner.dailynotifier;

import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public abstract class SettingsProvider {
	
	public static final String SETTINGS = "AppSettings";	
	public static final String POINTS = "TotalPoints";	
	public static final int BONUS_POINTS = 100;
	
	public static final String LAST_ACTIVITY = "LastActivityTime";
	
	// period for bonus points. In mili seconds
	public static final long PERIOD = 2*60*1000;
	public static final float NOTIFY_FACTOR = 0.9f;
	
	public static final String NOTIFIER_UPDATE_ID = "ru.lagner.dailynotifier.update";
	public static final String NOTIFIER_NOTIFY_ID = "ru.lagner.dailynotifier.notify";
	
	public static final String IS_FIRST_TIME = "isFirstTime";
	
	
	public static long getLastActivityTime(Context context) {
		SharedPreferences pref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
		
		if (!pref.contains(LAST_ACTIVITY)) {
			Log.i(logTag, "last_activity element doesn't exist. Create and return now.");
			setLastActivityTime(context);
		}
		
		return pref.getLong(LAST_ACTIVITY, 0);
	}
	
	public static void setLastActivityTime(Context context, long date) {
		SharedPreferences pref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
		
		Editor e = pref.edit();
		e.putLong(LAST_ACTIVITY, date);
		e.commit();
	}
	
	public static void setLastActivityTime(Context context, Date date) {
		setLastActivityTime(context, date.getTime());
	}
	
	public static void setLastActivityTime(Context context) {
		setLastActivityTime(context, (new Date()).getTime());
	}
	
	
	
	public static int getPoints(Context context) {
		SharedPreferences pref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
		
		return pref.getInt(POINTS, 0);		
	}
	
	public static void setPoints(Context context, int value) {
		SharedPreferences pref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
		
		Editor e = pref.edit();
		e.putInt(POINTS, value);
		e.commit();
	}	
	
	
	// check is first time app running 
	// if true, update value to false
	public static boolean isFirstTime(Context context) {
		SharedPreferences pref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
		
		if (!pref.contains(IS_FIRST_TIME)) {
			Editor e = pref.edit();
			e.putBoolean(IS_FIRST_TIME, false);
			e.commit();
			return true;
		}
		
		return false;
	}
	
	
	private static final String logTag = SettingsProvider.class.getSimpleName();
}
