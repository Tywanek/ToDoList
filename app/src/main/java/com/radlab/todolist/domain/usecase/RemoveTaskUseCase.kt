package com.radlab.todolist.domain.usecase

import com.radlab.todolist.domain.model.Task
import com.radlab.todolist.domain.repository.TaskRepository

class RemoveTaskUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(task: Task) = repository.removeTask(task)
}
