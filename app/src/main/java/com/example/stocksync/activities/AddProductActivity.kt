package com.example.stocksync.activities
    
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.stocksync.database.DatabaseHandler
import com.example.stocksync.databinding.ActivityAddProductBinding
import com.example.stocksync.models.Product

/**
 * AddProductActivity provides a form to add new items to the inventory.
 */
class AddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding
    private var selectedImageUri: Uri? = null

    // Register Photo Picker activity launcher
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            selectedImageUri = uri
            binding.ivProductPreview.load(uri)
            
            // Persist the permission to access this URI even after reboot
            val contentResolver = applicationContext.contentResolver
            val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION
            contentResolver.takePersistableUriPermission(uri, takeFlags)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.btnSelectImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.btnSaveProduct.setOnClickListener {
            val name = binding.etProdName.text.toString()
            val priceStr = binding.etProdPrice.text.toString()
            val qtyStr = binding.etProdQty.text.toString()

            if (name.isNotEmpty() && priceStr.isNotEmpty() && qtyStr.isNotEmpty()) {
                val price = priceStr.toDoubleOrNull() ?: 0.0
                val quantity = qtyStr.toIntOrNull() ?: 0
                
                val db = DatabaseHandler(this)
                val newProduct = Product(
                    name = name, 
                    price = price, 
                    quantity = quantity, 
                    imageUri = selectedImageUri?.toString()
                )
                val result = db.addProduct(newProduct)
                
                if (result != -1L) {
                    Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error adding product", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
