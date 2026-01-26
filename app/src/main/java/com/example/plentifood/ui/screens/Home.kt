package com.example.plentifood.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.plentifood.R
import com.example.plentifood.ui.composables.PrimaryButton
import com.example.plentifood.ui.composables.SecondaryButton
import com.example.plentifood.ui.composables.SingleChoiceSegmentedButton
import com.example.plentifood.ui.theme.PlentifoodTheme



@Composable
fun Home (modifier: Modifier = Modifier) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {

        Text("Logo")

        Spacer(modifier = Modifier.padding(24.dp))


        Text("Welcome to the PlentiFood",
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
                .height(350.dp)
        )

        Column (modifier = Modifier
        ) {
            PrimaryButton("Lets begin", onClick = {})
            Spacer(modifier = Modifier.padding(8.dp))
            SecondaryButton("Staff Login", onClick = {} )
        }

        Text("Don't have a staff account as a staff? Sign up")





    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    PlentifoodTheme {
        Home()
    }
}
