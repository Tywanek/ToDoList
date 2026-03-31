package com.radlab.todolist.domain.repository

import com.radlab.todolist.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun fetchAllTasks(): Flow<List<Task>>
    suspend fun addTask(message: String)
    suspend fun removeTask(task: Task)
}
