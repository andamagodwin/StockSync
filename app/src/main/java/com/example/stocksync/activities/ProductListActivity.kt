package com.example.stocksync.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.stocksync.adapters.ProductAdapter
import com.example.stocksync.database.DatabaseHandler
import com.example.stocksync.databinding.ActivityProductListBinding

/**
 * ProductListActivity shows all stock items in a RecyclerView for easy management.
 * 
 * In this student project, this screen acts as the main inventory dashboard.
 * We use a GridLayout to display our products efficiently.
 */
class ProductListActivity : AppCompatActivity() {

    // Member variables for ViewBinding and DatabaseHandler
    private lateinit var binding: ActivityProductListBinding
    private lateinit var db: DatabaseHandler

    /**
     * Standard activity setup.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize ViewBinding for type-safe layout access
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize our database instance
        db = DatabaseHandler(this)

        // Floating Action Button (FAB) to navigate to the 'Add Product' screen
        binding.fabAddProduct.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }

        // Secondary action button to transition to the 'Create Order' flow
        binding.btnGoToOrder.setOnClickListener {
            val intent = Intent(this, CreateOrderActivity::class.java)
            startActivity(intent)
        }

        // Initial loading of our database data into the RecyclerView
        refreshList()
    }

    /**
     * onResume() is a critical part of the Android Activity Lifecycle.
     * We override it to refresh the product list whenever this screen 
     * comes back into focus (e.g., after adding a new product).
     */
    override fun onResume() {
        super.onResume()
        refreshList()
    }

    /**
     * private helper function to read from the DB and update our list.
     */
    private fun refreshList() {
        // Query all products from the SQLite database
        val products = db.readProducts()
        
        // Initialize the custom Adapter that bridges our product data with the RecyclerView
        val adapter = ProductAdapter(products)
        
        // Use a GridLayoutManager to display products in two columns (per user request)
        binding.rvProducts.layoutManager = GridLayoutManager(this, 2)
        
        // Set the adapter to our RecyclerView to populate the display
        binding.rvProducts.adapter = adapter
    }
}
