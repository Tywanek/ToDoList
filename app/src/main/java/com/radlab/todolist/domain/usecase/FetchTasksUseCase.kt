package com.radlab.todolist.domain.usecase

import com.radlab.todolist.domain.model.Task
import com.radlab.todolist.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class FetchTasksUseCase(private val repository: TaskRepository) : FlowNoInputUseCase<List<Task>> {
    override fun invoke(): Flow<List<Task>> = repository.fetchAllTasks()
}
