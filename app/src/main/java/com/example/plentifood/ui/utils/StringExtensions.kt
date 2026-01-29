package com.example.plentifood.ui.utils

fun String.toTitleFromSnakeCase(): String =
    split("_")
        .joinToString(" ") { word ->
            word.lowercase().replaceFirstChar {it.uppercase()}
        }