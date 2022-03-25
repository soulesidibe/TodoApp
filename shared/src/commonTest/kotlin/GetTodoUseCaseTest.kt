package com.soulesidibe.domain

import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.exception.NoTodoFoundException
import com.soulesidibe.domain.repository.TodoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class GetTodoUseCaseTest {

    @Test
    fun `should get a todo`() = suspendTest {
        val fakeId = "12345"
        val repository = mock(TodoRepository::class.java)
        `when`(repository.byId(fakeId)).thenReturn(TodoEntity("12345", "Test"))

        val usecase = GetTodoUseCase(repository)

        val response = usecase.execute(fakeId)
        assertEquals(
            Response.success(TodoEntity("12345", "Test")),
            response
        )
    }

    @Test
    fun `should get an error when no todo id do no exist`() = suspendTest {
        val fakeId = "12345"
        val repository = mock(TodoRepository::class.java)
        `when`(repository.byId(fakeId)).thenReturn(null)

        val usecase = GetTodoUseCase(repository)

        val response = usecase.execute(fakeId)
        assertEquals(
            Response.failure<NoTodoFoundException>(NoTodoFoundException),
            response
        )
    }
}