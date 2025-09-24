package com.miko.managetask.data.repositery

import com.miko.managetask.data.dao.TaskDao
import com.miko.managetask.data.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

// TaskRepository class is used to perform database operations. All the database operations are defined here.
class TaskRepository @Inject constructor(private val dao: TaskDao) {
    fun getAllTasks() = dao.getAllTasks() //get all tasks
    fun searchTasks(query: String) = dao.searchTasks("%$query%") //search task
    fun filterTasksByStatus(status: Boolean) = dao.filterTasksByStatus(status) //filter tasks by status
    fun filterTasksByPriority(priority: String) = dao.filterTasksByPriority(priority) //filter tasks by priority

    suspend fun insert(task: TaskEntity) = dao.insertTask(task) //add task
    suspend fun update(task: TaskEntity) = dao.updateTask(task) //update task
    suspend fun delete(task: TaskEntity) = dao.deleteTask(task) //delete task
}