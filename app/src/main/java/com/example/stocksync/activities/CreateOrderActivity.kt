package com.example.stocksync.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stocksync.database.DatabaseHandler
import com.example.stocksync.databinding.ActivityCreateOrderBinding
import com.example.stocksync.models.Customer
import com.example.stocksync.models.Product
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * CreateOrderActivity allows matching a customer to a product and specifying quantity.
 */
class CreateOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateOrderBinding
    private lateinit var db: DatabaseHandler
    private lateinit var customers: List<Customer>
    private lateinit var products: List<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHandler(this)
        
        loadSpinners()

        binding.btnPlaceOrder.setOnClickListener {
            val customerPos = binding.spinnerCustomer.selectedItemPosition
            val productPos = binding.spinnerProduct.selectedItemPosition
            val qtyStr = binding.etOrderQty.text.toString()

            if (qtyStr.isNotEmpty() && customerPos != -1 && productPos != -1) {
                val qty = qtyStr.toIntOrNull() ?: 0
                val selectedCustomer = customers[customerPos]
                val selectedProduct = products[productPos]

                if (qty > selectedProduct.quantity) {
                    Toast.makeText(this, "Not enough stock!", Toast.LENGTH_SHORT).show()
                } else {
                    val date = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
                    val success = db.placeOrder(selectedCustomer.id, selectedProduct.id, qty, date)
                    
                    if (success) {
                        Toast.makeText(this, "Order placed! Stock updated.", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Error placing order", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadSpinners() {
        customers = db.readCustomers()
        products = db.readProducts()

        val custNames = customers.map { it.name }
        val prodNames = products.map { "${it.name} (Stock: ${it.quantity})" }

        val custAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, custNames)
        custAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCustomer.adapter = custAdapter

        val prodAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, prodNames)
        prodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerProduct.adapter = prodAdapter
    }
}
