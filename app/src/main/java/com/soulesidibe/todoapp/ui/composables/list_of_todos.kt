package com.soulesidibe.todoapp.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import com.soulesidibe.todoapp.ui.Screen
import com.soulesidibe.todoapp.ui.theme.Typography
import com.soulesidibe.todocompose.data.Todo

@Composable
fun TodosScreen(todosLiveData: LiveData<List<Todo>>, navController: NavHostController) {

    val todosState by todosLiveData.observeAsState()

    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Todo Compose")
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    navController.navigate(Screen.Create.route)
                }) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "add a todo")
                }
            }
        ) {
            TodoList(
                todos = todosState,
                onClick = { todo ->
                    navController.navigate(Screen.Create.createRoute(todo.id))
                }
            )

        }
    }
}

@Composable
fun TodoList(onClick: (Todo) -> Unit, todos: List<Todo>?, modifier: Modifier = Modifier) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        items(items = todos!!, key = { it.id }) { todo: Todo ->
            TodoItem(todo = todo, onItemClick = { onClick(todo) })
        }
    }
}

@Composable
fun TodoItem(onItemClick: () -> Unit, todo: Todo) {
    val constraintSet = ConstraintSet {
        val titleRef = createRefFor("title")

        constrain(titleRef) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }
    ConstraintLayout(constraintSet, modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
        .clickable { onItemClick() }
        .padding(PaddingValues(horizontal = 16.dp, vertical = 8.dp))
    ) {

        Text(
            todo.title, modifier = Modifier
                .fillMaxWidth()
                .layoutId("title"), style = Typography.body1
        )
    }
}

@Preview(heightDp = 100, name = "Todo item", widthDp = 411, showBackground = true)
@Composable
fun PreviewTodoItem() {
    TodoItem(todo = Todo(title = "This is a test todo"), onItemClick = {})
}

@Preview(device = Devices.PIXEL_4, showSystemUi = true, name = "The todo list")
@Composable
fun PreviewTodoList() {
    TodoList(
        todos = listOf(
            Todo(title = "Test 1"),
            Todo(title = "Test 2"),
            Todo(title = "Test 3"),
            Todo(title = "Test 4"),
        ),
        onClick = { }

    )

}