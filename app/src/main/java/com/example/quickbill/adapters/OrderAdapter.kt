package com.example.quickbill.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quickbill.databinding.OrderItemBinding
import com.example.quickbill.models.OrderDisplay

class OrderAdapter(private val orders: List<OrderDisplay>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(val binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.binding.apply {
            tvOrderId.text = "Order #${order.id}"
            tvOrderDate.text = order.date
            tvOrderCustomer.text = "Customer: ${order.customerName}"
            tvOrderProduct.text = "Product: ${order.productName}"
            tvOrderQty.text = "Qty: ${order.quantity}"
            tvOrderTotal.text = "Total: UGX ${order.totalPrice}"
        }
    }

    override fun getItemCount(): Int = orders.size
}
