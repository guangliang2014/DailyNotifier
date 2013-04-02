package ru.lagner.dailynotifier.receivers;

import java.util.Date;

import ru.lagner.dailynotifier.SettingsProvider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DailyAlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		Date now = new Date();
		
		long delta = now.getTime() - SettingsProvider.getLastActivityTime(context);
		
		if (delta < SettingsProvider.PERIOD) {
			Log.i(logTag, "add bonus points");
			// начислить бонусные балы 
			// уведомить об этом
		} else {
			Log.i(logTag, "bonus points are losted");
			// просохатил балы
			// уведомить об этом
		}
	}

	private static final String logTag = DailyAlarmReceiver.class.getSimpleName();
}
