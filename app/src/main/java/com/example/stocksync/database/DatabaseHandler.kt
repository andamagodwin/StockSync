package com.example.stocksync.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.stocksync.models.Customer
import com.example.stocksync.models.Product

/**
 * DatabaseHandler manages the local SQLite database for StockSync.
 * It extends SQLiteOpenHelper, which provides callbacks for database creation 
 * and version management.
 * 
 * This is a core component for persistent storage in our school project.
 */
class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // Database metadata
        private const val DATABASE_NAME = "StockSync.db"
        private const val DATABASE_VERSION = 2 // Incremented to 2 when we added image support

        // Table Names - used throughout the class to avoid typos
        private const val TABLE_PRODUCTS = "Products"
        private const val TABLE_CUSTOMERS = "Customers"
        private const val TABLE_ORDERS = "Orders"

        // Products Table Columns
        private const val KEY_PROD_ID = "id"
        private const val KEY_PROD_NAME = "name"
        private const val KEY_PROD_PRICE = "price"
        private const val KEY_PROD_QTY = "quantity"
        private const val KEY_PROD_IMAGE_URI = "image_uri"

        // Customers Table Columns
        private const val KEY_CUST_ID = "id"
        private const val KEY_CUST_NAME = "name"
        private const val KEY_CUST_PHONE = "phone"

        // Orders Table Columns
        private const val KEY_ORDER_ID = "id"
        private const val KEY_ORDER_CUST_ID = "cust_id"
        private const val KEY_ORDER_PROD_ID = "prod_id"
        private const val KEY_ORDER_DATE = "date"
        private const val KEY_ORDER_QTY = "qty"
    }

    /**
     * Called when the database is created for the first time.
     * This is where the creation of tables and the initial population 
     * of the initial data should happen.
     */
    override fun onCreate(db: SQLiteDatabase) {
        // SQL string to create the Products table
        val createProductsTable = ("CREATE TABLE $TABLE_PRODUCTS (" +
                "$KEY_PROD_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$KEY_PROD_NAME TEXT," +
                "$KEY_PROD_PRICE REAL," +
                "$KEY_PROD_QTY INTEGER," +
                "$KEY_PROD_IMAGE_URI TEXT)")

        // SQL string to create the Customers table
        val createCustomersTable = ("CREATE TABLE $TABLE_CUSTOMERS (" +
                "$KEY_CUST_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$KEY_CUST_NAME TEXT," +
                "$KEY_CUST_PHONE TEXT)")

        // SQL string to create the Orders table with Foreign Key constraints
        // Foreign keys ensure that an order cannot exist without a valid customer and product.
        val createOrdersTable = ("CREATE TABLE $TABLE_ORDERS (" +
                "$KEY_ORDER_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$KEY_ORDER_CUST_ID INTEGER," +
                "$KEY_ORDER_PROD_ID INTEGER," +
                "$KEY_ORDER_DATE TEXT," +
                "$KEY_ORDER_QTY INTEGER," +
                "FOREIGN KEY($KEY_ORDER_CUST_ID) REFERENCES $TABLE_CUSTOMERS($KEY_CUST_ID)," +
                "FOREIGN KEY($KEY_ORDER_PROD_ID) REFERENCES $TABLE_PRODUCTS($KEY_PROD_ID))")

        // Execute the SQL creation strings
        db.execSQL(createProductsTable)
        db.execSQL(createCustomersTable)
        db.execSQL(createOrdersTable)
    }

    /**
     * Called when the database needs to be upgraded (e.g., when DATABASE_VERSION is increased).
     * For this project, we simply drop and recreate the tables.
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CUSTOMERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ORDERS")
        onCreate(db)
    }

    // --- Product Operations ---

    /**
     * Adds a new product to the database.
     * @return The ID of the newly inserted row, or -1 if an error occurred.
     */
    fun addProduct(product: Product): Long {
        val db = this.writableDatabase
        // ContentValues is used to store a set of values that the ContentResolver can process.
        // It provides a type-safe way to map column names to values.
        val values = ContentValues().apply {
            put(KEY_PROD_NAME, product.name)
            put(KEY_PROD_PRICE, product.price)
            put(KEY_PROD_QTY, product.quantity)
            put(KEY_PROD_IMAGE_URI, product.imageUri)
        }
        val success = db.insert(TABLE_PRODUCTS, null, values)
        db.close() // It's important to close the connection to avoid memory leaks
        return success
    }

    /**
     * Retrieves all products from the database and returns them as a List.
     */
    fun readProducts(): List<Product> {
        val productList = mutableListOf<Product>()
        val selectQuery = "SELECT * FROM $TABLE_PRODUCTS"
        val db = this.readableDatabase
        // A Cursor represents the result set of a query in Android.
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                // Get values from the current row index
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_PROD_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PROD_NAME))
                val price = cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_PROD_PRICE))
                val quantity = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_PROD_QTY))
                val imageUri = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PROD_IMAGE_URI))
                
                // Add the data to our list as a Product object
                productList.add(Product(id, name, price, quantity, imageUri))
            } while (cursor.moveToNext())
        }
        cursor.close() // Always close cursors after use
        db.close()
        return productList
    }

    // --- Customer Operations ---

    /**
     * Adds a new customer to the database.
     */
    fun addCustomer(customer: Customer): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_CUST_NAME, customer.name)
            put(KEY_CUST_PHONE, customer.phone)
        }
        val success = db.insert(TABLE_CUSTOMERS, null, values)
        db.close()
        return success
    }

    /**
     * Reads all customers from the database.
     */
    fun readCustomers(): List<Customer> {
        val customerList = mutableListOf<Customer>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_CUSTOMERS", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_CUST_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CUST_NAME))
                val phone = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CUST_PHONE))
                customerList.add(Customer(id, name, phone))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return customerList
    }

    // --- Order Operations ---

    /**
     * placeOrder inserts a new order and subtracts the ordered quantity from Product stock.
     * This uses a Transaction (beginTransaction) to ensure data integrity:
     * either BOTH the order is saved and stock is reduced, or NEITHER happens.
     * 
     * @return true if the full transaction was successful, false otherwise.
     */
    fun placeOrder(customerId: Int, productId: Int, quantity: Int, date: String): Boolean {
        val db = this.writableDatabase
        db.beginTransaction() // Start the transaction
        try {
            // 1. Insert the record into the Orders table
            val values = ContentValues().apply {
                put(KEY_ORDER_CUST_ID, customerId)
                put(KEY_ORDER_PROD_ID, productId)
                put(KEY_ORDER_QTY, quantity)
                put(KEY_ORDER_DATE, date)
            }
            val orderId = db.insert(TABLE_ORDERS, null, values)

            // If entry is not inserted, fail the transaction
            if (orderId == -1L) return false

            // 2. Update Product Stock by subtracting the ordered quantity
            // This SQL query updates the specific product's inventory level.
            val updateQuery = "UPDATE $TABLE_PRODUCTS SET $KEY_PROD_QTY = $KEY_PROD_QTY - $quantity WHERE $KEY_PROD_ID = $productId"
            db.execSQL(updateQuery)

            // Mark the transaction as successful
            db.setTransactionSuccessful()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            // Finish the transaction (commit if successful, rollback if not)
            db.endTransaction()
            db.close()
        }
    }
}
