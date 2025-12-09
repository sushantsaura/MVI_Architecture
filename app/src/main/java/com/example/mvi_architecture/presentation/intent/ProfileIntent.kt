package com.example.mvi_architecture.presentation.intent

sealed class ProfileIntent {
    object LoadProfile : ProfileIntent()
    object FollowUser : ProfileIntent()
    object UnfollowUser : ProfileIntent()
    object Retry : ProfileIntent()
}