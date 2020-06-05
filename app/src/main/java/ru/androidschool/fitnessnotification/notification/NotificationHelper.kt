package ru.androidschool.fitnessnotification.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.androidschool.fitnessnotification.KEY_ID
import ru.androidschool.fitnessnotification.MainActivity
import ru.androidschool.fitnessnotification.R
import ru.androidschool.fitnessnotification.data.ReminderData
import ru.androidschool.fitnessnotification.data.WorkoutType

object NotificationHelper {

    /**
     * Sets up the notification channels for API 26+.
     * Note: This uses package name + channel name to create unique channelId's.
     *
     * @param context     application context
     * @param importance  importance level for the notificaiton channel
     * @param showBadge   whether the channel should have a notification badge
     * @param name        name for the notification channel
     * @param description description for the notification channel
     */
    fun createNotificationChannel(
        context: Context,
        importance: Int,
        showBadge: Boolean,
        name: String,
        description: String
    ) {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelId = "${context.packageName}-$name"
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.setShowBadge(showBadge)

            // Register the channel with the system
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Helps issue the default application channels (package name + app name) notifications.
     * Note: this shows the use of [NotificationCompat.BigTextStyle] for expanded notifications.
     *
     * @param context    current application context
     * @param title      title for the notification
     * @param message    content text for the notification when it's not expanded
     * @param bigText    long form text for the expanded notification
     * @param autoCancel `true` or `false` for auto cancelling a notification.
     * if this is true, a [PendingIntent] is attached to the notification to
     * open the application.
     */
    fun createSampleDataNotification(
        context: Context, title: String, message: String,
        bigText: String, autoCancel: Boolean
    ) {

        // 1
        val channelId = "${context.packageName}-${context.getString(R.string.app_name)}"
        // 2
        val notificationBuilder = NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.ic_fitnes) // 3
            setContentTitle(title) // 4
            setContentText(message) // 5
            setAutoCancel(autoCancel) // 6
            setStyle(NotificationCompat.BigTextStyle().bigText(bigText)) // 7
            priority = NotificationCompat.PRIORITY_DEFAULT // 8

            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            setContentIntent(pendingIntent)
        }

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1001, notificationBuilder.build())
    }


    fun createNotificationForWorkout(context: Context, reminderData: ReminderData) {

        // 1 create a group notification
        val groupBuilder = buildGroupNotification(context, reminderData)
        // 2
        val notificationBuilder = buildNotification(context, reminderData)
        // 3
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(reminderData.type.ordinal, groupBuilder.build())
        notificationManager.notify(reminderData.id.toInt(), notificationBuilder.build())
    }



    private fun buildGroupNotification(
        context: Context,
        reminderData: ReminderData
    ): NotificationCompat.Builder {
        val channelId = "${context.packageName}-${reminderData.type.name}"
        return NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.ic_fitnes)
            setContentTitle(reminderData.type.name)
            setContentText(
                context.getString(
                    R.string.group_notification_for,
                    reminderData.type.name
                )
            )
            setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    context.getString(
                        R.string.group_notification_for,
                        reminderData.type.name
                    )
                )
            )
            setAutoCancel(true)
            setGroupSummary(true)
            setGroup(reminderData.type.name)
        }
    }

    private fun buildNotification(
        context: Context,
        reminderData: ReminderData
    ): NotificationCompat.Builder {


        val channelId = "${context.packageName}-${reminderData.type.name}"

        return NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.ic_fitnes)
            setContentTitle(reminderData.name)
            setAutoCancel(true)

            // get a drawable reference for the LargeIcon
            val drawable = when (reminderData.type) {
                WorkoutType.Running -> R.drawable.ic_run
                WorkoutType.Cycling -> R.drawable.ic_velo
                else -> R.drawable.ic_swimming
            }
            setLargeIcon(BitmapFactory.decodeResource(context.resources, drawable))
            setContentText("${reminderData.name}")
            setGroup(reminderData.type.name)


            // Launches the app to open the reminder edit screen when tapping the whole notification
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra(KEY_ID, reminderData.id)
            }

            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            setContentIntent(pendingIntent)
        }
    }
}