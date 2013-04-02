package ru.lagner.dailynotifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
						

		if (MainActivity.checkLastActivity(context))
		{
			int points = MainActivity.increasePoints(context);
			Log.i(logTag, "Increase points to " + points);
		}
		else
		{
			Log.i(logTag, "Long time inactive. No bonus points");
		}		
	}
	
	private static final String logTag = "AlarmReceiver";
}
