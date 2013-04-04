package ru.lagner.dailynotifier.receivers;

import ru.lagner.dailynotifier.Scheduler;
import ru.lagner.dailynotifier.SettingsProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TimeChangedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {		
		Log.w(logTag, "date or time changed manually. Reset alarm sheduler");
		
		// change last activity time to now
		SettingsProvider.setLastActivityTime(context);
		// reset scheduler
		Scheduler sch = new Scheduler(context);
		// assume that it already running
		sch.restart();		
		// next tick will in period time
	}
	
	private static final String logTag = TimeChangedReceiver.class.getSimpleName();
}
