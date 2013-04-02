package ru.lagner.dailynotifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		// вызвать активити и добавить дневную ману
		
		Log.i(logTag, "timer tick");
	}
	
	private static final String logTag = "AlarmReceiver";
}
