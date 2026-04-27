package com.example.quickbill.models

/**
 * Data class representing a Product in the inventory.
 * We use a data class here to cleanly represent our business data.
 * 
 * @property id Primary key for the database record.
 * @property name Descriptive name of the product.
 * @property price Retail price of the product (stored as Double).
 * @property quantity Current stock level in the inventory.
 * @property imageUri Optional URI pointing to an image file on the device.
 */
data class Product(
    val id: Int = 0,
    val name: String,
    val price: Double,
    var quantity: Int,
    val imageUri: String? = null
)
