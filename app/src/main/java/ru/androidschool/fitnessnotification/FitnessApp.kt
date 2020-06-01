package ru.androidschool.fitnessnotification

import android.app.Application
import androidx.core.app.NotificationManagerCompat
import ru.androidschool.fitnessnotification.notification.NotificationHelper

class FitnessApp : Application() {

    override fun onCreate() {
        super.onCreate()
        NotificationHelper.createNotificationChannel(
            this,
            NotificationManagerCompat.IMPORTANCE_DEFAULT, false,
            getString(R.string.app_name), "Канал Фитнес - приложения."
        )
    }
}