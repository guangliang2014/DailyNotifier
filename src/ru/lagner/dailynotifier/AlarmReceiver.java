package ru.lagner.dailynotifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		// ������� �������� � �������� ������� ����
		
		Log.i(logTag, "timer tick. Start activity to increase mana");
		
	}
	
	private static final String logTag = "AlarmReceiver";
}
