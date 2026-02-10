package com.example.plentifood.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plentifood.R
import com.example.plentifood.ui.composables.PrimaryButton
import com.example.plentifood.ui.composables.SecondaryButton
import com.example.plentifood.ui.theme.PlentifoodTheme


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onBeginClick: () -> Unit,
    onStaffLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {

//            Text("PlentiFood",
//                style = MaterialTheme.typography.bodyLarge,
//                fontWeight = FontWeight.Bold,
//                color = MaterialTheme.colorScheme.primary
//                )

            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "PlentiFood Logo",
                modifier = Modifier
                    .size(78.dp)
                    .testTag("home_logo")
            )


            Spacer(modifier = Modifier.height(24.dp))


            Text(
                "Welcome to the PlentiFood",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("home_title")
            )

            Image(
                painter = painterResource(R.drawable.salad),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .testTag("home_image"),
                contentScale = ContentScale.Crop
            )

            Column (
                modifier = Modifier.testTag("home_buttons"),
            ) {
                PrimaryButton(
                    "Let's begin",
                    modifier = Modifier.testTag("home_begin_button"),
                    onButtonClick = onBeginClick,
                )
                Spacer(modifier = Modifier.height(14.dp))
                SecondaryButton(
                    "Staff Login",
                    onButtonClick = onStaffLoginClick,
                    modifier = Modifier.testTag("home_staff_button")
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Don't have a staff account? ",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary,
                )

                Text(
                    "Sign up",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier
                        .clickable { onSignUpClick() }
                        .testTag("home_sign_up_button"),
                )
            }

        }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    PlentifoodTheme {
//        Home( onBeginClick)
    }
}
