package com.soulesidibe.data.repository

import com.soulesidibe.data.datasource.TodoLocalDataSource
import com.soulesidibe.data.model.TodoData
import com.soulesidibe.data.model.mapper.toEntity
import com.soulesidibe.domain.Response
import com.soulesidibe.domain.entity.TodoEntity
import com.soulesidibe.domain.exception.CannotAddOrUpdateException
import com.soulesidibe.domain.exception.CannotRemoveTodoException
import com.soulesidibe.domain.exception.NoTodosFoundException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.sql.SQLException

@ExperimentalCoroutinesApi
class TodoRepositoryImplTest {

    @Test
    fun `should get the list of todos`() = runBlockingTest {
        val dataSource = mock(TodoLocalDataSource::class.java)
        `when`(dataSource.getAll()).thenReturn(
            flowOf(
                listOf(
                    TodoData(
                        id = "1344",
                        title = "test"
                    )
                )
            )
        )

        val repos = TodoRepositoryImpl(dataSource)
        val result = repos.get()
        assertEquals(
            Response.success(listOf(TodoData(id = "1344", title = "test").toEntity())),
            result.firstOrNull()
        )
    }

    @Test
    fun `should get a failure when no data found`() = runBlockingTest {
        val dataSource = mock(TodoLocalDataSource::class.java)
        `when`(dataSource.getAll()).thenReturn(flowOf(listOf()))

        val repos = TodoRepositoryImpl(dataSource)
        val result = repos.get()
        assertEquals(
            Response.failure<NoTodosFoundException>(NoTodosFoundException),
            result.firstOrNull()
        )
    }

    @Test
    fun `should get a todo when id exists`() = runBlockingTest {
        val dataSource = mock(TodoLocalDataSource::class.java)
        `when`(dataSource.getBy("12345")).thenReturn(TodoData(id = "1344", title = "test"))

        val repos = TodoRepositoryImpl(dataSource)
        val result = repos.byId("12345")
        assertNotNull(result)
        assertEquals(
            TodoData(id = "1344", title = "test").toEntity(),
            result
        )
    }

    @Test
    fun `should get null when id does not exist`() = runBlockingTest {
        val dataSource = mock(TodoLocalDataSource::class.java)
        `when`(dataSource.getBy("12345")).thenReturn(null)

        val repos = TodoRepositoryImpl(dataSource)
        val result = repos.byId("12345")
        assertNull(result)
        assertEquals(null, result)
    }

    @Test
    fun `should add a new todo`() = runBlockingTest {
        val dataSource = mock(TodoLocalDataSource::class.java)
        `when`(dataSource.insert(TodoData(id = "1344", title = "test"))).thenReturn(true)

        val repos = TodoRepositoryImpl(dataSource)
        val result = repos.addOrUpdate(TodoData(id = "1344", title = "test").toEntity())
        assertEquals(true, result)

    }

    @Test(expected = CannotAddOrUpdateException::class)
    fun `should throws an exception on sql issue`() = runBlockingTest {
        val dataSource = mock(TodoLocalDataSource::class.java)
        `when`(dataSource.insert(TodoData(id = "1344", title = "test"))).thenThrow(SQLException())

        val repos = TodoRepositoryImpl(dataSource)
        repos.addOrUpdate(TodoData(id = "1344", title = "test").toEntity())
    }

    @Test
    fun `should remove a todo`() = runBlockingTest {
        val dataSource = mock(TodoLocalDataSource::class.java)
        val repos = TodoRepositoryImpl(dataSource)
        assertEquals(true, repos.remove(TodoEntity(title = "Test")))
    }

    @Test(expected = CannotRemoveTodoException::class)
    fun `should throw an exception when todo not removed`() = runBlockingTest {
        val dataSource = mock(TodoLocalDataSource::class.java)
        `when`(dataSource.remove(TodoData(id = "1344", title = "test"))).thenThrow(SQLException())

        val repos = TodoRepositoryImpl(dataSource)
        repos.remove(TodoEntity(id= "1344", title = "test"))
    }


}