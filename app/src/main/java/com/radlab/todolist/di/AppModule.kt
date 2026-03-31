package com.radlab.todolist.di

import com.radlab.todolist.data.repository.InMemoryTaskRepository
import com.radlab.todolist.domain.repository.TaskRepository
import com.radlab.todolist.domain.usecase.AddTaskUseCase
import com.radlab.todolist.domain.usecase.FetchTasksUseCase
import com.radlab.todolist.domain.usecase.RemoveTaskUseCase
import com.radlab.todolist.presentation.todo.TodoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Repository
    single<TaskRepository> { InMemoryTaskRepository() }

    // Use Cases
    factory { FetchTasksUseCase(get()) }
    factory { AddTaskUseCase(get()) }
    factory { RemoveTaskUseCase(get()) }

    // ViewModel
    viewModel { TodoViewModel(get(), get(), get()) }
}
