package com.example.stocksync.models

/**
 * Data class representing a Product in the inventory.
 */
data class Product(
    val id: Int = 0,
    val name: String,
    val price: Double,
    var quantity: Int,
    val imageUri: String? = null
)
