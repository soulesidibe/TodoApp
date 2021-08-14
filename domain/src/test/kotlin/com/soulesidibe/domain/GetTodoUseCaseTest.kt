package com.soulesidibe.domain

import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.exception.NoTodoFoundException
import com.soulesidibe.domain.repository.TodoRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class GetTodoUseCaseTest {

    @Test
    fun `should get a todo`() = runBlockingTest {
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
    fun `should get an error when no todo id do no exist`() = runBlockingTest {
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