package ru.lagner.dailynotifier;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class TimeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		Log.i(logTag, "global time was changed -> reset last activity time");		
		MainActivity.updateLastActivityTime(context);		
	}
	
	final private String logTag = "TimeReceiver";
}
