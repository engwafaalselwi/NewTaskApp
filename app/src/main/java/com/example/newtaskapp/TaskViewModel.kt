package com.example.newtaskapp

import androidx.lifecycle.ViewModel

class TaskViewModel :ViewModel(){
    private val taskRepository = TaskRepository.get()
    val taskListLiveData  = taskRepository.getTasks()
    val taskToDoListLiveData  = taskRepository.getTodo()
    val taskPrograssListLiveData  = taskRepository.getPrograss()
    val taskDoneListLiveData  = taskRepository.getDone()


    fun addTask(task: Task){
        taskRepository.addTask(task)

    }
    fun updateTask(task: Task){
        taskRepository.updateTask(task)

    }
       fun deleteTask(task: Task){
            taskRepository.deleteTask(task)
 }
}