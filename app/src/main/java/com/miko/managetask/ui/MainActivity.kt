package com.miko.managetask.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.PopupMenu
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.text.toUpperCase
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.miko.manageTaskEntity.ui.features.ManageTaskEntity.TaskEntityAdapter
import com.miko.managetask.R
import com.miko.managetask.data.entity.TaskEntity
import com.miko.managetask.databinding.ActivityMainBinding
import com.miko.managetask.ui.features.ManageTask.TaskViewModel
import com.miko.managetask.ui.features.addTask.AddEditTaskDialogFragment
import com.miko.managetask.ui.theme.MyAppTheme
import com.miko.managetask.utils.TaskPriority
import dagger.hilt.android.AndroidEntryPoint

/*
 MainActivity - entry point of the app that displays and manages the list of tasks.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() { // MainActivity extends AppCompatActivity

    private lateinit var binding: ActivityMainBinding // Declare binding variable
    private val viewModel: TaskViewModel by viewModels() // Inject ViewModel
    private lateinit var adapter: TaskEntityAdapter // Declare adapter variable
    private var isListView = true // Flag to track view type

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Initialize binding
        setContentView(binding.root)

        init() // Initialize functions

        // window for add task
        binding.faAddTask.setOnClickListener {
            AddEditTaskDialogFragment().show(supportFragmentManager, this.getString(R.string.add_task_dialog))
        }
    }

    private fun init() {
        setupRecyclerView()
        setupSearch()
        setupFilter()
        observeTasks()
    }

    // Set up recycler view
    private fun setupRecyclerView() {
        adapter = TaskEntityAdapter(
            tasks = emptyList(), onSelected = {
                AddEditTaskDialogFragment(
                    task = it,
                ).show(supportFragmentManager, this.getString(R.string.edit_task_dialog))
            }
        )

        // Default layout manager: List
        binding.rvTasks.layoutManager = LinearLayoutManager(this)
        binding.rvTasks.adapter = adapter

        // Toggle button click
        binding.ivToggleView.setOnClickListener {
            isListView = !isListView // switch the state

            if (isListView) {
                binding.rvTasks.layoutManager = LinearLayoutManager(this)
                binding.ivToggleView.setImageResource(R.drawable.icon_grid)
                binding.ivToggleView.contentDescription = getString(R.string.grid)
            } else {
                binding.rvTasks.layoutManager = GridLayoutManager(this, 2) // 2 columns grid
                binding.ivToggleView.setImageResource(R.drawable.icon_list)
                binding.ivToggleView.contentDescription = getString(R.string.list)
            }
        }
    }

    //Set up search based on task title.
    private fun setupSearch() {
        val searchEditText = binding.searchView.findViewById<EditText>(
            androidx.appcompat.R.id.search_src_text
        )
        searchEditText.hint = getString(R.string.search_task)
        searchEditText.setHintTextColor(ContextCompat.getColor(this, R.color.textGrey))

        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.search(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.search(it) }
                return true
            }
        })
    }

    //Set up filter for tasks - All, High, Medium, Low priority
    private fun setupFilter() {
        binding.ivFilter.setOnClickListener { view ->
            val popup = PopupMenu(this, view)

            // Add "All" option
            popup.menu.add(Menu.NONE, 0, 0, "All")

            // Add enum values dynamically
            TaskPriority.values().forEachIndexed { index, priority ->
                popup.menu.add(Menu.NONE, index + 1, index + 1, priority.name.capitalize())
            }

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    0 -> observeTasks() // "All" selected
                    else -> {
                        val selectedPriority = TaskPriority.values()[item.itemId - 1].name
                        viewModel.filterByPriority(selectedPriority)
                            .observe(this) { filteredTasks ->
                                updateList(filteredTasks)
                            }
                    }
                }
                true
            }

            popup.show()
        }
    }

    // Observe tasks
    private fun observeTasks() {
        viewModel.allTasks.observe(this) { updateList(it) }
        viewModel.searchResults.observe(this) { updateList(it) }
    }

    // Update list of tasks
    private fun updateList(list: List<TaskEntity>) {
        if (list.isEmpty()) {
            binding.tvNoTaskAdded.visibility = View.VISIBLE
            binding.llContainer.visibility = View.GONE
        } else {
            binding.tvNoTaskAdded.visibility = View.GONE
            binding.llContainer.visibility = View.VISIBLE
            adapter.updateList(list)
        }
    }
}
