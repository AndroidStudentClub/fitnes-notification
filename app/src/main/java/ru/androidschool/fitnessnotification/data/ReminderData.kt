package ru.androidschool.fitnessnotification.data

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "reminder_data")
@Parcelize
data class ReminderData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String? = null,
    var type: WorkoutType = WorkoutType.Swimming,
    var hour: Int = 0,
    var minute: Int = 0,
    var days: List<String?>? = null
) : Parcelable

@Dao
interface ReminderDao {

    @Query("SELECT * from reminder_data")
    fun getReminderData(): List<ReminderData>

    @Query("SELECT * from reminder_data where id= :id")
    fun getReminderById(id: Long): ReminderData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(reminder: ReminderData): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(reminders: List<ReminderData>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(reminder: ReminderData)

    @Query("DELETE FROM reminder_data")
    fun deleteAll()

    @Query("DELETE FROM reminder_data where id LIKE :id")
    fun deleteById(id: Long)
}
