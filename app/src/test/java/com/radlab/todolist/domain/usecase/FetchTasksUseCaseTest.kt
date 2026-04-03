package com.radlab.todolist.domain.usecase

import app.cash.turbine.test
import com.radlab.todolist.domain.model.Task
import com.radlab.todolist.domain.repository.TaskRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchTasksUseCaseTest {
    private lateinit var repository: TaskRepository
    private lateinit var fetchTasksUseCase: FetchTasksUseCase

    @Before
    fun setUp() {
        repository = mockk()
        fetchTasksUseCase = FetchTasksUseCase(repository)
    }

    @Test
    fun `when fetchTasksUseCase is invoked, then it should return tasks from repository`() = runTest {
        // Given
        val expectedTasks = listOf(
            Task(id = 1, message = "Task 1"),
            Task(id = 2, message = "Task 2")
        )
        every { repository.fetchAllTasks() } returns flowOf(expectedTasks)

        // When
        val resultFlow = fetchTasksUseCase()

        //Then
        resultFlow.test {
            assertEquals(expectedTasks, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `should return empty list when repository returns empty list`() = runTest {
        // Given
        every { repository.fetchAllTasks() } returns flowOf(emptyList())

        // When
        val resultFlow = fetchTasksUseCase()

        //Then
        resultFlow.test {
            assertEquals(emptyList<Task>(), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `should propagate error when repository throws`() = runTest {
        // Given
        val exception = RuntimeException("Error")
        every { repository.fetchAllTasks() } returns flow {
            throw exception
        }

        // When
        val resultFlow = fetchTasksUseCase()

        // Then
        resultFlow.test {
            assertEquals(exception, awaitError())
        }
    }
}
