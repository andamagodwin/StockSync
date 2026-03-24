package com.example.stocksync.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stocksync.database.DatabaseHandler
import com.example.stocksync.databinding.ActivityLoginBinding
import com.example.stocksync.models.Customer

/**
 * LoginActivity provides a basic entry point. 
 * For this assignment, it also initializes some dummy customer data if the table is empty.
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Pre-populate customers if none exist for demonstration
        val db = DatabaseHandler(this)
        if (db.readCustomers().isEmpty()) {
            db.addCustomer(Customer(name = "John Doe", phone = "555-0101"))
            db.addCustomer(Customer(name = "Jane Smith", phone = "555-0102"))
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            // Basic logic: any non-empty credentials allow entry for this coursework task
            if (username.isNotEmpty() && password.isNotEmpty()) {
                val intent = Intent(this, ProductListActivity::class.java)
                startActivity(intent)
                finish() // Close login screen
            } else {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
