package com.example.mvi_architecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.mvi_architecture.presentation.ui.profilescreens.ProfileScreen
import com.example.mvi_architecture.presentation.ui.theme.MVI_ArchitectureTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MVI_ArchitectureTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        ProfileScreen()
                    }
                }
            }
        }
    }
}

/**
 * MVI PATTERN OVERVIEW:
 * - Model: Represents the state of your UI (immutable data class)
 * - View: Renders the UI based on the Model/State
 * - Intent: User actions/events that trigger state changes
 *
 * Flow: Intent → ViewModel → Model (State) → View
 */
