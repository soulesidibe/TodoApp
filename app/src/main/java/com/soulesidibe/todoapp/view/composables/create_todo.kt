package com.soulesidibe.todoapp.view.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.soulesidibe.todoapp.R
import com.soulesidibe.todoapp.model.TodoViewModel
import com.soulesidibe.todoapp.viewmodel.TodoDetailViewModel
import com.soulesidibe.todoapp.viewmodel.ViewState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CreateTodoScreen(
    navController: NavHostController,
    viewModel: TodoDetailViewModel,
    todoId: String,
) {
    val todoState by viewModel.getTodoBy(todoId).collectAsState(initial = ViewState.idle())
    val onRemove: (String) -> Unit = { id -> viewModel.remove(id) }
    val onAddOrUpdate: (TodoViewModel) -> Unit = { viewModel.addOrUpdate(it) }
    val onNavigationUp: () -> Unit = { navController.navigateUp() }

    Surface(color = MaterialTheme.colors.background) {

        Scaffold(topBar = { CreateTodoAppBar(todoState, onRemove, onNavigationUp) }) {
            CreateTodo(
                todoState = todoState,
                addOrUpdateFlow = viewModel.addOrUpdateState,
                removeFlow = viewModel.removeState,
                onAddOrUpdate = onAddOrUpdate,
                onNavigationUp = onNavigationUp
            )
        }
    }
}

@Composable
private fun CreateTodoAppBar(
    todoId: ViewState<TodoViewModel>,
    onRemove: (String) -> Unit,
    onNavigationUp: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.str_toolbar_title))
        },
        actions = {
            CreateTodoToolbarActions(todoId, onRemove)
        },
        navigationIcon = {
            IconButton(onClick = { onNavigationUp() }) {
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
    todoId: ViewState<TodoViewModel>,
    onRemove: (String) -> Unit
) {

    todoId.getDataOrNull()?.id?.let {
        IconButton(onClick = {
            onRemove(it)
        }) {
            Icon(imageVector = Icons.Default.Delete, "Description")
        }
    }
}

@Composable
private fun CreateTodo(
    todoState: ViewState<TodoViewModel>,
    addOrUpdateFlow: StateFlow<ViewState<Boolean>>,
    removeFlow: StateFlow<ViewState<Boolean>>,
    onAddOrUpdate: (TodoViewModel) -> Unit,
    onNavigationUp: () -> Unit
) {
    var textFieldValue by remember(todoState) {
        mutableStateOf(
            todoState.getDataOrNull()?.title ?: ""
        )
    }
    val addOrUpdateState by addOrUpdateFlow.collectAsState()
    val removeState by removeFlow.collectAsState()

    when (removeState) {
        is ViewState.Failed -> {
        }
        is ViewState.Loading -> {
        }
        is ViewState.Success -> {
            onNavigationUp()
            return
        }
        is ViewState.Idle -> {
        }
    }

    when (addOrUpdateState) {
        is ViewState.Success -> {
            textFieldValue = ""
            onNavigationUp()
            return
        }
        else -> {
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Text(text = stringResource(R.string.str_add_todo_explanation))
        Spacer(modifier = Modifier.height(8.dp))

        val onClick: () -> Unit = {
            onAddOrUpdate(
                todoState.getDataOrNull()?.copy(
                    title = textFieldValue
                ) ?: TodoViewModel(title = textFieldValue)
            )
        }

        CreateTodoTitleInput(textFieldValue, { textFieldValue = it }, onClick, addOrUpdateState)
        Spacer(modifier = Modifier.height(8.dp))
        CreateTodoSubmitButton(
            onClick,
            modifier = Modifier.align(Alignment.End),
            todoState,
            addOrUpdateState,
            textFieldValue.isNotEmpty()
        )
    }
}

@Composable
private fun CreateTodoTitleInput(
    value: String,
    onTextChange: (String) -> Unit,
    onClick: () -> Unit,
    addOrUpdateState: ViewState<Boolean>?
) {
    var loadingState by remember { mutableStateOf(false) }
    var errorState: Throwable? by remember { mutableStateOf(null) }

    when (addOrUpdateState) {
        is ViewState.Loading -> loadingState = true
        is ViewState.Failed -> {
            loadingState = false
            errorState = addOrUpdateState.throwable
        }
        else -> loadingState = false
    }

    OutlinedTextField(
        value = value,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        maxLines = 1,
        enabled = !loadingState,
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
        keyboardActions = KeyboardActions(onDone = { onClick() }),
        isError = errorState != null
    )
}

@Composable
private fun CreateTodoSubmitButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    todoState: ViewState<TodoViewModel>,
    addOrUpdateState: ViewState<Boolean>?,
    enableButton: Boolean
) {
    var loadingState by remember { mutableStateOf(false) }

    loadingState = when (addOrUpdateState) {
        is ViewState.Loading -> true
        else -> false
    }

    if (loadingState) {
        Text(
            text = "Enregistrement en cours...",
            modifier = modifier,
            style = TextStyle(color = Color.Gray, fontSize = 12.sp)
        )
    } else {
        Button(modifier = modifier, onClick = onClick, enabled = enableButton) {
            val label = if (todoState is ViewState.Success) {
                stringResource(R.string.btn_edit_todo)
            } else {
                stringResource(R.string.btn_add_todo)
            }
            Text(text = label, textAlign = TextAlign.Center)
        }
    }

}