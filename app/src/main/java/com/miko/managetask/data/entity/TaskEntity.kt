package com.miko.managetask.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.miko.managetask.utils.TaskPriority

// TaskEntity class is used to create a table in the database. All the fields eg. title, priority declared below will behave as column in database.
@Entity(tableName = "tasks_db") //Database name - tasks_db
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String = "",
    val priority: TaskPriority = TaskPriority.LOW,
    val isCompleted: Boolean = false,
    val dueDate: String = "",
    val createdAt: String = ""
)