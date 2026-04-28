# QuickBill: Pharmacy Management System

**QuickBill** is a specialized retail and inventory management application designed specifically for pharmacies. It streamlines the process of managing drug inventory, tracking patient records, and processing sales orders with a focus on speed, clarity, and ease of use.

## Core Features

- **Drug Inventory Management**: 
  - Track stock levels, pricing, and details for all medications.
  - Attach images to drugs for easy identification.
  - Real-time stock updates as orders are placed.

- **Patient Records**:
  - Maintain a structured database of patients.
  - Quick access to patient contact information.
  - Visual, avatar-based list for easy navigation.

- **Seamless Ordering**:
  - Simplified "Order Placement" flow matching patients to drugs.
  - Automatic inventory deduction and error prevention (e.g., preventing sales of out-of-stock items).
  - Order history tracking with timestamps.

- **Modern Design**:
  - Immersive UI with a transparent status bar and integrated headers.
  - High-density information cards for efficient scanning.
  - Dark mode support for low-light environments.

## Technical Highlights

- **Architecture**: Modern Android architecture using ViewBinding and Fragments.
- **Database**: ACID-compliant SQLite implementation via `DatabaseHandler`.
- **UI Components**: Material 3 components, CoordinatorLayout, and high-performance RecyclerViews.
- **Image Handling**: Integration with modern Android system photo pickers for drug documentation.

---
*Developed for efficient pharmacy operations.*
