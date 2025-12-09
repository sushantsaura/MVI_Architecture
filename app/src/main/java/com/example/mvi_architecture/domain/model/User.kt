package com.example.mvi_architecture.domain.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val followerCount: Int
)