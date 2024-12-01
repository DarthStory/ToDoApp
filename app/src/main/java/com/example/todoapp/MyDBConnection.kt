package com.example.todoapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBConnection(context: Context, dbName:String, dbVersion: Int): SQLiteOpenHelper(context, dbName, null, dbVersion) {

    companion object{
        const val tableName: String = "ToDo"
        const val col1 ="Uid"
        const val col2 ="name"
        const val col3 ="description"
        const val col4 = "isCompleted"
    }
    private val query =
        "CREATE TABLE $tableName ($col1 INTEGER PRIMARY KEY AUTOINCREMENT, $col2 TEXT, $col3 TEXT, $col4 INTEGER)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $tableName")
        db?.execSQL(query)
    }
    fun insertTask(task: TaskItem): Long {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(col2, task.name)
            put(col3, task.desc)
            put(col4, if (task.isCompleted()) 1 else 0)
        }
        return db.insert(tableName, null, contentValues)
    }
    fun updateTask(task: TaskItem): Int {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(col2, task.name)
            put(col3, task.desc)
            put(col4, if (task.isCompleted()) 1 else 0)
        }
        return db.update(tableName, contentValues, "$col1=?", arrayOf(task.id.toString()))
    }

    fun deleteTask(id: String): Int {
        val db = writableDatabase
        return db.delete(tableName, "$col1=?", arrayOf(id))
    }

    fun getAllTasks(): MutableList<TaskItem> {
        val db = readableDatabase
        val cursor: Cursor = db.query(
            tableName, null, null, null, null, null, null
        )
        val tasks = mutableListOf<TaskItem>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(col1))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(col2))
                val desc = cursor.getString(cursor.getColumnIndexOrThrow(col3))
                val isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow(col4)) == 1
                tasks.add(TaskItem(name, desc, id, isCompleted))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return tasks
    }
}