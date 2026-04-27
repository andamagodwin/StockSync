package com.example.quickbill.models

/**
 * Data class representing a Customer in the StockSync system.
 * In Kotlin, 'data class' automatically generates useful methods like equals(), 
 * hashCode(), and toString() for we only need to provide properties.
 * 
 * @property id The unique identifier for the customer, defaults to 0 for new entries.
 * @property name The full name of the customer.
 * @property phone The contact phone number of the customer.
 */
data class Customer(
    val id: Int = 0,
    val name: String,
    val phone: String
)
