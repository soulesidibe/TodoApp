package com.soulesidibe.todoapp.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.soulesidibe.todoapp.ui.TodoViewModel
import com.soulesidibe.todocompose.data.Todo

@Composable
fun CreateTodoScreen(
    viewModel: TodoViewModel,
    navController: NavHostController,
    todo: Todo? = null
) {
    Surface(color = MaterialTheme.colors.background) {
        Scaffold(topBar = { CreateTodoAppBar(todo, viewModel, navController) }) {
            CreateTodo(todo, viewModel, navController)
        }
    }
}

@Composable
private fun CreateTodoAppBar(
    todo: Todo?,
    viewModel: TodoViewModel,
    navController: NavHostController
) {
    TopAppBar(
        title = {
            Text(text = "Add a todo")
        },
        actions = {
            CreateTodoToolbarActions(todo, viewModel, navController)
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Go back"
                )
            }
        }
    )
}

@Composable
private fun CreateTodoToolbarActions(
    todo: Todo?,
    viewModel: TodoViewModel,
    navController: NavHostController
) {
    todo?.let {
        IconButton(onClick = {
            viewModel.remove(it.id)
            navController.popBackStack()
        }) {
            Icon(imageVector = Icons.Default.Delete, "Description")
        }
    }
}

@Composable
private fun CreateTodo(
    todo: Todo?,
    viewModel: TodoViewModel,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Text(text = "Veuillez fournir un titre a votre todo.")
        Spacer(modifier = Modifier.height(8.dp))
        var textFieldValue by remember { mutableStateOf(todo?.title ?: "") }

        val onClick: () -> Unit = {
            viewModel.add(
                todo?.copy(title = textFieldValue) ?: Todo(title = textFieldValue)
            )
            textFieldValue = ""
            navController.popBackStack()
        }


        CreateTodoTitleInput(textFieldValue, onClick)
        Spacer(modifier = Modifier.height(8.dp))
        CreateTodoSubmitButton(onClick, modifier = Modifier.align(Alignment.End), todo)


    }
}

@Composable
private fun CreateTodoTitleInput(value: String, onClick: () -> Unit) {
    var textFieldValue = value
    TextField(
        value = textFieldValue,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        maxLines = 1,
        singleLine = true,
        onValueChange = {
            textFieldValue = it
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            autoCorrect = false,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { onClick() })
    )
}

@Composable
private fun CreateTodoSubmitButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    todo: Todo?
) {
    Button(modifier = modifier, onClick = onClick) {
        val label = if (todo != null) {
            "Modifier"
        } else {
            "Ajouter"
        }
        Text(text = label)
    }
}

@Preview(device = Devices.PIXEL_4)
@Composable
fun PreviewCreateTodoScreen() {
    CreateTodoScreen(viewModel = viewModel(), navController = rememberNavController())
}