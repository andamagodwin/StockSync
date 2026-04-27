# QuickBill 📦✨

**QuickBill** is a premium, high-performance retail and inventory management application for Android. Designed with a modern aesthetic and seamless user experience, it empowers small to medium businesses to manage stock, customers, and orders with professional precision.

---

## 🎨 Premium Design System

QuickBill features a custom-engineered **Indigo & Gold** design language:
- **Immersive Branding**: Immersive gradient headers and a persistent colored status bar for a unified app experience.
- **Visual Clarity**: High-density cards with stock badges, customer avatars, and colored order pills.
- **Interactive UX**: Extended Floating Action Buttons (FABs), smooth transitions, and rich empty-state illustrations.
- **Material 3**: Built on the latest Material Design foundations for a future-proof interface.

---

## 🚀 Key Features

- **Inventory Intelligence**: A polished 2-column grid view with real-time stock badges and high-performance image loading.
- **Smart Photo Picker**: Effortlessly attach product photos using modern Android system pickers with persistent URI storage.
- **Customer CRM**: Maintain structured records of your clientele with a clean, avatar-based interface.
- **Transactional Ordering**: Atomic order placement that automatically synchronizes stock levels to prevent inventory drift.
- **Order History**: A dedicated log of all business transactions with clear visual status indicators.

---

## 🛠 Tech Stack

- **Kotlin**: Modern, expressive code architecture.
- **SQLite (ACID)**: Robust local persistence ensuring data integrity for all transactions.
- **ViewBinding**: Type-safe and efficient UI interaction.
- **Coil**: Advanced asynchronous image loading.
- **Material 3**: Premium UI components and styling.

---

## 📁 Architecture

- `activities/`: Cleanly separated screen logic for high maintainability.
- `models/`: Immutable data models representing core business entities.
- `database/`: Centralized SQLite handler with transaction-aware operations.
- `adapters/`: Optimized RecyclerView logic for smooth list performance.

---

## 📖 Documentation

For a technical deep dive into the database schema and implementation details, please see **[PROJECT_DOCUMENTATION.md](./PROJECT_DOCUMENTATION.md)**.

---

## 🏗 Setup & Installation

1. Clone this repository.
2. Open in **Android Studio (Ladybug or newer)**.
3. Sync Gradle and build.
4. Run on API 24+ (Recommended: API 35+ for best visual fidelity).

---

*QuickBill — Streamlining Business, One Transaction at a Time.*
