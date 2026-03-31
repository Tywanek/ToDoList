package com.radlab.todolist.domain.usecase

import com.radlab.todolist.domain.repository.TaskRepository

class AddTaskUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(message: String) {
        if (message.isNotBlank()) {
            repository.addTask(message)
        }
    }
}
