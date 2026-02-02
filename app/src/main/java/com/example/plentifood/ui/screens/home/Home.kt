package com.example.plentifood.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plentifood.R
import com.example.plentifood.ui.composables.PrimaryButton
import com.example.plentifood.ui.composables.SecondaryButton
import com.example.plentifood.ui.theme.PlentifoodTheme
import com.example.plentifood.ui.screens.home.HomeViewModel


@Composable
fun Home(
    modifier: Modifier = Modifier,
    onBeginClick: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    if (state.isLoading) {
        CircularProgressIndicator()
    } else {

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {

            Text("PlentiFood",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
                )

            Spacer(modifier = Modifier.height(24.dp))


            Text(
                "Welcome to the PlentiFood",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Image(
                painter = painterResource(R.drawable.salad),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                contentScale = ContentScale.Crop
            )

            Column {
                PrimaryButton("Let's begin", onButtonClick = onBeginClick)
                Spacer(modifier = Modifier.height(14.dp))
                SecondaryButton("Staff Login", onButtonClick = {})
            }

            Text(
                "Don't have a staff account? Sign up",
                modifier.fillMaxWidth(),
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
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
