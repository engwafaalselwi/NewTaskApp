package database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newtaskapp.Task


@Database(entities = [Task::class] , version=1)
@TypeConverters(TaskTypeConverters::class)
abstract class TaskMangDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskMangDao
}
