package com.example.todoapp

import android.content.Context
import androidx.core.content.ContextCompat
import java.util.UUID

class TaskItem (var name: String, var desc: String, var id: UUID = UUID.randomUUID(), private var completed: Boolean = false) {

    fun imageResource(): Int = if (isCompleted()) R.drawable.checked_24 else R.drawable.unchecked_24
    fun imageColor(context: Context): Int = if(isCompleted()) purple(context) else black(context)

        private fun purple(context: Context) = ContextCompat.getColor(context, R.color.purple)
        private fun black(context: Context) = ContextCompat.getColor(context, R.color.black)
    fun isCompleted(): Boolean = completed

    fun toggleCompleted() {
        completed = !completed
    }
}