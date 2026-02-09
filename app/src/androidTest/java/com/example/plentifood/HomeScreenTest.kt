package com.example.plentifood

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.plentifood.ui.screens.home.HomeScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun checkTitle_showsCorrectTitle() {
        rule.setContent {
            HomeScreen(
                onBeginClick = {},
                onStaffLoginClick = {},
                onSignUpClick = {},
            )
        }

        rule.onNodeWithTag("home_title").assertIsDisplayed()
        rule.onNodeWithTag("home_begin_button").assertIsDisplayed()
        rule.onNodeWithTag("home_staff_button").assertIsDisplayed()
        rule.onNodeWithTag("home_sign_up_button").assertIsDisplayed()
    }

    @Test
    fun checkLogo_showsCorrectLogo() {
        rule.setContent {
            HomeScreen(
                onBeginClick = {},
                onStaffLoginClick = {},
                onSignUpClick = {},
            )
        }

        rule.onNodeWithTag("home_logo").assertIsDisplayed()
        rule.onNodeWithTag("home_image").assertIsDisplayed()
    }

    @Test
    fun clickingBeginButton_callsOnBeginClick() {
        rule.setContent {
            HomeScreen(
                onBeginClick = {},
                onStaffLoginClick = {},
                onSignUpClick = {},
            )
        }

        rule.onNodeWithTag("home_begin_button").performClick()
        rule.onNodeWithTag("home_staff_button").performClick()
        rule.onNodeWithTag("home_sign_up_button").performClick()
    }

}

