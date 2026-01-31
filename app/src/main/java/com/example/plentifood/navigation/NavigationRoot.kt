package com.example.plentifood.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.plentifood.data.models.site.Site
import com.example.plentifood.ui.screens.home.Home
import com.example.plentifood.ui.screens.TodoDetailScreen
import com.example.plentifood.ui.screens.TodoListScreen
import com.example.plentifood.ui.screens.filters.FilterScreen
import com.example.plentifood.ui.screens.search.SearchResultScreen
import com.example.plentifood.ui.screens.site.SiteDetailScreen


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
                        SearchResultScreen(
                            onClickSiteDetail = {
                                backStack.add(Route.SiteDetail(it))
                            },
                            onClickFilterButton = {
                                backStack.add(Route.Filter)
                            }
                        )
                    }
                }

                is Route.Filter -> {
                    NavEntry(key) {
                        FilterScreen(
                            onClickBack = { backStack.removeLastOrNull() }
                        )
                    }
                }

                is Route.SiteDetail -> {
                    NavEntry(key){
                        SiteDetailScreen(
                            siteId = key.siteId,
                            onClickBack = { backStack.removeLastOrNull() }
                        )
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