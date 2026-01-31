package com.example.plentifood.ui.composables


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.plentifood.ui.theme.PlentifoodTheme
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun SingleChoiceSegmentedButton(
    options: List<String>,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
//    var selectedIndex by remember { mutableIntStateOf(0) }
//    val options = listOf( "Map", "List")

    SingleChoiceSegmentedButtonRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
//        Ues forEachIndexed because they need to know index to know which button
        options.forEachIndexed { index, label ->

            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = { onSelectedIndexChange(index) },
                selected = index == selectedIndex,
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = MaterialTheme.colorScheme.primary,
                    activeContentColor = Color.White,
                    inactiveContainerColor = MaterialTheme.colorScheme.surface,
                    inactiveContentColor = MaterialTheme.colorScheme.onSurface
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                label = {
                    Text(
                        label,
                    )


                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SingleChoiceSegmentedButtonPreview() {
    PlentifoodTheme {
        val options = listOf("Map", "List")
        var selectedIndex by rememberSaveable { mutableStateOf(0) }

        SingleChoiceSegmentedButton(
            options,
            selectedIndex,
            onSelectedIndexChange = { println("clicked") })
    }
}
