package com.example.tasks.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tasks.data.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = true)
@TypeConverters(Converter::class)
abstract class TaskDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null
        private const val DB_NAME = "tasks.db"

        fun getInstance(context: Context): TaskDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java,
                        DB_NAME
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }

    abstract fun taskDao(): TaskDao
}