package ru.lagner.dailynotifier.receivers;

import java.util.Date;
import ru.lagner.dailynotifier.SettingsProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DailyNotifyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		Date now = new Date();
		long delta = now.getTime() - SettingsProvider.getLastActivityTime(context);
		float norma = SettingsProvider.PERIOD * SettingsProvider.NOTIFY_FACTOR;
		
		if (!(delta < norma)) {
			Log.i(logTag, "notify user about remaining time");			
			// уведомить пользователя что у него мало времени
			// нужно зайти чтобы получить бонус

		} 	
	}
	
	
	private static final String logTag = DailyNotifyReceiver.class.getSimpleName();
}
