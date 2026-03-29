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
 * AddProductActivity provides a comprehensive form to add new items to the inventory.
 * A key feature of this activity is the ability to select and persist product images 
 * using the modern Android Photo Picker API.
 */
class AddProductActivity : AppCompatActivity() {

    // ViewBinding and image storage
    private lateinit var binding: ActivityAddProductBinding
    private var selectedImageUri: Uri? = null

    /**
     * registerForActivityResult is the modern replacement for onActivityResult().
     * We use a contract (PickVisualMedia) to safely launch the system-provided photo picker.
     */
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback functionality once the user picks an image or cancels.
        if (uri != null) {
            selectedImageUri = uri
            
            // Coil is an image loading library that handles threading and memory efficiently.
            binding.ivProductPreview.load(uri)
            
            // SECURITY/PERSISTENCE: We must request 'persistable' permission from Android's ContentResolver.
            // Without this, the app will lose access to the image URI when the device restarts.
            val contentResolver = applicationContext.contentResolver
            val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION
            contentResolver.takePersistableUriPermission(uri, takeFlags)
        }
    }

    /**
     * Standard activity initialization.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Setup the modern ViewBinding for safe UI interaction
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Event listener for the "Select Image" button
        binding.btnSelectImage.setOnClickListener {
            // Launch the photo picker to only show images
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        // Action when the user clicks 'Save'
        binding.btnSaveProduct.setOnClickListener {
            // Retrieve data from input fields and perform basic validation
            val name = binding.etProdName.text.toString()
            val priceStr = binding.etProdPrice.text.toString()
            val qtyStr = binding.etProdQty.text.toString()

            // Simplistic form validation: require all fields as per coursework requirement
            if (name.isNotEmpty() && priceStr.isNotEmpty() && qtyStr.isNotEmpty()) {
                val price = priceStr.toDoubleOrNull() ?: 0.0
                val quantity = qtyStr.toIntOrNull() ?: 0
                
                // Initialize the database and prepare our data model
                val db = DatabaseHandler(this)
                val newProduct = Product(
                    name = name, 
                    price = price, 
                    quantity = quantity, 
                    imageUri = selectedImageUri?.toString()
                )
                
                // Add the object into the database. addProduct returns the row ID (-1 if error)
                val result = db.addProduct(newProduct)
                
                if (result != -1L) {
                    Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show()
                    
                    // Close this activity and return to the list screen
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
