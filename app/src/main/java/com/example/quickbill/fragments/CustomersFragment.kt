package com.example.quickbill.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quickbill.activities.AddCustomerActivity
import com.example.quickbill.adapters.CustomerAdapter
import com.example.quickbill.database.DatabaseHandler
import com.example.quickbill.databinding.FragmentCustomersBinding

class CustomersFragment : Fragment() {

    private var _binding: FragmentCustomersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCustomersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(requireContext(), AddCustomerActivity::class.java))
        }

        refreshList()
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    private fun refreshList() {
        val db = DatabaseHandler(requireContext())
        val customers = db.readCustomers()

        if (customers.isEmpty()) {
            binding.rvCustomers.visibility = View.GONE
            binding.tvEmpty.visibility = View.VISIBLE
        } else {
            binding.rvCustomers.visibility = View.VISIBLE
            binding.tvEmpty.visibility = View.GONE
            binding.rvCustomers.layoutManager = LinearLayoutManager(requireContext())
            binding.rvCustomers.adapter = CustomerAdapter(customers)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
