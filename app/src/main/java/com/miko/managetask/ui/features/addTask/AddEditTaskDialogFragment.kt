package com.miko.managetask.ui.features.addTask

import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.miko.managetask.R
import com.miko.managetask.data.entity.TaskEntity
import com.miko.managetask.databinding.AddTaskBinding
import com.miko.managetask.utils.TaskPriority
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

// AddEditTaskDialogFragment is used to add or edit a task.
@AndroidEntryPoint
class AddEditTaskDialogFragment(
    private val task: TaskEntity? = null
) : DialogFragment() {

    private lateinit var binding: AddTaskBinding // UI binding
    private val viewModel: AddEditTaskViewModel by viewModels() // ViewModel
    private val priorities = listOf("High", "Medium", "Low") // Priority options

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        binding = AddTaskBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        // Background & UI setup same as before ...
        setupSwitchColorOnCheck()

        // Dropdown
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_dropdown, priorities)
        binding.spPriority.setAdapter(adapter)


        // Date picker
        binding.dateLayout.setEndIconOnClickListener { openDatePicker() }

        // Observe error messages only
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { state ->
                state.errorMessage?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    viewModel.clearError()
                }
                if (task != null) {
                    binding.etTitle.setText(state.title)
                    binding.etDescription.setText(state.description)
                    binding.spPriority.setText(state.priority.name, false)
                    binding.switchStatus.isChecked = state.isCompleted
                    if (state.dueDate.isNotBlank()) {
                        binding.etDate.setText(state.dueDate)
                    }
                }
            }
        }

        // Load task if editing
        viewModel.loadTask(task)

        // Save
        binding.btnSave.setOnClickListener {
            viewModel.onSaveClicked(
                title = binding.etTitle.text.toString(),
                description = binding.etDescription.text.toString(),
                dueDate = binding.etDate.text.toString(),
                priorityText = binding.spPriority.text.toString(),
                isCompleted = binding.switchStatus.isChecked,
                originalTask = task
            ) {
                dismiss() // ✅ directly close
            }
        }

        // Delete
        if (task == null) {
            binding.btnDelete.visibility = View.GONE
        } else {
            binding.btnDelete.setOnClickListener {
                viewModel.onDeleteClicked(task) {
                    dismiss() // ✅ directly close
                }
            }
        }

        return dialog
    }

    // Open date range picker with restriction on past dates.
    private fun openDatePicker() {
        val constraints = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.from(System.currentTimeMillis()))
            .build()

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select due date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(constraints)
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            binding.etDate.setText(sdf.format(Date(selection)))
        }

        datePicker.show(parentFragmentManager, "DATE_PICKER")
    }

    // Set dialog size
    override fun onStart() {
        super.onStart()
        dialog?.window?.let { window ->
            val params = window.attributes
            params.width = (resources.displayMetrics.widthPixels * 0.9).toInt()
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            window.attributes = params
            window.setGravity(Gravity.CENTER)
        }
    }

    // Set switch color on check
    private fun setupSwitchColorOnCheck() {
        binding.switchStatus.apply {
            textOn = ""    // prevent NullPointerException
            textOff = ""   // prevent NullPointerException
        }

        val thumbColors = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),    // ON
                intArrayOf(-android.R.attr.state_checked)    // OFF
            ),
            intArrayOf(
                ContextCompat.getColor(requireContext(), R.color.primary), // thumb ON
                ContextCompat.getColor(
                    requireContext(),
                    R.color.thinGrey
                )     // thumb OFF
            )
        )

        val trackColors = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),    // ON
                intArrayOf(-android.R.attr.state_checked)    // OFF
            ),
            intArrayOf(
                ContextCompat.getColor(requireContext(), R.color.thinGrey), // track ON
                ContextCompat.getColor(requireContext(), R.color.white) // track OFF
            )
        )

        binding.switchStatus.thumbTintList = thumbColors
        binding.switchStatus.trackTintList = trackColors
    }
}




