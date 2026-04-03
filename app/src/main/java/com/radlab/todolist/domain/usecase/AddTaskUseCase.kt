package com.radlab.todolist.domain.usecase

import com.radlab.todolist.domain.repository.TaskRepository

class AddTaskUseCase(private val repository: TaskRepository)
    : UseCase<String, Unit> {
    override suspend fun invoke(input: String) {
        if(input.isNotBlank()){
            repository.addTask(input)
        }
    }
}
