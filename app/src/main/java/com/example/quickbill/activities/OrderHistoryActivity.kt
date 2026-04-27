package com.example.quickbill.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quickbill.adapters.OrderAdapter
import com.example.quickbill.database.DatabaseHandler
import com.example.quickbill.databinding.ActivityOrderHistoryBinding

class OrderHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderHistoryBinding
    private lateinit var db: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar back navigation
        binding.toolbar.setNavigationOnClickListener { finish() }

        db = DatabaseHandler(this)
        refreshList()
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    private fun refreshList() {
        val orders = db.readOrders()
        if (orders.isEmpty()) {
            binding.rvOrders.visibility = View.GONE
            binding.tvEmptyOrders.visibility = View.VISIBLE
        } else {
            binding.rvOrders.visibility = View.VISIBLE
            binding.tvEmptyOrders.visibility = View.GONE
            binding.rvOrders.layoutManager = LinearLayoutManager(this)
            binding.rvOrders.adapter = OrderAdapter(orders)
        }
    }
}
