package com.example.plentifood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.plentifood.ui.composables.PrimaryButton
import com.example.plentifood.ui.composables.SecondaryButton
import com.example.plentifood.ui.composables.SingleChoiceSegmentedButton
import com.example.plentifood.ui.screens.Home
import com.example.plentifood.ui.screens.search.SearchResultScreen
import com.example.plentifood.ui.theme.PlentifoodTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlentifoodTheme {
//                Home()
                SearchResultScreen()
            }
        }
    }
}




@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
    ElevatedButton(onClick = {}) {
        Text("Click me")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PlentifoodTheme {
        Greeting("Android")
    }
}