package com.example.plentifood.ui.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import java.time.LocalTime
import java.util.Calendar
import androidx.compose.ui.window.Dialog

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePicker(
    day: String,
    updateTime: (String, LocalTime) -> Unit
) {

    var selectedTime by remember {
        mutableStateOf<LocalTime?>(null)
    }

    var showTimePicker by remember { mutableStateOf(false) }

    fun updateTime(hour: Int, minute: Int) {
        selectedTime = LocalTime.of(hour, minute)
    }


    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {

        Row {
            if (selectedTime == null) {
                Box(
                    modifier = Modifier
                        .width(58.dp)
                        .height(32.dp)
                        .background(
                            MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { showTimePicker = true },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Select",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.secondary,
                            textDecoration = TextDecoration.Underline
                        ),
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Text(
                    selectedTime.toString(),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.secondary,
                        textDecoration = TextDecoration.Underline
                    ),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { showTimePicker = true },
                )
            }

        }


        if (showTimePicker) {
            Dial(
                onConfirm = { day, time ->
                    updateTime(day, time)
                    showTimePicker = false
                },
                onDismiss = { showTimePicker = false },
                { hour, minute -> updateTime(hour, minute) },
                day = day
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dial(
    onConfirm: (String, LocalTime) -> Unit,
    onDismiss: () -> Unit,
    updateTime: (Int, Int) -> Unit,
    day: String,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(16.dp),

            ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                TimePicker(
                    state = timePickerState,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = onDismiss) {
                        Text(
                            "Dismiss",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            val pickedTime = LocalTime.of(
                                timePickerState.hour,
                                timePickerState.minute
                            )

                            updateTime(timePickerState.hour, timePickerState.minute)
                            onConfirm(day, pickedTime)
                        }
                    ) {
                        Text(
                            "Confirm",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }

        }

    }

}

