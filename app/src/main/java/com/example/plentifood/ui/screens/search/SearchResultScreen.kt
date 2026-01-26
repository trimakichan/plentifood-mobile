package com.example.plentifood.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.plentifood.ui.composables.SingleChoiceSegmentedButton
import com.example.plentifood.ui.screens.Home
import com.example.plentifood.ui.theme.PlentifoodTheme



@Composable
fun SearchResultScreen(
    modifier: Modifier = Modifier,
//    ViewModel() will keep the previous changes even after configuration gets updated.
    viewModel: SearchResultScreenViewMode = viewModel()
) {
    val query = viewModel.query.value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(18.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { newText ->
                    viewModel.onQueryChange(newText) },
                modifier = modifier.weight(1f),
                placeholder = { Text("search")},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null
                    )
                }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
            }) {
                Icon(
                    imageVector = Icons.Filled.Tune,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(28.dp)
                        .rotate(90f)

                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SingleChoiceSegmentedButton()



    }

}


@Preview(showBackground = true)
@Composable
fun SearchResultScreenPreview() {
    PlentifoodTheme {
        SearchResultScreen()
    }
}

