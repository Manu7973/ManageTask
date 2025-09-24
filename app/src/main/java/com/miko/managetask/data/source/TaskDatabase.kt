package com.miko.managetask.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.miko.managetask.data.dao.TaskDao
import com.miko.managetask.data.entity.TaskEntity

// Room database class that holds the TaskEntity table.
// Provides access to TaskDao for performing CRUD operations on tasks.
@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}