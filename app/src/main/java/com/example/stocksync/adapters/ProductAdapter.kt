package com.example.stocksync.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.stocksync.databinding.ProductItemBinding
import com.example.stocksync.models.Product

/**
 * ProductAdapter is a custom RecyclerView Adapter designed to display a list of 
 * StockSync products in a high-performance grid or list layout.
 * 
 * Adapters are the bridge between your data source (SQLite) and your UI components.
 */
class ProductAdapter(private val products: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    /**
     * The 'ViewHolder' class is a design pattern used to minimize the number of calls 
     * to costly UI operations like finding views by ID.
     * We use ViewBinding here for type-safe and more readable code.
     */
    class ProductViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * onCreateViewHolder() is called by the RecyclerView when it needs a new ViewHolder.
     * We use LayoutInflater to turn our XML layout (product_item.xml) into a View object.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        // Inflate the layout using the binding class's static inflate method
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    /**
     * onBindViewHolder() is called to bind the data at a specific position to the ViewHolder.
     * This is where you set the text, images, and click listeners for individual list items.
     */
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        
        // Use 'apply' to conveniently access all members of the binding object
        holder.binding.apply {
            tvProductName.text = product.name
            tvProductPrice.text = "Price: UGX ${product.price}"
            tvProductQuantity.text = "Stock: ${product.quantity}"
            
            // ASYNCHRONOUS IMAGE LOADING:
            // We use the 'Coil' library to load the image URI in the background.
            // This prevents the UI thread from freezing while loading a picture.
            ivProductImage.load(product.imageUri) {
                crossfade(true) // Smooth fade animation for better UX
                placeholder(android.R.drawable.ic_menu_gallery) // Default image while loading
                error(android.R.drawable.ic_menu_report_image) // Fallback if image fails
            }
        }
    }

    /**
     * Simple utility function telling the RecyclerView how many items are in our data list.
     */
    override fun getItemCount(): Int = products.size
}
