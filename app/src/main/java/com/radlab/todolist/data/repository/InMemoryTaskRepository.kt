package com.radlab.todolist.data.repository

import com.radlab.todolist.domain.model.Task
import com.radlab.todolist.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class InMemoryTaskRepository : TaskRepository {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    private var nextId = 1

    override fun fetchAllTasks(): Flow<List<Task>> = _tasks.asStateFlow()

    override suspend fun addTask(message: String) = withContext(Dispatchers.IO) {
        _tasks.update { currentList ->
            currentList + Task(id = nextId++, message = message)
        }
    }

    override suspend fun removeTask(task: Task) = withContext(Dispatchers.IO) {
        _tasks.update { currentList ->
            currentList.filter { it.id != task.id }
        }
    }
}
