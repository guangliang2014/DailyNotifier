package ru.lagner.dailynotifier.receivers;

import java.util.Date;

import ru.lagner.dailynotifier.MainActivity;
import ru.lagner.dailynotifier.R;
import ru.lagner.dailynotifier.Scheduler;
import ru.lagner.dailynotifier.SettingsProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DailyNotifyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Date now = new Date();
		Scheduler sch = new Scheduler(context);
		
		long delta = now.getTime() - SettingsProvider.getLastActivityTime(context);
		long norma = sch.getPeriod() - sch.getPreNotifyTime();
		
		if (!(delta < norma)) {
			Log.i(logTag, "notify user about remaining time");			
			String message = context.getResources().getString(R.string.dont_lose_bonus);
			MainActivity.notifyUser(context, message);
		} 	
	}
	
	
	private static final String logTag = DailyNotifyReceiver.class.getSimpleName();
}
