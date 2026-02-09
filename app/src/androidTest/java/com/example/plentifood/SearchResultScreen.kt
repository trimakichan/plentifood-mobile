package com.example.plentifood

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.plentifood.ui.screens.search.SearchResultContent
import com.example.plentifood.ui.screens.search.SearchResultUiState
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class SearchResultContentTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun whenMapSelected_andLoading_showsLoadingIndicator() {
        rule.setContent {
            SearchResultContent(
                state = SearchResultUiState(
                    isLoading = true,
                    numOfFilters = 2,
                    totalResults = 0,
                    sites = emptyList(),
                    selectedIndex = 0 // Map
                ),
                onSelectedIndexChange = {},
                onClickSiteDetail = {}
            )
        }

        rule.onNodeWithTag("results_loading").assertIsDisplayed()
    }

    @Test
    fun switchingToList_showsListContainer() {
        var selected = 0

        rule.setContent {
            SearchResultContent(
                state = SearchResultUiState(
                    isLoading = false,
                    numOfFilters = 0,
                    totalResults = 3,
                    sites = emptyList(),
                    selectedIndex = selected
                ),
                onSelectedIndexChange = { selected = it },
                onClickSiteDetail = {}
            )
        }

        rule.onNodeWithText("List").performClick()

        rule.runOnIdle { assertEquals(1, selected) }
    }

}
