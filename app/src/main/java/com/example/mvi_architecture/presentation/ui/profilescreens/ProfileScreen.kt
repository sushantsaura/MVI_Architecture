package com.example.mvi_architecture.presentation.ui.profilescreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mvi_architecture.presentation.intent.ProfileIntent
import com.example.mvi_architecture.presentation.profileviewmodel.ProfileViewModel
import com.example.mvi_architecture.presentation.ui.profilescreens.components.ErrorScreen
import com.example.mvi_architecture.presentation.ui.profilescreens.components.LoadingIndicator
import com.example.mvi_architecture.presentation.ui.profilescreens.components.ProfileContent

@Suppress("ParamsComparedByRef")
@Composable
fun ProfileScreen(viewModel: ProfileViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                LoadingIndicator()
            }
            uiState.error != null -> {
                ErrorScreen(
                    message = uiState.error!!,
                    onRetry = { viewModel.processIntent(ProfileIntent.Retry) }
                )
            }
            uiState.user != null -> {
                ProfileContent(
                    user = uiState.user!!,
                    isFollowing = uiState.isFollowing,
                    onFollowClick = {
                        if (uiState.isFollowing) {
                            viewModel.processIntent(ProfileIntent.UnfollowUser)
                        } else {
                            viewModel.processIntent(ProfileIntent.FollowUser)
                        }
                    }
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.processIntent(ProfileIntent.LoadProfile)
    }
}
