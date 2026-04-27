package com.example.quickbill.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quickbill.database.DatabaseHandler
import com.example.quickbill.databinding.ActivityAddCustomerBinding
import com.example.quickbill.models.Customer

class AddCustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCustomerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar back navigation
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.btnSaveCustomer.setOnClickListener {
            val name = binding.etCustName.text.toString()
            val phone = binding.etCustPhone.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty()) {
                val db = DatabaseHandler(this)
                val result = db.addCustomer(Customer(name = name, phone = phone))
                if (result != -1L) {
                    Toast.makeText(this, "Customer added successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error adding customer", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
