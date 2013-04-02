package ru.lagner.dailynotifier.receivers;

import java.util.Date;

import ru.lagner.dailynotifier.SettingsProvider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DailyAlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		Date now = new Date();
		
		long delta = now.getTime() - SettingsProvider.getLastActivityTime(context);
		
		if (delta < SettingsProvider.PERIOD) {
			// начисляем бонусные баллы
			// уведомляем об этом
		} else {
			// уведомляем о том, что пользователь свои баллы просохатил
		}
	}

}
