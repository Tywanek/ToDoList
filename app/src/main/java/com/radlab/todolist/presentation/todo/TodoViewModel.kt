package com.radlab.todolist.presentation.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radlab.todolist.domain.model.Task
import com.radlab.todolist.domain.usecase.AddTaskUseCase
import com.radlab.todolist.domain.usecase.FetchTasksUseCase
import com.radlab.todolist.domain.usecase.RemoveTaskUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(
    private val fetchTasksUseCase: FetchTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val removeTaskUseCase: RemoveTaskUseCase
) : ViewModel() {

    val tasks: StateFlow<List<Task>> = fetchTasksUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onAddTask(message: String) {
        viewModelScope.launch {
            addTaskUseCase(message)
        }
    }

    fun onDeleteTask(task: Task) {
        viewModelScope.launch {
            removeTaskUseCase(task)
        }
    }
}
