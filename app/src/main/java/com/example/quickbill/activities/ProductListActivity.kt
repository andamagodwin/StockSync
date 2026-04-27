package com.example.quickbill.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.quickbill.R
import com.example.quickbill.databinding.ActivityProductListBinding
import com.example.quickbill.fragments.CustomersFragment
import com.example.quickbill.fragments.HomeFragment
import com.example.quickbill.fragments.OrdersFragment

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set status bar color to match the gradient toolbar
        @Suppress("DEPRECATION")
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar)

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    binding.toolbar.title = "QuickBill"
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
