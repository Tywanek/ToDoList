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

/**
 * Unit tests for [FetchTasksUseCase].
 *
 * Verifies that the use case correctly exposes the task stream from [TaskRepository],
 * including normal emission, empty-list emission, and upstream error propagation.
 */
class FetchTasksUseCaseTest {

    private lateinit var repository: TaskRepository
    private lateinit var fetchTasksUseCase: FetchTasksUseCase

    /**
     * Initialises a mocked [TaskRepository] and the [FetchTasksUseCase] under test
     * before each test case.
     */
    @Before
    fun setUp() {
        repository = mockk()
        fetchTasksUseCase = FetchTasksUseCase(repository)
    }

    /**
     * Verifies that when the repository emits a non-empty list of [Task] objects,
     * [FetchTasksUseCase] forwards exactly that list to collectors and then completes.
     */
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

        // Then
        resultFlow.test {
            assertEquals(expectedTasks, awaitItem())
            awaitComplete()
        }
    }

    /**
     * Verifies that when the repository emits an empty list,
     * [FetchTasksUseCase] forwards the empty list to collectors and then completes.
     */
    @Test
    fun `should return empty list when repository returns empty list`() = runTest {
        // Given
        every { repository.fetchAllTasks() } returns flowOf(emptyList())

        // When
        val resultFlow = fetchTasksUseCase()

        // Then
        resultFlow.test {
            assertEquals(emptyList<Task>(), awaitItem())
            awaitComplete()
        }
    }

    /**
     * Verifies that when the repository's flow throws an exception,
     * [FetchTasksUseCase] propagates that same exception to collectors.
     */
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