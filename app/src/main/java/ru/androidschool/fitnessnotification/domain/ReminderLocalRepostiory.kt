package ru.androidschool.fitnessnotification.domain

import android.content.Context
import ru.androidschool.fitnessnotification.FitnessApp
import ru.androidschool.fitnessnotification.data.ReminderData

class ReminderLocalRepostiory(val context: Context?) {

    private val roomDatabase = (context?.applicationContext as FitnessApp).getDatabase()
    private val dao = roomDatabase.reminderDataDao()

    fun getReminders(): List<ReminderData> {
        return dao.getReminderData()
    }

    fun saveReminder(reminderData:ReminderData):Long{
        return dao.insert(reminderData)
    }
}