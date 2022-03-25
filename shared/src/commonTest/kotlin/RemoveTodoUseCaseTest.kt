package com.soulesidibe.domain

import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.exception.NoTodoFoundException
import com.soulesidibe.domain.repository.TodoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class RemoveTodoUseCaseTest {


    @Test
    fun `should remove the todo if exsist`() = suspendTest {
        val repository = mock(TodoRepository::class.java)
        `when`(repository.byId("12345")).thenReturn(TodoEntity("12345", "test"))
        `when`(repository.remove(TodoEntity("12345", "test"))).thenReturn(true)


        val usecase = RemoveTodoUseCase(repository)
        val response = usecase.execute("12345")
        assertEquals(Response.success(true), response)
    }

    @Test
    fun `should not remove the todo if not exsist`() = suspendTest {
        val repository = mock(TodoRepository::class.java)
        `when`(repository.byId("12345")).thenReturn(null)

        val usecase = RemoveTodoUseCase(repository)
        val response = usecase.execute("12345")
        assertEquals(Response.Error<NoTodoFoundException>(NoTodoFoundException), response)
    }
}