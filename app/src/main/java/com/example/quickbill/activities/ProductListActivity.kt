package com.example.quickbill.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
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

        // Set status bar and navigation bar appearance
        val window = window
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.bottom_nav_bg)
        
        // Ensure status bar icons are white (not dark) on our dark background
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = false
        }

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        binding.toolbar.setOnMenuItemClickListener { item ->
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
