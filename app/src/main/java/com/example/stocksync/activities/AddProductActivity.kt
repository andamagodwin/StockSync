package com.example.stocksync.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stocksync.database.DatabaseHandler
import com.example.stocksync.databinding.ActivityAddProductBinding
import com.example.stocksync.models.Product

/**
 * AddProductActivity provides a form to add new items to the inventory.
 */
class AddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSaveProduct.setOnClickListener {
            val name = binding.etProdName.text.toString()
            val priceStr = binding.etProdPrice.text.toString()
            val qtyStr = binding.etProdQty.text.toString()

            if (name.isNotEmpty() && priceStr.isNotEmpty() && qtyStr.isNotEmpty()) {
                val price = priceStr.toDoubleOrNull() ?: 0.0
                val quantity = qtyStr.toIntOrNull() ?: 0
                
                val db = DatabaseHandler(this)
                val result = db.addProduct(Product(name = name, price = price, quantity = quantity))
                
                if (result != -1L) {
                    Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error adding product", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
