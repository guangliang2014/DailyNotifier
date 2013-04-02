package ru.lagner.dailynotifier.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TimeChangedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		// пользователь вручную изменил дату. 
		// установить дату последный активности на этот момент
	}

}
