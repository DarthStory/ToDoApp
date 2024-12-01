package com.example.todoapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBConnection(context: Context, dbName:String, dbVersion: Int): SQLiteOpenHelper(context, dbName, null, dbVersion) {

    companion object{
        const val TABLENAME: String = "ToDo"
        const val COL1 ="Uid"
        const val COL2 ="name"
        const val COL3 ="description"
        const val COL4 = "isCompleted"
    }
    private val query =
        "CREATE TABLE $TABLENAME ($COL1 INTEGER PRIMARY KEY AUTOINCREMENT, $COL2 TEXT, $COL3 TEXT, $COL4 INTEGER)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLENAME")
        db?.execSQL(query)
    }
    fun insertTask(task: TaskItem): Long {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COL2, task.name)
            put(COL3, task.desc)
            put(COL4, if (task.isCompleted()) 1 else 0)
        }
        return db.insert(TABLENAME, null, contentValues)
    }
    fun updateTask(task: TaskItem): Int {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COL2, task.name)
            put(COL3, task.desc)
            put(COL4, if (task.isCompleted()) 1 else 0)
        }
        return db.update(TABLENAME, contentValues, "$COL1=?", arrayOf(task.id.toString()))
    }

    fun deleteTask(id: String): Int {
        val db = writableDatabase
        return db.delete(TABLENAME, "$COL1=?", arrayOf(id))
    }

    fun getAllTasks(): MutableList<TaskItem> {
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLENAME, null, null, null, null, null, null
        )
        val tasks = mutableListOf<TaskItem>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COL1))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COL2))
                val desc = cursor.getString(cursor.getColumnIndexOrThrow(COL3))
                val isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow(COL4)) == 1
                tasks.add(TaskItem(name, desc, id, isCompleted))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return tasks
    }
}