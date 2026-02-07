package com.example.plentifood.ui.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime


@RequiresApi(Build.VERSION_CODES.O)
fun LocalTime.toApi(): String =
    "%02d:%02d".format(hour, minute)