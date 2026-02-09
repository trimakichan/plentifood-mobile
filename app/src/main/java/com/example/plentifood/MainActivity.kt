package com.example.plentifood

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.plentifood.navigation.NavigationRoot
import com.example.plentifood.ui.theme.PlentifoodTheme
import com.google.android.libraries.places.api.Places

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        val apiKey = appInfo.metaData.getString("com.google.android.geo.API_KEY")

        if(!Places.isInitialized() && apiKey != null) {
                Places.initializeWithNewPlacesApiEnabled(this,apiKey)
            }

        setContent {
            PlentifoodTheme {
                Scaffold { innerPadding ->

                    NavigationRoot(modifier = Modifier
                        .padding(innerPadding)
                    )
                }

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