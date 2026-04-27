package com.example.quickbill.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.quickbill.database.DatabaseHandler
import com.example.quickbill.databinding.ActivityEditProductBinding
import com.example.quickbill.models.Product

class EditProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProductBinding
    private var selectedImageUri: Uri? = null
    private var productId: Int = -1

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            selectedImageUri = uri
            binding.ivProductPreview.load(uri)
            val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION
            contentResolver.takePersistableUriPermission(uri, takeFlags)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar back navigation
        binding.toolbar.setNavigationOnClickListener { finish() }

        productId = intent.getIntExtra("product_id", -1)
        if (productId == -1) {
            Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val db = DatabaseHandler(this)
        val product = db.getProduct(productId)
        if (product == null) {
            Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.etProdName.setText(product.name)
        binding.etProdPrice.setText(product.price.toString())
        binding.etProdQty.setText(product.quantity.toString())

        if (!product.imageUri.isNullOrEmpty()) {
            selectedImageUri = Uri.parse(product.imageUri)
            binding.ivProductPreview.load(product.imageUri) {
                crossfade(true)
                placeholder(android.R.drawable.ic_menu_gallery)
                error(android.R.drawable.ic_menu_report_image)
            }
        }

        binding.btnSelectImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.btnUpdateProduct.setOnClickListener {
            val name = binding.etProdName.text.toString()
            val priceStr = binding.etProdPrice.text.toString()
            val qtyStr = binding.etProdQty.text.toString()

            if (name.isNotEmpty() && priceStr.isNotEmpty() && qtyStr.isNotEmpty()) {
                val price = priceStr.toDoubleOrNull() ?: 0.0
                val quantity = qtyStr.toIntOrNull() ?: 0
                val updatedProduct = Product(
                    id = productId,
                    name = name,
                    price = price,
                    quantity = quantity,
                    imageUri = selectedImageUri?.toString()
                )
                val result = db.updateProduct(updatedProduct)
                if (result > 0) {
                    Toast.makeText(this, "Product updated successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error updating product", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDeleteProduct.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Delete Product")
                .setMessage("Are you sure you want to delete \"${product.name}\"?")
                .setPositiveButton("Delete") { _, _ ->
                    val result = db.deleteProduct(productId)
                    if (result > 0) {
                        Toast.makeText(this, "Product deleted", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Error deleting product", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}
