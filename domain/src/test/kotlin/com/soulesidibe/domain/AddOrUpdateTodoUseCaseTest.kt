package com.soulesidibe.domain

import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.exception.CannotAddOrUpdateException
import com.soulesidibe.domain.repository.TodoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class AddOrUpdateTodoUseCaseTest {


    @Test
    fun `should add or update a todo`() = runBlockingTest {
        val todo = TodoEntity("12345", "test todo")
        val repository = mock(TodoRepository::class.java)
        `when`(repository.addOrUpdate(todo)).thenReturn(true)

        val usecase = AddOrUpdateTodoUseCase(repository)
        val responseResult = usecase.execute(todo)
        assertEquals(ResponseResult.success(true), responseResult)
    }

    @Test
    fun `should return a fail response`() = runBlockingTest {
        val todo = TodoEntity("12345", "test todo")
        val repository = mock(TodoRepository::class.java)
        `when`(repository.addOrUpdate(todo)).thenThrow(CannotAddOrUpdateException)

        val useCase = AddOrUpdateTodoUseCase(repository)
        val responseResult = useCase.execute(todo)
        assertEquals(
            ResponseResult.Error<CannotAddOrUpdateException>(CannotAddOrUpdateException),
            responseResult
        )
    }
}