package com.example.plentifood.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plentifood.ui.theme.PlentifoodTheme

@Composable
fun SecondaryButton (title: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    OutlinedButton (onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
        contentPadding = PaddingValues(   horizontal = 24.dp, vertical = 14.dp ),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ),
    ) {
        Text(title,
            fontSize = 18.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun SecondaryButton() {
    PlentifoodTheme {
        PrimaryButton("Let's begin", onClick = {})
    }
}
