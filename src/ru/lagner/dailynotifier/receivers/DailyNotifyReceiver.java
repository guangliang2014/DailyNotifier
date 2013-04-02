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

		// ��������� ������� �� ������������ ��������� 23 ����
		// ���� ��� ����������, ��� � ���� ������� ���
		// ����� �������� �������� ������	
		
		Date now = new Date();
		long delta = now.getTime() - SettingsProvider.getLastActivityTime(context);
		float norma = SettingsProvider.PERIOD * SettingsProvider.NOTIFY_FACTOR;
		
		if (!(delta < norma)) {
			
			// ���������� ������������ ��� ���� �� ������ �� ������ �� �������
		} 	
	}
	
	
	private static final String logTag = "receivers.DailyNotifyReceiver";
}
