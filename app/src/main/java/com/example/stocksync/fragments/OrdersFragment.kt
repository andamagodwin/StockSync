package com.example.stocksync.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stocksync.activities.CreateOrderActivity
import com.example.stocksync.adapters.OrderAdapter
import com.example.stocksync.database.DatabaseHandler
import com.example.stocksync.databinding.FragmentOrdersBinding

class OrdersFragment : Fragment() {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(requireContext(), CreateOrderActivity::class.java))
        }

        refreshList()
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    private fun refreshList() {
        val db = DatabaseHandler(requireContext())
        val orders = db.readOrders()

        if (orders.isEmpty()) {
            binding.rvOrders.visibility = View.GONE
            binding.tvEmpty.visibility = View.VISIBLE
        } else {
            binding.rvOrders.visibility = View.VISIBLE
            binding.tvEmpty.visibility = View.GONE
            binding.rvOrders.layoutManager = LinearLayoutManager(requireContext())
            binding.rvOrders.adapter = OrderAdapter(orders)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
