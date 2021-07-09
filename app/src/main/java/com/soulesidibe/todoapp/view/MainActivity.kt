package com.soulesidibe.todoapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soulesidibe.todoapp.model.TodoViewModel
import com.soulesidibe.todoapp.view.composables.CreateTodoScreen
import com.soulesidibe.todoapp.view.composables.TodosScreen
import com.soulesidibe.todoapp.view.theme.TodoAppTheme
import com.soulesidibe.todoapp.viewmodel.TodoDetailViewModel
import com.soulesidibe.todoapp.viewmodel.TodoListViewModel
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val listViewModel: TodoListViewModel by viewModel()
    private val todoDetailViewModel: TodoDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            val color = MaterialTheme.colors.primary

            SideEffect {
                systemUiController.setStatusBarColor(
                    color = color
                )
            }

            TodoAppTheme() {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.TodosScreen.route
                ) {

                    composable(Screen.TodosScreen.route) {
                        // A surface container using the 'background' color from the theme
                        TodosScreen(listViewModel.todosLiveData, navController = navController)
                    }

                    composable(
                        Screen.CreateTodoScreen.route, arguments = listOf(
                            navArgument("id") { type = NavType.StringType })
                    ) {
                        CreateTodoScreen(
                            viewModel = get(),
                            navController = navController,
                            todoViewModel = listViewModel.get(it.arguments?.getString("id"))
                        )
                    }
                }
            }
        }
    }
}

