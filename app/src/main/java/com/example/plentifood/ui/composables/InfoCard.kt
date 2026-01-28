package com.example.plentifood.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.util.TableInfo
import com.example.plentifood.ui.theme.PlentifoodTheme

@Composable
fun InfoCard(modifier: Modifier) {
    Row (
        modifier = modifier
            .fillMaxWidth(0.95f)
            .height(114.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(12.dp),

        verticalAlignment = Alignment.CenterVertically


    ) {

        Box(
            modifier = Modifier
                .size(64.dp) // width & height
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.onSurface),
        ) {
            Text("Image")
        }

        Spacer(Modifier.width(12.dp))
        Column (
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                "SODO Community Market",
                style = MaterialTheme.typography.bodyMedium
                )
            Text(
                "SODO Community Market",
                style = MaterialTheme.typography.bodySmall
                )
            Text(
                "SODO Community Market",
                style = MaterialTheme.typography.labelSmall
            )

            OutlinedButton(
                onClick = {},
                modifier = Modifier.align(Alignment.End),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(
                    horizontal = 12.dp,
                    vertical = 4.dp)

                ){
                Text(
                    "Details",
                    textAlign = TextAlign.Center
                    )
            }
        }




    }
}


//
//@Preview(showBackground = true)
//@Composable
//fun InfoCardPreview() {
//    PlentifoodTheme {
//        InfoCard()
//}}


