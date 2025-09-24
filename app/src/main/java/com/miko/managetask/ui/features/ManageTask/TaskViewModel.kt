package com.miko.managetask.ui.features.ManageTask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.miko.managetask.data.entity.TaskEntity
import com.miko.managetask.data.repositery.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    // All tasks (auto-updates because Room emits changes)
    val allTasks: LiveData<List<TaskEntity>> = repository.getAllTasks()

    // Search results
    private val _searchQuery = MutableLiveData<String>("")

    val searchResults: LiveData<List<TaskEntity>> = _searchQuery.switchMap { query ->
        if (query.isNullOrBlank()) {
            repository.getAllTasks()
        } else {
            repository.searchTasks(query)
        }
    }

    // Filter by priority
    fun filterByPriority(priority: String): LiveData<List<TaskEntity>> =
        repository.filterTasksByPriority(priority)

    // Filter by status
    fun filterByStatus(status: Boolean): LiveData<List<TaskEntity>> =
        repository.filterTasksByStatus(status)

    // Trigger search
    fun search(query: String) {
        _searchQuery.value = query
    }
}
