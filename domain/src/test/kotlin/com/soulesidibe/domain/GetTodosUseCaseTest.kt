package com.soulesidibe.domain

import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.exception.NoTodosFoundException
import com.soulesidibe.domain.repository.TodoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class GetTodosUseCaseTest {

    @Test
    fun `should get the list of todos`() = runBlockingTest {
        val repository = mock(TodoRepository::class.java)
        `when`(repository.get()).thenReturn(flowOf(Response.success(listOf())))

        val useCase: GetTodosUseCase = GetTodosUseCase(repository)
        val result = useCase.execute(None())

        assertEquals(
            Response.Success<List<TodoEntity>>(listOf()),
            result.firstOrNull()
        )
    }


    @Test
    fun `should get a fail response`() = runBlockingTest {
        val repository = mock(TodoRepository::class.java)
        `when`(repository.get()).thenReturn(flowOf(Response.failure(NoTodosFoundException)))

        val useCase: GetTodosUseCase = GetTodosUseCase(repository)
        val result = useCase.execute(None())

        assertEquals(
            Response.Error<NoTodosFoundException>(NoTodosFoundException),
            result.firstOrNull()
        )
    }
}