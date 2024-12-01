package com.example.todoapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.UUID

class TaskViewModel: ViewModel() {

    var taskItems: MutableLiveData<MutableList<TaskItem>> = MutableLiveData(mutableListOf())
            init {
                taskItems.value = mutableListOf()
            }
    fun addTaskItem(newTask: TaskItem) {
        val list = taskItems.value
        list!!.add(newTask)
        taskItems.postValue(list)
    }
    fun updateTaskItem(id: UUID, name: String, desc: String) {
        val list = taskItems.value
        val task = list!!.find{ it.id == id }
        if(task != null) {
            task.name = name
            task.desc = desc
        }
        taskItems.postValue(list)
    }
    fun setCompleted(taskItem: TaskItem) {
        val list = taskItems.value ?: mutableListOf()
        val task = list.find{ it.id == taskItem.id }
        when {
            task != null -> {
                task.toggleCompleted()
            }
        }
        taskItems.postValue(list)
    }
}