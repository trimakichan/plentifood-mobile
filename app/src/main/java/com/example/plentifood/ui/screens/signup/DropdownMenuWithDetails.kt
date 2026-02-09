package com.example.plentifood.ui.screens.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun DropdownMenuWithDetails(
    selectedOption: String? = null,
    onOptionSelected: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .clickable { expanded = true }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedOption ?: "Select Organization Type *",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .border(1.dp, MaterialTheme.colorScheme.secondary)
                    .padding(vertical = 16.dp, horizontal = 12.dp)
            )

        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        ) {

            DropdownMenuItem(
                text = { Text("Food Bank") },
                onClick = {
                    onOptionSelected("Food Bank")
                    expanded = false
                }
            )

            HorizontalDivider()

            DropdownMenuItem(
                text = { Text("Church") },
                onClick = {
                    onOptionSelected("Church")
                    expanded = false
                }
            )

            HorizontalDivider()

            DropdownMenuItem(
                text = { Text("Nonprofit") },
                onClick = {
                    onOptionSelected("Nonprofit")
                    expanded = false
                }
            )

            HorizontalDivider()

            DropdownMenuItem(
                text = { Text("Community Center") },
                onClick = {
                    onOptionSelected("Community Center")
                    expanded = false
                }
            )

            HorizontalDivider()

            DropdownMenuItem(
                text = { Text("Others") },
                onClick = {
                    onOptionSelected("Others")
                    expanded = false
                }
            )
        }
    }
}
