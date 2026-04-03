package com.radlab.todolist.domain.usecase

import kotlinx.coroutines.flow.Flow

/**
 * Base interface for a suspending use case that takes a single input and produces a single output.
 *
 * Implementations should encapsulate a discrete piece of business logic and be invoked
 * via the [invoke] operator, making them callable like a function.
 *
 * `@param` Input the type of the input parameter consumed by this use case (contravariant).
 * `@param` Output the type of the result produced by this use case (covariant).
 */
interface UseCase<in Input, out Output> {
    /**
     * Executes the use case with the provided [input].
     *
     * This is a suspending function and must be called from a coroutine or another suspend function.
     *
     * `@param` input the input data required to run the use case.
     * `@return` the result of type [Output].
     */
    suspend operator fun invoke(input: Input): Output
}

/**
 * Base interface for a use case that takes a single input and returns a [Flow] of outputs.
 *
 * Use this interface for use cases that stream multiple values over time rather than
 * returning a single result.
 *
 * `@param` Input the type of the input parameter consumed by this use case (contravariant).
 * `@param` Output the type of the values emitted by the resulting [Flow] (covariant).
 */
interface FlowUseCase<in Input, out Output> {
    /**
     * Executes the use case with the provided [input] and returns a [Flow] of [Output] values.
     *
     * `@param` input the input data required to run the use case.
     * `@return` a [Flow] that emits values of type [Output].
     */
    operator fun invoke(input: Input): Flow<Output>
}

/**
 * Base interface for a use case that requires no input and returns a [Flow] of outputs.
 *
 * Use this interface for use cases that observe or stream data without needing
 * any input parameter.
 *
 * `@param` Output the type of the values emitted by the resulting [Flow] (covariant).
 */
interface FlowNoInputUseCase<out Output> {
    /**
     * Executes the use case and returns a [Flow] of [Output] values.
     *
     * `@return` a [Flow] that emits values of type [Output].
     */
    operator fun invoke(): Flow<Output>
}