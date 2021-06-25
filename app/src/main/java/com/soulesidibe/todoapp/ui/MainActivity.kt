package com.soulesidibe.todoapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soulesidibe.todoapp.ui.composables.CreateTodoScreen
import com.soulesidibe.todoapp.ui.composables.TodosScreen
import com.soulesidibe.todoapp.ui.theme.TodoAppTheme

class MainActivity : ComponentActivity() {

    private val viewModel: TodoViewModel by viewModels()

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
                            viewModel = viewModel,
                            navController = navController,
                            todo = viewModel.get(it.arguments?.getString("id"))
                        )
                    }
                }
            }
        }
    }
}

