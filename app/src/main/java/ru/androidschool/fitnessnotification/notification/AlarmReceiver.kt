package ru.androidschool.fitnessnotification.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import ru.androidschool.fitnessnotification.KEY_ID
import ru.androidschool.fitnessnotification.domain.ReminderLocalRepostiory

class AlarmReceiver : BroadcastReceiver() {

    private val TAG = AlarmReceiver::class.java.simpleName

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive() called with: context = [$context], intent = [$intent]")
        if (context != null && intent != null) {
            if (intent.extras != null) {
                val reminderData =
                    ReminderLocalRepostiory(context).getReminderById(intent.extras!!.getLong(KEY_ID))
                if (reminderData != null) {
                    NotificationHelper.createNotificationForWorkout(context, reminderData)
                }
            }
        }
    }
}