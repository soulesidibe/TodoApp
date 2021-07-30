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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import com.soulesidibe.todoapp.model.TodoViewModel
import com.soulesidibe.todoapp.viewmodel.TodoDetailViewModel
import com.soulesidibe.todoapp.viewmodel.ViewState

@Composable
fun CreateTodoScreen(
    navController: NavHostController,
    todoViewModel: TodoViewModel? = null,
    addOrUpdateLiveData: LiveData<ViewState<Boolean>>,
    removeLiveData: LiveData<ViewState<Boolean>>,
    onAddOrUpdate: (TodoViewModel) -> Unit,
    onRemove: (String) -> Unit
) {
    Surface(color = MaterialTheme.colors.background) {
        Scaffold(topBar = { CreateTodoAppBar(todoViewModel, onRemove, navController) }) {
            CreateTodo(
                todoViewModel = todoViewModel,
                addOrUpdateLiveData = addOrUpdateLiveData,
                removeLiveData = removeLiveData,
                onAddOrUpdate = onAddOrUpdate,
                navController = navController
            )
        }
    }
}

@Composable
private fun CreateTodoAppBar(
    todoViewModel: TodoViewModel?,
    onRemove: (String) -> Unit,
    navController: NavHostController
) {
    TopAppBar(
        title = {
            Text(text = "Add a todo")
        },
        actions = {
            CreateTodoToolbarActions(todoViewModel, onRemove)
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
    onRemove: (String) -> Unit
) {

    todoViewModel?.let {
        IconButton(onClick = {
            onRemove(it.id)
        }) {
            Icon(imageVector = Icons.Default.Delete, "Description")
        }
    }
}

@Composable
private fun CreateTodo(
    todoViewModel: TodoViewModel?,
    addOrUpdateLiveData: LiveData<ViewState<Boolean>>,
    removeLiveData: LiveData<ViewState<Boolean>>,
    onAddOrUpdate: (TodoViewModel) -> Unit,
    navController: NavHostController
) {
    var textFieldValue by remember { mutableStateOf(todoViewModel?.title ?: "") }
    val addOrUpdateState by addOrUpdateLiveData.observeAsState()

    val removeState by removeLiveData.observeAsState()

    removeState?.let {
        when (it) {
            is ViewState.Failed -> {
                // Todo like addOrUpdateState
            }
            is ViewState.Loading -> {
                // Todo like addOrUpdateState
            }
            is ViewState.Success -> {
                navController.popBackStack()
            }
        }
        return
    }

    addOrUpdateState?.let {
        when (addOrUpdateState) {
            is ViewState.Success -> {
                textFieldValue = ""
                navController.navigateUp()
                return
            }
            else -> {
            }
        }
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
            onAddOrUpdate(
                todoViewModel?.copy(title = textFieldValue)
                    ?: TodoViewModel(title = textFieldValue)
            )
        }

        CreateTodoTitleInput(textFieldValue, { textFieldValue = it }, onClick, addOrUpdateState)
        Spacer(modifier = Modifier.height(8.dp))
        CreateTodoSubmitButton(
            onClick,
            modifier = Modifier.align(Alignment.End),
            todoViewModel,
            addOrUpdateState
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
    todoViewModel: TodoViewModel?,
    addOrUpdateState: ViewState<Boolean>?
) {
    var loadingState by remember { mutableStateOf(false) }

    loadingState = when (addOrUpdateState) {
        is ViewState.Loading -> true
        else -> false
    }

    Button(modifier = modifier, onClick = onClick, enabled = !loadingState) {
        val label = if (todoViewModel != null) {
            "Modifier"
        } else {
            "Ajouter"
        }
        Text(text = label, textAlign = TextAlign.Center)
    }
}