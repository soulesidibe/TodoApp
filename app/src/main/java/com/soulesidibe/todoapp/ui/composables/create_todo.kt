package com.soulesidibe.todoapp.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.soulesidibe.todocompose.data.Todo

@Composable
fun CreateTodoScreen(todo: Todo? = null, navController: NavHostController) {
    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Add a todo")
                    },
                    actions = {
                        todo?.let {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Default.Delete, "Description")
                            }
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                Text(text = "Veuillez fournir un titre a votre todo.")
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = todo?.title ?: "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    onValueChange = { _ ->

                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        autoCorrect = false,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        navController.popBackStack()
                    })
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(modifier = Modifier.align(Alignment.End), onClick = {
                    navController.popBackStack()
                }) {
                    val label = if (todo != null) {
                        "Modifier"
                    } else {
                        "Ajouter"
                    }
                    Text(text = label)
                }


            }
        }
    }
}

@Preview(device = Devices.PIXEL_4)
@Composable
fun PreviewCreateTodoScreen() {
    CreateTodoScreen(navController = rememberNavController(), todo = Todo(title = "Hello from KM City"))
}