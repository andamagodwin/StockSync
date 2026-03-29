# StockSync 📦

**StockSync** is a modern, efficient retail management application built for Android. It streamlines inventory tracking, customer management, and order processing for small to medium-sized businesses.

![StockSync Logo](app/src/main/res/mipmap-xxxhdpi/ic_launcher.png)

---

## 🚀 Key Features

- **Inventory Dashboard**: A clean, 2-column grid view of all your products with real-time stock levels.
- **Product Images**: Attach and persist photos for every item using the modern Android Photo Picker.
- **Customer Database**: Maintain clear records of your frequent buyers.
- **Smart Order Flow**: Place orders with automatic stock synchronisation to prevent overselling.
- **Modern UI/UX**: Material 3 design with a unified purple branding and a seamless status bar transition.

## 🛠 Tech Stack

- **Kotlin**: Primary programming language.
- **SQLite**: Local persistent storage for products, customers, and orders.
- **ViewBinding**: Type-safe access to layout views.
- **Coil**: High-performance image loading library.
- **Material 3**: Modern Android design components.

## 📁 Project Structure

- `activities/`: Contains all screen logic (Login, Inventory, Orders).
- `models/`: Data classes representing our core business entities.
- `database/`: Handlers for SQLite creation and transactions.
- `adapters/`: RecyclerView logic for bridging data to the UI.

## 📖 Documentation

For a deep dive into the architecture, database schema, and implementation details of this coursework project, please refer to the detailed **[PROJECT_DOCUMENTATION.md](./PROJECT_DOCUMENTATION.md)**.

## 🏗 Setup & Installation

1. Clone this repository.
2. Open the project in **Android Studio (Hedgehog or newer)**.
3. Sync the project with Gradle files.
4. Run the app on an Emulator or Physical Device (API 24+).

---

*This project was developed as part of a University Coursework assignment.*
