package com.soulesidibe.domain

import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.exception.NoTodosFoundException
import com.soulesidibe.domain.repository.TodoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class GetTodosUseCaseTest {

    @Test
    fun `should get the list of todos`() = suspendTest {
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
    fun `should get a fail response`() = suspendTest {
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