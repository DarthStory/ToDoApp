package com.example.todoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),  TaskItemClickListener{
    private lateinit var binding: ActivityMainBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dbcon:MyDBConnection= MyDBConnection(this@MainActivity, "ToDoDB", 1)
        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        binding.newTaskButton.setOnClickListener {
            NewTaskSheet(null).show(supportFragmentManager, "newTaskTag")
        }
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val mainActivity = this
        taskViewModel.taskItems.observe(this) {
            binding.todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskItemAdapter(it, mainActivity)
            }

        }
    }

    override fun editTaskItem(taskItem: TaskItem) {
        NewTaskSheet(taskItem).show(supportFragmentManager, "newTaskTag")
    }

    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem)
    }
}