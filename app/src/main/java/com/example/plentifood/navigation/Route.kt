package com.example.plentifood.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

// seals means only a fixed, known set of types is allowed.
// No other classes can be added outside this file.
@Serializable
sealed interface Route: NavKey {


    @Serializable
    data object Home:  Route, NavKey

    @Serializable
    data object SearchResult: Route, NavKey

    @Serializable
    data object Filter: Route, NavKey

    @Serializable
    data class SiteDetail(val siteId: Int): Route, NavKey

    @Serializable
    data object Login: Route, NavKey

    @Serializable
    data object SignUp: Route, NavKey

    @Serializable
    data class Dashboard(
        val organizationId: Int,
        val adminInfo: String
    ) : Route, NavKey
    @Serializable
    data object TodoList: Route, NavKey

    @Serializable
    data class TodoDetail(val todo:String): Route, NavKey


}