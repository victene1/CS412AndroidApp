package edu.csci412.assignment2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(
                onExplicitClick = {
                    startActivity(Intent(this, SecondActivity::class.java))
                },
                onImplicitClick = {
                    startActivity(Intent(SecondActivity.ACTION_SHOW_CHALLENGES).apply {
                        setPackage(packageName)
                    })
                }
            )
        }
    }
}

@Composable
private fun MainScreen(onExplicitClick: () -> Unit, onImplicitClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Airean Ashmore")
        Text("Student ID: 1174900")
        Button(onClick = onExplicitClick) { Text("Start Activity Explicitly") }
        Button(onClick = onImplicitClick) { Text("Start Activity Implicitly") }
    }
}
