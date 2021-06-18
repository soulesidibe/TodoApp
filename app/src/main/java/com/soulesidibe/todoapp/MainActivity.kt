package com.soulesidibe.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.soulesidibe.todoapp.ui.Screen
import com.soulesidibe.todoapp.ui.composables.CreateTodoScreen
import com.soulesidibe.todoapp.ui.composables.TodosScreen
import com.soulesidibe.todoapp.ui.theme.TodoAppTheme
import com.soulesidibe.todocompose.data.Todo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.Todos.route) {
                    val modifier = Modifier.padding(PaddingValues(all = 8.dp))
                    composable(Screen.Todos.route) {
                        // A surface container using the 'background' color from the theme
                        TodosScreen(navController = navController)
                    }

                    composable(Screen.Create.route) {
                        CreateTodoScreen(navController = navController)
                    }
                }
            }
        }
    }
}