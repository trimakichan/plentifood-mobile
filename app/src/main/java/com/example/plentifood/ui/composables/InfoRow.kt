package com.example.plentifood.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.plentifood.ui.utils.toTitleFromSnakeCase

@Composable
fun InfoRow(
    icon: ImageVector,
    label: String,
    values: List<String>,
    badge: Boolean = false
) {

    val alignment: Alignment.Vertical =
        if (label == "Address") Alignment.Top else Alignment.CenterVertically

    Row(
        verticalAlignment = alignment,
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Icon(
            imageVector = icon,
            contentDescription = "Icon",
            tint = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            "$label: ",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        if (badge) {
            values.forEach { value ->
                Label(value.toTitleFromSnakeCase())
                Spacer(modifier = Modifier.width(8.dp))
            }
        } else {
            Text(
                values.joinToString(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

}