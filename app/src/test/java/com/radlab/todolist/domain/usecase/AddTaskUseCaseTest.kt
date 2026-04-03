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

/**
 * Unit tests for [AddTaskUseCase].
 *
 * Verifies that the use case correctly delegates to [TaskRepository.addTask]
 * for non-blank input and suppresses the call entirely for blank input.
 */
class AddTaskUseCaseTest {

    private lateinit var repository: TaskRepository
    private lateinit var addTaskUseCase: AddTaskUseCase

    /**
     * Initialises a mocked [TaskRepository] and the [AddTaskUseCase] under test
     * before each test case.
     */
    @Before
    fun setUp() {
        repository = mockk()
        addTaskUseCase = AddTaskUseCase(repository)
    }

    /**
     * Verifies that [AddTaskUseCase] calls [TaskRepository.addTask] with the
     * provided message when the input is a non-blank string.
     */
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

    /**
     * Verifies that [AddTaskUseCase] does NOT call [TaskRepository.addTask]
     * when the input is a blank (whitespace-only) string.
     */
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