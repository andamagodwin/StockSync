package com.example.quickbill.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quickbill.R
import com.example.quickbill.database.DatabaseHandler
import com.example.quickbill.databinding.ActivityLoginBinding
import com.example.quickbill.models.Customer

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set status bar color to match the gradient header
        @Suppress("DEPRECATION")
        window.statusBarColor = ContextCompat.getColor(this, R.color.primary_dark)

        val db = DatabaseHandler(this)
        if (db.readCustomers().isEmpty()) {
            db.addCustomer(Customer(name = "John Doe", phone = "555-0101"))
            db.addCustomer(Customer(name = "Jane Smith", phone = "555-0102"))
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (db.authenticateUser(username, password)) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ProductListActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
