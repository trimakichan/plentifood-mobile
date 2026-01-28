package com.example.plentifood.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.plentifood.ui.screens.home.Home
import com.example.plentifood.ui.screens.TodoDetailScreen
import com.example.plentifood.ui.screens.TodoListScreen
import com.example.plentifood.ui.screens.search.SearchResultScreen


@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(Route.Home)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when(key) {
                is Route.Home -> {
                    NavEntry(key) {
                        Home(
                            onBeginClick = { backStack.add(Route.SearchResult) }
                        )
                    }
                }

                is Route.SearchResult -> {
                    NavEntry(key) {
                        SearchResultScreen()
                    }
                }



                is Route.TodoList -> {
                    NavEntry(key) {
                        TodoListScreen(
                            onTodoClick = {
                                backStack.add(Route.TodoDetail(it))
                            }
                        )
                    }
                }
                is Route.TodoDetail -> {
                    NavEntry(key) {
                        TodoDetailScreen(
                            todo = key.todo
                        )
                    }
                }
                else -> error("Unknown NavKey: $key ")
            }
        }
    )
}