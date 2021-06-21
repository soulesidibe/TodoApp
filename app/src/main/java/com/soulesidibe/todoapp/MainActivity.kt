package com.soulesidibe.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.soulesidibe.todoapp.ui.Screen
import com.soulesidibe.todoapp.ui.composables.CreateTodoScreen
import com.soulesidibe.todoapp.ui.composables.TodosScreen
import com.soulesidibe.todoapp.ui.theme.TodoAppTheme
import com.soulesidibe.todocompose.data.Todo

class MainActivity : ComponentActivity() {

    val viewModel: TodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.Todos.route) {

                    composable(Screen.Todos.route) {
                        // A surface container using the 'background' color from the theme
                        TodosScreen(viewModel.todosLiveData, navController = navController)
                    }

                    composable(
                        Screen.Create.route, arguments = listOf(
                            navArgument("id") { type = NavType.StringType })
                    ) {
                        CreateTodoScreen(
                            navController = navController,
                            todo = viewModel.get(it.arguments?.getString("id"))
                        )
                    }
                }
            }
        }
    }
}

class TodoViewModel : ViewModel() {
    fun get(id: String?): Todo? {
        return id?.let {
            Todo(title = "Test 1")
        }
    }

    private val _todos: MutableLiveData<List<Todo>> = MutableLiveData(
        listOf(
            Todo(title = "Test 1"),
            Todo(title = "Test 2"),
            Todo(title = "Test 3"),
            Todo(title = "Test 4"),
        )
    )
    val todosLiveData = _todos
}

