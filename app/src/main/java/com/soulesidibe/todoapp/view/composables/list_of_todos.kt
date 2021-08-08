package com.soulesidibe.todoapp.view.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavHostController
import com.soulesidibe.todoapp.R
import com.soulesidibe.todoapp.model.TodoViewModel
import com.soulesidibe.todoapp.view.Screen
import com.soulesidibe.todoapp.view.theme.Typography
import com.soulesidibe.todoapp.viewmodel.ViewState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TodosScreen(
    data: StateFlow<ViewState<List<TodoViewModel>>>,
    navController: NavHostController
) {

    val todosState by data.collectAsState()

    Surface(color = MaterialTheme.colors.background) {
        val createTodo = {
            navController.navigate(Screen.CreateTodoScreen.route)
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Todo Compose")
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = createTodo) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "add a todo")
                }
            }
        ) {
            when (todosState) {
                is ViewState.Failed -> {
                    //Show Empty view
                    TodosEmptyView { createTodo() }
                }
                is ViewState.Loading -> {
                    TodosCircularProgress()
                }
                is ViewState.Success -> {
                    TodoList(
                        onClick = { todo ->
                            navController.navigate(Screen.CreateTodoScreen.createRoute(todo.id))
                        },
                        todos = (todosState as ViewState.Success<List<TodoViewModel>>).data
                    )

                }
                is ViewState.Idle -> {
                    TodosEmptyView { createTodo() }
                }
            }
        }
    }
}

@Composable
private fun TodosCircularProgress() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(), contentAlignment = Center) {

        CircularProgressIndicator()
    }
}

@Composable
fun TodoList(
    onClick: (TodoViewModel) -> Unit,
    todos: List<TodoViewModel>,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        items(items = todos, key = { it.id }) { todo: TodoViewModel ->
            TodoItem(todoViewModel = todo, onItemClick = { onClick(todo) })
        }
    }
}

@Composable
fun TodosEmptyView(onAdd: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(), contentAlignment = Alignment.Center
    ) {

        Column {
            Icon(
                modifier = Modifier.align(CenterHorizontally),
                painter = painterResource(id = R.drawable.ic_to_do_list),
                tint = Color.Gray,
                contentDescription = "Empty todo list"
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Vous n'avez pas encore de todo :(", color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Ajouter un nouveau todo",
                color = Color.Gray,
                modifier = Modifier
                    .clickable { onAdd() }
                    .padding(8.dp)
                    .align(CenterHorizontally),
                style = TextStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)
            )

        }

    }
}

@Composable
fun TodoItem(onItemClick: () -> Unit, todoViewModel: TodoViewModel) {
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
            todoViewModel.title, modifier = Modifier
                .fillMaxWidth()
                .layoutId("title"), style = Typography.body1
        )
    }
}

@Preview(heightDp = 100, name = "Todo item", widthDp = 411, showBackground = true)
@Composable
fun PreviewTodoItem() {
    TodoItem(todoViewModel = TodoViewModel(title = "This is a test todo"), onItemClick = {})
}

@Preview(device = Devices.PIXEL_4, showSystemUi = true, name = "The todo list")
@Composable
fun PreviewTodoList() {
    TodoList(
        onClick = { },
        todos = listOf()
    )

}