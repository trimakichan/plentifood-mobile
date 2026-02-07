package com.example.plentifood.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.plentifood.ui.screens.home.HomeScreen
import com.example.plentifood.ui.screens.TodoDetailScreen
import com.example.plentifood.ui.screens.TodoListScreen
import com.example.plentifood.ui.screens.dashboard.AdminDashboardScreen
import com.example.plentifood.ui.screens.dashboard.AdminDashboardViewModel
import com.example.plentifood.ui.screens.filters.FilterScreen
import com.example.plentifood.ui.screens.search.SearchResultScreen
import com.example.plentifood.ui.screens.search.SearchResultViewModel
import com.example.plentifood.ui.screens.site.SiteDetailScreen
import com.example.plentifood.ui.screens.filters.FilterViewModel
import com.example.plentifood.ui.screens.login.LoginScreen
import com.example.plentifood.ui.screens.signup.SignUpScreen


@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(Route.Home)
    val searchResultViewModel: SearchResultViewModel = viewModel()
    val filterViewModel: FilterViewModel = viewModel()


    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is Route.Home -> {
                    NavEntry(key) {
                        HomeScreen(
                            onBeginClick = { backStack.add(Route.SearchResult) },
                            onStaffLoginClick = { backStack.add(Route.Login) },
                            onSignUpClick = { backStack.add(Route.SignUp) }
                        )
                    }
                }

                is Route.Login -> {
                    NavEntry(key) {
                        LoginScreen(
                            onClickBack = { backStack.removeLastOrNull() },
                            onNavigateToDashboard = { organizationId, adminInfo ->
                                backStack.add(Route.Dashboard(
                                organizationId,
                                adminInfo
                            )) }
                        )
                    }
                }

                is Route.SignUp -> {
                    NavEntry(key) {
                        SignUpScreen(
                            onClickBack = { backStack.removeLastOrNull() },
                            onClickStaffLogin = { backStack.add(Route.Login) },
                            onNavigateToDashboard = { organizationId, adminInfo ->
                                backStack.add(Route.Dashboard(
                                    organizationId,
                                    adminInfo
                                )) }
                        )
                    }
                }


                is Route.Dashboard -> {
                    NavEntry(key) {
                        AdminDashboardScreen(
                            organizationId = key.organizationId,
                            username = key.adminInfo,
                            onClickHome = {
                                backStack.clear()
                                backStack.add(Route.Home)
                            },
                            onClickSiteDetail = {
                                backStack.add(Route.SiteDetail(it))
                            }
                        )
                    }
                }



                is Route.SearchResult -> {
                    NavEntry(key) {
                        SearchResultScreen(
                            viewModel = searchResultViewModel,
                            onClickSiteDetail = {
                                backStack.add(Route.SiteDetail(it))
                            },
                            onClickFilterButton = {
                                backStack.add(Route.Filter)
                            },
                            onClickBack = { backStack.removeLastOrNull() }
                        )
                    }
                }

                is Route.Filter -> {
                    NavEntry(key) {
                        FilterScreen(
                            searchViewModel = searchResultViewModel,
                            filterViewModel = filterViewModel,
                            onClickBack = { backStack.removeLastOrNull() }
                        )
                    }
                }

                is Route.SiteDetail -> {
                    NavEntry(key) {
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


