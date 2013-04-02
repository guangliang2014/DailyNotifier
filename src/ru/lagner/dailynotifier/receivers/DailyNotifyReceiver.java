package ru.lagner.dailynotifier.receivers;

import java.util.Date;

import ru.lagner.dailynotifier.SettingsProvider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DailyNotifyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		// проверять заходил ли пользователь последние 23 часа
		// если нет уведомлять, что у него остался час
		// иначе никакого дневного бонуса	
		
		Date now = new Date();
		long delta = now.getTime() - SettingsProvider.getLastActivityTime(context);
		float norma = SettingsProvider.PERIOD * SettingsProvider.NOTIFY_FACTOR;
		
		if (!(delta < norma)) {
			
			// уведомляем пользователя что если не зайдет то ничего не получит
		} 	
	}
	
	
	private static final String logTag = "receivers.DailyNotifyReceiver";
}
