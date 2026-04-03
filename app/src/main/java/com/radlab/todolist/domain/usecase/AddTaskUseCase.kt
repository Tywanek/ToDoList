package com.radlab.todolist.domain.usecase

import com.radlab.todolist.domain.repository.TaskRepository

/**
 * Use case responsible for adding a new task to the repository.
 *
 * Implements [UseCase] with a [String] input (the task message) and [Unit] output.
 * The task is only persisted when the provided message is not blank; blank or
 * whitespace-only strings are silently ignored.
 *
 * `@property` repository the [TaskRepository] used to persist the new task.
 */
class AddTaskUseCase(private val repository: TaskRepository) : UseCase<String, Unit> {

    /**
     * Adds a new task with the given [input] message if it is not blank.
     *
     * If [input] consists solely of whitespace characters, no task is created
     * and the repository is not called.
     *
     * `@param` input the task message to be added.
     */
    override suspend fun invoke(input: String) {
        if (input.isNotBlank()) {
            repository.addTask(input)
        }
    }
}