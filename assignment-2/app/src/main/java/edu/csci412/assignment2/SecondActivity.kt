package edu.csci412.assignment2

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

class SecondActivity : ComponentActivity() {
    companion object {
        const val ACTION_SHOW_CHALLENGES = "edu.csci412.assignment2.SHOW_CHALLENGES"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ChallengesScreen(onReturnClick = { finish() }) }
    }
}

@Composable
private fun ChallengesScreen(onReturnClick: () -> Unit) {
    val challenges = listOf(
        "Supporting many screen sizes",
        "Handling Android version differences",
        "Managing battery and memory",
        "Protecting user data and privacy",
        "Maintaining reliable network behavior"
    )
    Column(
        modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Mobile software engineering challenges")
        challenges.forEachIndexed { index, challenge ->
            Text("${index + 1}. $challenge")
        }
        Button(onClick = onReturnClick) { Text("Main Activity") }
    }
}
