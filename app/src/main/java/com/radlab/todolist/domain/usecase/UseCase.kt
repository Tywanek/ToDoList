package com.radlab.todolist.domain.usecase

import kotlinx.coroutines.flow.Flow

interface UseCase<in Input, out Output> {
    suspend operator fun invoke (input: Input): Output
}

interface FlowUseCase<in Input, out Output> {
    operator fun invoke(input: Input): Flow<Output>
}

interface FlowNoInputUseCase<out Output> {
    operator fun invoke(): Flow<Output>
}
