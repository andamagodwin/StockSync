package com.example.stocksync.models

data class OrderDisplay(
    val id: Int,
    val customerName: String,
    val productName: String,
    val quantity: Int,
    val date: String,
    val totalPrice: Double
)
