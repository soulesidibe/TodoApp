package com.soulesidibe.device.datasource

//import com.soulesidibe.data.model.TodoData
//import com.soulesidibe.device.db.dao.TodoDao
//import com.soulesidibe.device.model.TodoDb
//import io.mockk.verify
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.firstOrNull
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.test.runBlockingTest
//import org.junit.Assert.*
//import org.junit.Test
//import org.mockito.ArgumentMatchers
//import org.mockito.Mockito.*

//@ExperimentalCoroutinesApi
//class TodoLocalDataSourceImplTest {
//
//    @Test
//    fun `should get all todos`() = runBlockingTest {
//        val dao = mock(TodoDao::class.java)
//        `when`(dao.getAll()).thenReturn(
//            flowOf(
//                listOf(
//                    TodoDb("1234", "test 1"),
//                    TodoDb("1235", "test 2")
//                )
//            )
//        )
//
//        val localDataSource = TodoLocalDataSourceImpl(dao)
//        val all = localDataSource.getAll()
//        assertEquals(
//            listOf(
//                TodoData("1234", "test 1"),
//                TodoData("1235", "test 2")
//            ),
//            all.firstOrNull()
//        )
//        verify(dao).getAll()
//    }
//
//    @Test
//    fun `should get a todo when id exists`() = runBlockingTest {
//        val dao = mock(TodoDao::class.java)
//        `when`(dao.getById("12345")).thenReturn(TodoDb("12345", "Test todo"))
//
//        val localDataSource = TodoLocalDataSourceImpl(dao)
//        val by = localDataSource.getBy("12345")
//        verify(dao).getById("12345")
//        assertNotNull(by)
//        assertEquals(TodoData("12345", "Test todo"), by)
//    }
//
//    @Test
//    fun `should get null  when id does not exist`() = runBlockingTest {
//        val dao = mock(TodoDao::class.java)
//        `when`(dao.getById("12345")).thenReturn(null)
//
//        val localDataSource = TodoLocalDataSourceImpl(dao)
//        val by = localDataSource.getBy("12345")
//        verify(dao).getById("12345")
//        assertNull(by)
//    }
//
//
//    @Test
//    fun `should add a todo`() = runBlockingTest {
//        val dao = mock(TodoDao::class.java)
//        `when`(dao.insert(TodoDb("12345", "Test"))).thenReturn(12345)
//
//        val localDataSource = TodoLocalDataSourceImpl(dao)
//        val result = localDataSource.insert(TodoData("12345", "Test"))
//        verify(dao).insert(TodoDb("12345", "Test"))
//        assertEquals(true, result)
//    }
//
//
//    @Test
//    fun `should failed adding todo on sql issue`() = runBlockingTest {
//        val dao = mock(TodoDao::class.java)
//        `when`(dao.insert(TodoDb("12345", "Test"))).thenReturn(-1)
//
//        val localDataSource = TodoLocalDataSourceImpl(dao)
//        val result = localDataSource.insert(TodoData("12345", "Test"))
//        verify(dao).insert(TodoDb("12345", "Test"))
//        assertEquals(false, result)
//    }
//
//    @Test
//    fun `should remove a todo`() = runBlockingTest {
//        val dao = mock(TodoDao::class.java)
//        val localDataSource = TodoLocalDataSourceImpl(dao)
//        localDataSource.remove(TodoData("12345", "Test"))
//        verify(dao).delete(TodoDb("12345", "Test"))
//        verifyNoMoreInteractions(dao)
//    }
//}