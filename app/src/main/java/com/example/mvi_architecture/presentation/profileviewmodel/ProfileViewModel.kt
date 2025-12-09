package com.example.mvi_architecture.presentation.profileviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvi_architecture.presentation.intent.ProfileIntent
import com.example.mvi_architecture.presentation.uistate.ProfileUiState
import com.example.mvi_architecture.domain.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    // Mock data
    private var mockFollowerCount = 1250
    private var mockIsFollowing = false

    fun processIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.LoadProfile -> loadProfile()
            is ProfileIntent.FollowUser -> followUser()
            is ProfileIntent.UnfollowUser -> unfollowUser()
            is ProfileIntent.Retry -> loadProfile()
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            // Simulate network delay
            delay(1500)

            try {
                // Simulate random error (20% chance)
                if (Random.Default.nextInt(100) < 20) {
                    throw Exception("Network error occurred")
                }

                // Mock user data
                val user = User(
                    id = "user_123",
                    name = "John Doe",
                    email = "john.doe@example.com",
                    followerCount = mockFollowerCount
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        user = user,
                        isFollowing = mockIsFollowing
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    private fun followUser() {
        viewModelScope.launch {
            try {
                // Simulate network delay
                delay(500)

                mockIsFollowing = true
                mockFollowerCount++

                _uiState.update {
                    it.copy(
                        isFollowing = true,
                        user = it.user?.copy(followerCount = mockFollowerCount)
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to follow user") }
            }
        }
    }

    private fun unfollowUser() {
        viewModelScope.launch {
            try {
                // Simulate network delay
                delay(500)

                mockIsFollowing = false
                mockFollowerCount--

                _uiState.update {
                    it.copy(
                        isFollowing = false,
                        user = it.user?.copy(followerCount = mockFollowerCount)
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to unfollow user") }
            }
        }
    }
}