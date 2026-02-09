package com.example.plentifood.ui.composables

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ButtonWithIcon(
    modifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: () -> Unit,
    description: String = "Icon Button",
    tint: Color = MaterialTheme.colorScheme.primary,
    ) {
    IconButton(
        onClick = { onClick() },
        modifier = buttonModifier

    ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = tint,
            modifier = modifier
        )
    }
}