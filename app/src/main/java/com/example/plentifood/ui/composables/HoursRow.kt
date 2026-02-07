package com.example.plentifood.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.plentifood.data.models.response.Hours


@Composable
fun HoursRow(
    icon: ImageVector,
    label: String,
    values: Hours,
) {
    val days = listOf(
        "Monday" to values.monday,
        "Tuesday" to values.tuesday,
        "Wednesday" to values.wednesday,
        "Thursday" to values.thursday,
        "Friday" to values.friday,
        "Saturday" to values.saturday,
        "Sunday" to values.sunday
    )
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
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
        }

        days.forEach { (day, slots) ->
            val hoursText = if (slots.isEmpty()) {
                "Closed"
            } else {
                slots.joinToString(", ") { slot ->
                    "${slot.open} - ${slot.close}"
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {

                Text(
                    "$day: ",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,

                )

                Text(
                    hoursText, style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary

                )
            }
        }


    }
}