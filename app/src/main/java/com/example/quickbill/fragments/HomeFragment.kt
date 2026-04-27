package com.example.quickbill.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quickbill.activities.AddProductActivity
import com.example.quickbill.activities.EditProductActivity
import com.example.quickbill.adapters.ProductAdapter
import com.example.quickbill.database.DatabaseHandler
import com.example.quickbill.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(requireContext(), AddProductActivity::class.java))
        }

        refreshList()
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    private fun refreshList() {
        val db = DatabaseHandler(requireContext())
        val products = db.readProducts()

        if (products.isEmpty()) {
            binding.rvProducts.visibility = View.GONE
            binding.tvEmpty.visibility = View.VISIBLE
        } else {
            binding.rvProducts.visibility = View.VISIBLE
            binding.tvEmpty.visibility = View.GONE
            binding.rvProducts.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.rvProducts.adapter = ProductAdapter(products) { product ->
                val intent = Intent(requireContext(), EditProductActivity::class.java)
                intent.putExtra("product_id", product.id)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
