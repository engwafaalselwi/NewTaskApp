package com.example.newtaskapp
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room

import database.TaskMangDatabase
import java.util.concurrent.Executors
private const val DATABASE_NAME = "Task-database"
class TaskRepository  private constructor(context: Context) {

    private val database :TaskMangDatabase = Room.databaseBuilder(
        context.applicationContext,
        TaskMangDatabase::class.java,
        DATABASE_NAME).build()
    private val taskDao = database.taskDao()

    fun getTasks(): LiveData<List<Task>> = taskDao.getTasks()
    fun getTodo(): LiveData<List<Task>> = taskDao.getTodo()
    fun getPrograss(): LiveData<List<Task>> = taskDao.getPrograss()
    fun getDone(): LiveData<List<Task>> = taskDao.getDone()
    private val executor = Executors.newSingleThreadExecutor()

    fun getTask(id: Int): LiveData<Task?> = taskDao.getTask(id)
    companion object {
        private var INSTANCE: TaskRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = TaskRepository(context)
            }        }
        fun get(): TaskRepository {
            return INSTANCE ?:
            throw IllegalStateException("TaskRepository must be initialized")
        }    }
    fun addTask(task: Task) {
        executor.execute {
            taskDao.addTask(task)
        }  }
    fun deleteTask(task: Task) {
        executor.execute {
            taskDao.deleteTask(task)
        }  }
    fun updateTask(task: Task) {
        executor.execute {
            taskDao.updateTask(task)
        }  }

}
