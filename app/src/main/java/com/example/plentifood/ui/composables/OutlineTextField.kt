package com.example.plentifood.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PermIdentity
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


@Composable
fun OutlineTextField(
    icon: ImageVector,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    errorMsg: String? = "",
    isLoading: Boolean = false
) {


    TextField(
        value,
        onValueChange = {
            onValueChange(it)
        },
        modifier = Modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.tertiary,
//            errorContainerColor = MaterialTheme.colorScheme.error,
        ),
//            textStyle = MaterialTheme.typography.bodyMedium.copy(
//                textAlign = TextAlign.Center
//            ),
        label = {
            Text(
                label,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = MaterialTheme.colorScheme.primary,
                )
        },
        trailingIcon = {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(28.dp),
                )
            }
        },
        singleLine = true,
        supportingText = {
            if(errorMsg?.isNotEmpty() == true) {
                Text(errorMsg)
            }

        },
        isError = errorMsg?.isNotEmpty() == true,

    )

}