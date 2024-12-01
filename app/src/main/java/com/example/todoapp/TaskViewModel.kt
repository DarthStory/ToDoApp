package com.example.todoapp


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val dbConnection = MyDBConnection(application, "ToDoDB", 1)
    var taskItems: MutableLiveData<MutableList<TaskItem>> = MutableLiveData(dbConnection.getAllTasks())

    fun addTaskItem(newTask: TaskItem) {
        dbConnection.insertTask(newTask)
        taskItems.value = dbConnection.getAllTasks()
    }

    fun updateTaskItem(id: Int, name: String, desc: String) {
        val task = taskItems.value?.find { it.id == id }
        if (task != null) {
            task.name = name
            task.desc = desc
            dbConnection.updateTask(task)
            taskItems.value = dbConnection.getAllTasks()
        }
    }

    fun setCompleted(taskItem: TaskItem) {
        taskItem.toggleCompleted()
        dbConnection.updateTask(taskItem)
        taskItems.value = dbConnection.getAllTasks()
    }

    fun deleteTask(id: Int) {
        dbConnection.deleteTask(id.toString())
        taskItems.value = dbConnection.getAllTasks()
    }
}