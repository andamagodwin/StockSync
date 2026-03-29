package com.example.stocksync.models

/**
 * Data class representing an Order placed by a customer for a specific product.
 * This class links the Customer and Product via their respective IDs (Foreign Keys).
 * 
 * @property id The unique order reference.
 * @property customerId Foreign Key referencing the Customer who made the purchase.
 * @property productId Foreign Key referencing the Product purchased.
 * @property date The timestamp when the order was placed (String format YYYY-MM-DD).
 * @property quantity The amount of the product purchased in this order.
 */
data class Order(
    val id: Int = 0,
    val customerId: Int,
    val productId: Int,
    val date: String,
    val quantity: Int
)
