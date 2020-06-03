package ru.androidschool.fitnessnotification.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.androidschool.fitnessnotification.converters.DaysConverter
import ru.androidschool.fitnessnotification.converters.WorkoutTypeConverter

@Database(entities = [ReminderData::class], version = 1)
@TypeConverters(WorkoutTypeConverter::class, DaysConverter::class)
abstract class WorkoutDatabase : RoomDatabase() {

    abstract fun reminderDataDao(): ReminderDao
}