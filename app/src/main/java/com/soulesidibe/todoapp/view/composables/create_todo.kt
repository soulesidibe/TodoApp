package com.soulesidibe.todoapp.view.composables

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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.soulesidibe.todoapp.model.TodoViewModel
import com.soulesidibe.todoapp.viewmodel.TodoDetailViewModel
import com.soulesidibe.todoapp.viewmodel.ViewState

@Composable
fun CreateTodoScreen(
    viewModel: TodoDetailViewModel,
    navController: NavHostController,
    todoViewModel: TodoViewModel? = null
) {
    Surface(color = MaterialTheme.colors.background) {
        Scaffold(topBar = { CreateTodoAppBar(todoViewModel, viewModel, navController) }) {
            CreateTodo(todoViewModel, viewModel, navController)
        }
    }
}

@Composable
private fun CreateTodoAppBar(
    todoViewModel: TodoViewModel?,
    viewModel: TodoDetailViewModel,
    navController: NavHostController
) {
    TopAppBar(
        title = {
            Text(text = "Add a todo")
        },
        actions = {
            CreateTodoToolbarActions(todoViewModel, viewModel)
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
    todoViewModel: TodoViewModel?,
    viewModel: TodoDetailViewModel
) {

    todoViewModel?.let {
        IconButton(onClick = {
            viewModel.remove(it.id)
        }) {
            Icon(imageVector = Icons.Default.Delete, "Description")
        }
    }
}

@Composable
private fun CreateTodo(
    todoViewModel: TodoViewModel?,
    viewModel: TodoDetailViewModel,
    navController: NavHostController
) {

    var textFieldValue by remember { mutableStateOf(todoViewModel?.title ?: "") }
    val addOrUpdateState by viewModel.addOrUpdateLiveData.observeAsState()

    val removeState by viewModel.removeLiveData.observeAsState()

    removeState?.let {
        when (it) {
            is ViewState.Failed -> {

            }
            is ViewState.Loading -> {

            }
            is ViewState.Success -> {
                navController.popBackStack()
            }
        }
        return
    }

    addOrUpdateState?.let {


        when (addOrUpdateState) {
            is ViewState.Failed -> {

            }
            is ViewState.Loading -> {

            }
            is ViewState.Success -> {
                textFieldValue = ""
                navController.popBackStack()
            }
            null -> {

            }
        }
        return
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Text(text = "Veuillez fournir un titre a votre todo.")
        Spacer(modifier = Modifier.height(8.dp))

        val onClick: () -> Unit = {
            viewModel.addOrUpdate(
                todoViewModel?.copy(title = textFieldValue) ?: TodoViewModel(title = textFieldValue)
            )
        }

        CreateTodoTitleInput(textFieldValue, { textFieldValue = it }, onClick)
        Spacer(modifier = Modifier.height(8.dp))
        CreateTodoSubmitButton(onClick, modifier = Modifier.align(Alignment.End), todoViewModel)


    }
}

@Composable
private fun CreateTodoTitleInput(
    value: String,
    onTextChange: (String) -> Unit,
    onClick: () -> Unit
) {

    OutlinedTextField(
        value = value,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        maxLines = 1,
        label = { Text(text = "Title") },
        singleLine = true,
        onValueChange = {
            onTextChange(it)
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
    todoViewModel: TodoViewModel?
) {
    Button(modifier = modifier, onClick = onClick) {
        val label = if (todoViewModel != null) {
            "Modifier"
        } else {
            "Ajouter"
        }
        Text(text = label)
    }
}