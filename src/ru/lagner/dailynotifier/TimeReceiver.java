package ru.lagner.dailynotifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TimeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		Log.i(logTag, "global time was changed -> reset last activity time");		

		MainActivity.updateLastActivityTime(context);
		MainActivity.resetDailyAlarm(context);
	}
	
	private final static String logTag = "TimeReceiver";
}
