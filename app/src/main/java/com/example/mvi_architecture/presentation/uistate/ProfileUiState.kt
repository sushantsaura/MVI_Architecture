package com.example.mvi_architecture.presentation.uistate

import com.example.mvi_architecture.domain.model.User

data class ProfileUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null,
    val isFollowing: Boolean = false
)