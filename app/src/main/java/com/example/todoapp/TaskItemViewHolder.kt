package com.example.todoapp

import android.content.Context
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.TaskItemCellBinding

class TaskItemViewHolder(private val context: Context, private val binding: TaskItemCellBinding, private val clickListener: TaskItemClickListener): RecyclerView.ViewHolder(binding.root) {
    fun bindTaskItem(taskItem: TaskItem){
        binding.name.text = taskItem.name

        if(taskItem.isCompleted()) {
            binding.name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            binding.name.paintFlags = binding.name.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        binding.completeButton.setImageResource(taskItem.imageResource())
        binding.completeButton.setColorFilter(taskItem.imageColor(context))

        binding.completeButton.setOnClickListener() {
            clickListener.completeTaskItem(taskItem)
        }

        binding.taskCellContainer.setOnClickListener {
            clickListener.editTaskItem(taskItem)
        }

        binding.deleteButton.setOnClickListener {
            clickListener.deleteTaskItem(taskItem)
        }
    }
}