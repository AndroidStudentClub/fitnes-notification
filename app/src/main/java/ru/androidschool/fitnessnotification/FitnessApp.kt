package ru.androidschool.fitnessnotification

import android.app.Application
import androidx.core.app.NotificationManagerCompat
import androidx.room.Room
import ru.androidschool.fitnessnotification.data.WorkoutDatabase
import ru.androidschool.fitnessnotification.data.WorkoutType
import ru.androidschool.fitnessnotification.notification.NotificationHelper

class FitnessApp : Application() {

    lateinit var app: FitnessApp
    private lateinit var database: WorkoutDatabase

    override fun onCreate() {
        super.onCreate()

        app = this
        database = Room.databaseBuilder(this, WorkoutDatabase::class.java, "database")
            // Только для примера, в реальном проекте так не стоит делать
            .allowMainThreadQueries()
            .build()

        // 1
        NotificationHelper.createNotificationChannel(
            this,
            NotificationManagerCompat.IMPORTANCE_LOW, true,
            WorkoutType.Cycling.name, "Notification channel for Cycling."
        )
        // 2
        NotificationHelper.createNotificationChannel(
            this,
            NotificationManagerCompat.IMPORTANCE_HIGH, true,
            WorkoutType.Swimming.name, "Notification channel for Swimming."
        )
        // 3
        NotificationHelper.createNotificationChannel(
            this,
            NotificationManagerCompat.IMPORTANCE_NONE, false,
            WorkoutType.Running.name, "Notification channel for other Running"
        )
    }

    fun getInstance(): FitnessApp {
        return app
    }

    fun getDatabase(): WorkoutDatabase {
        return database
    }
}