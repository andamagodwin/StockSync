package com.example.stocksync.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.stocksync.R
import com.example.stocksync.databinding.ActivityProductListBinding
import com.example.stocksync.fragments.CustomersFragment
import com.example.stocksync.fragments.HomeFragment
import com.example.stocksync.fragments.OrdersFragment

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    binding.toolbar.title = "StockSync"
                    loadFragment(HomeFragment())
                    true
                }
                R.id.nav_orders -> {
                    binding.toolbar.title = "Orders"
                    loadFragment(OrdersFragment())
                    true
                }
                R.id.nav_customers -> {
                    binding.toolbar.title = "Customers"
                    loadFragment(CustomersFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
