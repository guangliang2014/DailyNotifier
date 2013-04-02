package ru.lagner.dailynotifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
						
		// check last activity not implemented
		
		int points = MainActivity.increasePoints(context);
		Log.i(logTag, "Timer tick. Increase points to " + points);
	}
	
	private static final String logTag = "AlarmReceiver";
}
