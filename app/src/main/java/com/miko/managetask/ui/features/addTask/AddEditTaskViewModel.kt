package com.miko.managetask.ui.features.addTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miko.managetask.data.entity.TaskEntity
import com.miko.managetask.data.repositery.TaskRepository
import com.miko.managetask.utils.DateUtils.getCurrentDate
import com.miko.managetask.utils.TaskPriority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddEditTaskUiState()) // UI state
    val uiState: StateFlow<AddEditTaskUiState> = _uiState // Expose as StateFlow

    // Load task for editing
    fun loadTask(task: TaskEntity?) {
        task?.let {
            _uiState.value = _uiState.value.copy(
                id = it.id,
                title = it.title,
                description = it.description,
                priority = it.priority,
                dueDate = it.dueDate.orEmpty(),
                isCompleted = it.isCompleted
            )
        }
    }

    // saving data in roomDB.
    fun onSaveClicked(
        title: String,
        description: String,
        dueDate: String,
        priorityText: String,
        isCompleted: Boolean,
        originalTask: TaskEntity?,
        onDone: () -> Unit // ✅ simple callback
    ) {
        if (title.isBlank() || description.isBlank() || dueDate.isBlank() || priorityText.isBlank()) {
            _uiState.update { it.copy(errorMessage = "All fields are required") }
            return
        }

        val priority = when (priorityText) {
            "High" -> TaskPriority.HIGH
            "Medium" -> TaskPriority.MEDIUM
            else -> TaskPriority.LOW
        }

        val task = TaskEntity(
            id = originalTask?.id ?: 0,
            title = title.trim(),
            description = description.trim(),
            dueDate = dueDate.trim(),
            priority = priority,
            createdAt = originalTask?.createdAt ?: getCurrentDate(),
            isCompleted = isCompleted
        )

        viewModelScope.launch {
            if (originalTask != null) {
                taskRepository.update(task)   //  Update
            } else {
                taskRepository.insert(task)   //  Insert
            }
            onDone() // notify fragment to dismiss
        }
    }

    // Delete
    fun onDeleteClicked(task: TaskEntity, onDone: () -> Unit) {
        viewModelScope.launch {
            taskRepository.delete(task)
            onDone()
        }
    }

    // Clear error message
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    // UI state manage
    data class AddEditTaskUiState(
        val id: Int = 0,
        val title: String = "",
        val description: String = "",
        val dueDate: String = "",
        val priority: TaskPriority = TaskPriority.LOW,
        val isCompleted: Boolean = false,
        val errorMessage: String? = null
    )
}


