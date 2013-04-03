package ru.lagner.dailynotifier.receivers;

import java.util.Date;

import ru.lagner.dailynotifier.MainActivity;
import ru.lagner.dailynotifier.R;
import ru.lagner.dailynotifier.SettingsProvider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DailyAlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		Date now = new Date();
		String message;
		
		long delta = now.getTime() - SettingsProvider.getLastActivityTime(context);
		
		if (delta < SettingsProvider.PERIOD) {
			Log.i(logTag, "add bonus points");
			
			int value = SettingsProvider.getPoints(context) + SettingsProvider.BONUS_POINTS;
			SettingsProvider.setPoints(context, value);
			
			message = context.getResources().getString(R.string.you_got_bonus);

		} else {
			Log.i(logTag, "bonus points are losted");
			message = context.getResources().getString(R.string.you_didnt_get_points);
		}
		
		// notify about 
		MainActivity.notifyUser(context, message);
	}

	private static final String logTag = DailyAlarmReceiver.class.getSimpleName();
}
