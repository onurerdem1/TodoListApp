package com.example.todoapp

import android.content.Context
import com.example.todoapp.data.TodoDataSource
import com.example.todoapp.data.room.TodoDatabase

object Graph {
    lateinit var database: TodoDatabase
        private set
    val todoRepo by lazy {
        TodoDataSource(database.todoDao())
    }

    fun provide(context: Context) {
        database = TodoDatabase.getDatabase(context)
    }
}