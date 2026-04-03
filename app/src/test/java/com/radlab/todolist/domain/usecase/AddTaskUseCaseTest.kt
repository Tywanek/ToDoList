package com.radlab.todolist.domain.usecase

import com.radlab.todolist.domain.repository.TaskRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddTaskUseCaseTest {

    private lateinit var repository: TaskRepository
    private lateinit var addTaskUseCase: AddTaskUseCase

    @Before
    fun setUp() {
        repository = mockk()
        addTaskUseCase = AddTaskUseCase(repository)
    }

    @Test
    fun `when message is not blank, task should be added`() = runTest {
        // Given
        val message = "New Task"
        coEvery { repository.addTask(any()) } just Runs

        // When
        addTaskUseCase(message)

        // Then
        coVerify { repository.addTask(message) }
    }

    @Test
    fun `when message is blank, task should NOT be added`() = runTest {
        // Given
        val message = "   "

        // When
        addTaskUseCase(message)

        // Then
        coVerify(exactly = 0) { repository.addTask(any()) }
    }
}
