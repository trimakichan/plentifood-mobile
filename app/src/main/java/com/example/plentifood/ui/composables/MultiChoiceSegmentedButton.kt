package com.example.plentifood.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DinnerDining
import androidx.compose.material.icons.outlined.FoodBank
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun MultiChoiceSegmentedButton(
    options: List<String>,
    selectedOptions: List<Boolean>,
    onCheckedChange: (index: Int, value: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    MultiChoiceSegmentedButtonRow (
        modifier = Modifier
            .fillMaxWidth()
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                checked = selectedOptions[index],
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = MaterialTheme.colorScheme.primary,
                    activeContentColor = Color.White,
                    inactiveContainerColor = MaterialTheme.colorScheme.surface,
                    inactiveContentColor = MaterialTheme.colorScheme.onSurface
                ),
                border = BorderStroke(1.dp,
                    if (selectedOptions[index])
                        MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary

                ),
                onCheckedChange = { onCheckedChange(index, it)
//                    selectedOptions[index] = !selectedOptions[index]
                },
                icon = { SegmentedButtonDefaults.Icon(selectedOptions[index]) },
                label = {

                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        when (label) {
                            "Food Bank" ->
                                Icon(
                                    imageVector =
                                        Icons.Outlined.FoodBank,
                                    contentDescription = "Food Bank"
                                )
                            "Meal" -> Icon(
                                imageVector =
                                    Icons.Outlined.DinnerDining,
                                contentDescription = "Meal"
                            )
                        }

                        Spacer(Modifier.width(4.dp))

                        Text(label)
                    }

                }
            )
        }
    }
}

