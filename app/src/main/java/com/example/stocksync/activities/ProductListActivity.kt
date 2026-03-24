package com.example.stocksync.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.stocksync.adapters.ProductAdapter
import com.example.stocksync.database.DatabaseHandler
import com.example.stocksync.databinding.ActivityProductListBinding

/**
 * ProductListActivity shows all stock items in a RecyclerView.
 */
class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding
    private lateinit var db: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHandler(this)

        binding.fabAddProduct.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }

        binding.btnGoToOrder.setOnClickListener {
            val intent = Intent(this, CreateOrderActivity::class.java)
            startActivity(intent)
        }

        refreshList()
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    private fun refreshList() {
        val products = db.readProducts()
        val adapter = ProductAdapter(products)
        binding.rvProducts.layoutManager = GridLayoutManager(this, 2)
        binding.rvProducts.adapter = adapter
    }
}
