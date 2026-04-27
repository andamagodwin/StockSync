package com.example.quickbill.models

data class User(
    val id: Int = 0,
    val username: String,
    val passwordHash: String
)
