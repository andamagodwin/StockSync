package com.example.stocksync.models

/**
 * Data class representing an Order placed by a customer for a product.
 */
data class Order(
    val id: Int = 0,
    val customerId: Int,
    val productId: Int,
    val date: String,
    val quantity: Int
)
