package com.miko.manageTaskEntity.ui.features.ManageTaskEntity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.miko.managetask.R
import com.miko.managetask.data.entity.TaskEntity
import com.miko.managetask.databinding.TaskAdapterItemBinding
import com.miko.managetask.utils.DateUtils.getFormattedDate
import com.miko.managetask.utils.TaskPriority

// TaskEntityAdapter is used to display the list of tasks.
class TaskEntityAdapter(
    private var tasks: List<TaskEntity>,
    private val onSelected: (TaskEntity) -> Unit,
) : RecyclerView.Adapter<TaskEntityAdapter.TaskEntityViewHolder>() {

    // ViewHolder class for the adapter
    inner class TaskEntityViewHolder(private val binding: TaskAdapterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            // Binds the task data to the view
        fun bind(task: TaskEntity) {
            binding.tvTitle.text = task.title
            binding.tvDescription.text = task.description
            binding.tvPriority.text = task.priority.name
            binding.tvDueDate.text = "Due: ${getFormattedDate(task.dueDate)}"
            binding.tvDate.text = getFormattedDate(task.createdAt)

            val drawableRes = when (task.priority) {
                TaskPriority.HIGH -> R.drawable.bg_priority_high
                TaskPriority.MEDIUM -> R.drawable.bg_priority_medium
                TaskPriority.LOW -> R.drawable.bg_priority_low
            }
            binding.tvPriority.setBackgroundResource(drawableRes)
            binding.root.setOnClickListener { onSelected(task) }
        }
    }

    // Creates a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskEntityViewHolder {
        val binding = TaskAdapterItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TaskEntityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskEntityViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    // Returns the number of items in the list
    override fun getItemCount(): Int = tasks.size

    // Updates the list of tasks and notifies the adapter
    fun updateList(newTasks: List<TaskEntity>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}


