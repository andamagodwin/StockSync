package com.example.stocksync.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stocksync.database.DatabaseHandler
import com.example.stocksync.databinding.ActivityLoginBinding
import com.example.stocksync.models.Customer

/**
 * LoginActivity provides a basic entry point for the StockSync application.
 * In this coursework task, we demonstrate navigation and data initialization.
 * 
 * We use 'ViewBinding' to safely and efficiently access our UI elements.
 */
class LoginActivity : AppCompatActivity() {

    // ViewBinding reference to the activity's layout
    private lateinit var binding: ActivityLoginBinding

    /**
     * Entry point of the activity lifecycle.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize ViewBinding: this generates the layout's root view
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // For this student project, we pre-populate customers if the database is empty.
        // This ensures the marker has data to work with when creating orders.
        val db = DatabaseHandler(this)
        if (db.readCustomers().isEmpty()) {
            db.addCustomer(Customer(name = "John Doe", phone = "555-0101"))
            db.addCustomer(Customer(name = "Jane Smith", phone = "555-0102"))
        }

        // Listener for the login button
        binding.btnLogin.setOnClickListener {
            // Retrieve text input from the UI fields
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            // For this specific task, any non-empty credentials allow entry.
            // This simplifies the login process for demonstration purposes.
            if (username.isNotEmpty() && password.isNotEmpty()) {
                // An 'Intent' is the standard way to move between activities in Android.
                val intent = Intent(this, ProductListActivity::class.java)
                startActivity(intent)
                
                // We call finish() to remove this login screen from the back stack.
                // This prevents the user from going back to the login screen after entering.
                finish() 
            } else {
                // A 'Toast' is a simple popup message to alert the user.
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
