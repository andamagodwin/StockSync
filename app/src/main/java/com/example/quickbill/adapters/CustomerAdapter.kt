package com.example.quickbill.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quickbill.databinding.CustomerItemBinding
import com.example.quickbill.models.Customer

class CustomerAdapter(private val customers: List<Customer>) : RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {

    class CustomerViewHolder(val binding: CustomerItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val binding = CustomerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val customer = customers[position]
        holder.binding.tvCustomerName.text = customer.name
        holder.binding.tvCustomerPhone.text = customer.phone
    }

    override fun getItemCount(): Int = customers.size
}
