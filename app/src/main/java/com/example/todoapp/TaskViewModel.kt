package com.example.todoapp

//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel

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

//class TaskViewModel: ViewModel() {
//
//    var taskItems: MutableLiveData<MutableList<TaskItem>> = MutableLiveData(mutableListOf())
//            init {
//                taskItems.value = mutableListOf()
//            }
//    fun addTaskItem(newTask: TaskItem) {
//        val list = taskItems.value
//        list!!.add(newTask)
//        taskItems.postValue(list)
//    }
//    fun updateTaskItem(id: UUID, name: String, desc: String) {
//        val list = taskItems.value
//        val task = list!!.find{ it.id == id }
//        if(task != null) {
//            task.name = name
//            task.desc = desc
//        }
//        taskItems.postValue(list)
//    }
//    fun setCompleted(taskItem: TaskItem) {
//        val list = taskItems.value ?: mutableListOf()
//        val task = list.find{ it.id == taskItem.id }
//        when {
//            task != null -> {
//                task.toggleCompleted()
//            }
//        }
//        taskItems.postValue(list)
//    }
}