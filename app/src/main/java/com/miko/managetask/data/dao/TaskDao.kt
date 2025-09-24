package com.miko.managetask.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.miko.managetask.data.entity.TaskEntity

// TaskDao interface used for database operations. All the queries are defined here.
@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity) //add task

    @Update
    suspend fun updateTask(task: TaskEntity) //update task

    @Delete
    suspend fun deleteTask(task: TaskEntity) //delete task

    @Query("SELECT * FROM tasks_db ORDER BY createdAt DESC") //get all tasks
    fun getAllTasks(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM tasks_db WHERE title LIKE :query ORDER BY createdAt DESC") //search task
    fun searchTasks(query: String): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM tasks_db WHERE isCompleted = :status ORDER BY createdAt DESC") //filter tasks by status
    fun filterTasksByStatus(status: Boolean): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM tasks_db WHERE priority = :priority ORDER BY createdAt DESC") //filter tasks by priority
    fun filterTasksByPriority(priority: String): LiveData<List<TaskEntity>>
}