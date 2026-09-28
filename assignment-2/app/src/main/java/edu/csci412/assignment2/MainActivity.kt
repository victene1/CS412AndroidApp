package edu.csci412.assignment2

import android.Manifest
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    private var gradeText by mutableStateOf("Grade: --")
    private var myService: MyService? = null
    private var isBound = false
    private var receiverRegistered = false

    private val receiver: BroadcastReceiver = MyBroadcastReceiver()

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            myService = (binder as MyService.LocalBinder).getService()
            isBound = true
            gradeText = "Grade: ${myService?.getMyGrade()}"
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            myService = null
            isBound = false
            gradeText = "Grade: --"
        }
    }

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                startAssignmentService()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(
                gradeText = gradeText,
                onExplicitClick = {
                    startActivity(Intent(this, SecondActivity::class.java))
                },
                onImplicitClick = {
                    startActivity(Intent(SecondActivity.ACTION_SHOW_CHALLENGES).apply {
                        setPackage(packageName)
                    })
                },
                onStartService = ::requestPermissionAndStartService,
                onBindService = ::bindAssignmentService,
                onSendBroadcast = {
                    sendBroadcast(Intent(ACTION_CUSTOM_BROADCAST).setPackage(packageName))
                }
            )
        }
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(ACTION_CUSTOM_BROADCAST)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            @Suppress("DEPRECATION")
            registerReceiver(receiver, filter)
        }
        receiverRegistered = true
    }

    override fun onStop() {
        if (receiverRegistered) {
            unregisterReceiver(receiver)
            receiverRegistered = false
        }
        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
            myService = null
        }
        super.onStop()
    }

    private fun requestPermissionAndStartService() {
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            startAssignmentService()
        }
    }

    private fun startAssignmentService() {
        val intent = Intent(this, MyService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    private fun bindAssignmentService() {
        if (isBound) {
            gradeText = "Grade: ${myService?.getMyGrade()}"
        } else {
            bindService(Intent(this, MyService::class.java), serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    companion object {
        const val ACTION_CUSTOM_BROADCAST = "edu.csci412.assignment2.MY_ACTION"
    }
}

@Composable
private fun MainScreen(
    gradeText: String,
    onExplicitClick: () -> Unit,
    onImplicitClick: () -> Unit,
    onStartService: () -> Unit,
    onBindService: () -> Unit,
    onSendBroadcast: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Airean Ashmore")
        Text("Student ID: 1174900")
        Button(onClick = onExplicitClick) { Text("Start Activity Explicitly") }
        Button(onClick = onImplicitClick) { Text("Start Activity Implicitly") }
        Button(onClick = onStartService) { Text("Start Service") }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = onBindService) { Text("Bind Service") }
            Text(gradeText)
        }
        Button(onClick = onSendBroadcast) { Text("Send Broadcast") }
    }
}
