package com.radlab.todolist.domain.usecase

import com.radlab.todolist.domain.model.Task
import com.radlab.todolist.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case responsible for fetching all tasks from the repository as a reactive stream.
 *
 * Implements [FlowNoInputUseCase] and delegates directly to [TaskRepository.fetchAllTasks],
 * emitting an updated list of [Task] objects whenever the underlying data source changes.
 *
 * `@property` repository the [TaskRepository] used to retrieve tasks.
 */
class FetchTasksUseCase(private val repository: TaskRepository) : FlowNoInputUseCase<List<Task>> {

    /**
     * Returns a [Flow] that emits the current list of all [Task] objects and
     * subsequently emits updated lists whenever the data source changes.
     *
     * `@return` a [Flow] of [List]<[Task]> from the repository.
     */
    override fun invoke(): Flow<List<Task>> = repository.fetchAllTasks()
}