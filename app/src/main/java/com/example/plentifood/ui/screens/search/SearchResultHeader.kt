package com.example.plentifood.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.plentifood.ui.composables.SingleChoiceSegmentedButton

@Composable
fun SearchResultHeader(
modifier: Modifier = Modifier,
    numOfFilters: String,
    isLoading: Boolean,
    totalResults: Int,
    options: List<String>,
    selectedIndex: Int,
onSelectedIndexChange: (Int) -> Unit
) {

    Column(
        modifier = modifier
            .padding(18.dp)
    ) {

        Spacer(modifier = Modifier.height(18.dp))

        SingleChoiceSegmentedButton(
            options,
            selectedIndex,
            onSelectedIndexChange = { onSelectedIndexChange(it) }
        )

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {


                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(numOfFilters)
                        }

                        append(" Filters")
                    },
                    style = MaterialTheme.typography.bodyMedium
                )

//
//                Icon(
//                    imageVector = Icons.Filled.Cancel,
//                    contentDescription = "Cancel Icon",
//                    modifier = Modifier
//                        .padding(6.dp)
//                        .size(18.dp),
//                    tint = MaterialTheme.colorScheme.primary,
//                )

            }

            if (isLoading) {

                Text(
                    "Loading...",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(totalResults.toString())
                        }

                        append(" Results")
                    },
                    style = MaterialTheme.typography.bodyMedium
                )

            }

        }
    }

}