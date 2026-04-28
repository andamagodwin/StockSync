package com.example.quickbill.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quickbill.database.DatabaseHandler
import com.example.quickbill.databinding.ActivityCreateOrderBinding
import com.example.quickbill.models.Customer
import com.example.quickbill.models.Product
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * CreateOrderActivity allows matching a customer to a product and specifying quantity.
 * This activity handles the core "Order Placement" logic which involves inventory 
 * updates within a database transaction.
 */
class CreateOrderActivity : AppCompatActivity() {

    // Member variables to hold UI binding, DB reference, and data lists
    private lateinit var binding: ActivityCreateOrderBinding
    private lateinit var db: DatabaseHandler
    private lateinit var customers: List<Customer>
    private lateinit var products: List<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize ViewBinding to access UI elements without findViewById()
        binding = ActivityCreateOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar back navigation
        binding.toolbar.setNavigationOnClickListener { finish() }

        // Initialize our persistent database storage handler
        db = DatabaseHandler(this)
        
        // Populate the dropdown selectors (Spinners) with current DB data
        loadSpinners()

        // Listener for the "Place Order" button
        binding.btnPlaceOrder.setOnClickListener {
            // Get current user selections from the UI
            val customerPos = binding.spinnerCustomer.selectedItemPosition
            val productPos = binding.spinnerProduct.selectedItemPosition
            val qtyStr = binding.etOrderQty.text.toString()

            // Basic validation to ensure the form is complete
            if (qtyStr.isNotEmpty() && customerPos != -1 && productPos != -1) {
                // Safely convert string input to an integer
                val qty = qtyStr.toIntOrNull() ?: 0
                val selectedCustomer = customers[customerPos]
                val selectedProduct = products[productPos]

                // Logic: prevent an order if stock levels are too low
                if (qty > selectedProduct.quantity) {
                    Toast.makeText(this, "Not enough stock!", Toast.LENGTH_SHORT).show()
                } else if (qty <= 0) {
                    Toast.makeText(this, "Please enter a valid quantity", Toast.LENGTH_SHORT).show()
                } else {
                    // Generate a timestamp for the order in a standard readable format
                    val date = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
                    
                    // Call the ACID-compliant placeOrder function in our DatabaseHandler.
                    // This function handles the atomic operation of saving the order AND reducing stock.
                    val success = db.placeOrder(selectedCustomer.id, selectedProduct.id, qty, date)
                    
                    if (success) {
                        Toast.makeText(this, "Order placed! Stock updated.", Toast.LENGTH_LONG).show()
                        
                        // Close activity and return to previous screen on success
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

    /**
     * Helper function to pull the latest lists of customers and products 
     * from the database and bind them to the Spinner (Dropdown) views.
     */
    private fun loadSpinners() {
        // Fetch fresh data from SQLite
        customers = db.readCustomers()
        products = db.readProducts()

        // Transform our data lists into simple String lists for the dropdown display
        // The '.map' function is a concise way in Kotlin to transform a collection.
        val custNames = customers.map { it.name }
        val prodNames = products.map { "${it.name} (Stock: ${it.quantity})" }

        // Using custom layouts to fix the white-on-white text visibility issue
        // We use com.example.quickbill.R explicitly to avoid unresolved reference in some IDE states
        val custAdapter = ArrayAdapter(this, com.example.quickbill.R.layout.item_spinner, custNames)
        custAdapter.setDropDownViewResource(com.example.quickbill.R.layout.item_spinner_dropdown)
        binding.spinnerCustomer.adapter = custAdapter

        val prodAdapter = ArrayAdapter(this, com.example.quickbill.R.layout.item_spinner, prodNames)
        prodAdapter.setDropDownViewResource(com.example.quickbill.R.layout.item_spinner_dropdown)
        binding.spinnerProduct.adapter = prodAdapter
    }
}
