package ru.lagner.dailynotifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TimeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		
		Log.i(logTag, "time was changed -> reset last activity time");
			
	}
	
	final private String logTag = "TimeReceiver";
}
