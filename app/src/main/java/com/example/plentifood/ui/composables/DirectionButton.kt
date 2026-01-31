package com.example.plentifood.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DirectionButton(onClick: () -> Unit, modifier: Modifier) {

    SmallFloatingActionButton(
        onClick = {
            onClick()
        },
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.surface
    ) {
        Icon(Icons.Filled.Directions, "Get Direction")
    }
}