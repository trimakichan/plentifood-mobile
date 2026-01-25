package com.example.plentifood.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plentifood.ui.theme.PlentifoodTheme

@Composable
fun PrimaryButton (title: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
        ElevatedButton(onClick = { onClick() },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface
            ),
        ) {
        Text(title,
            fontSize = 18.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonPreview() {
    PlentifoodTheme {
        PrimaryButton("Let's begin", onClick = {})
    }
}
