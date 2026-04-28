# QuickBill: Friendly Explorer's Guide

Welcome to the **QuickBill** project! This document is written for anyone who wants to understand how this pharmacy app is built without needing to know how to write code.

## What is this App?
QuickBill is a digital assistant for pharmacists. It helps them move away from paper notebooks by keeping a digital list of **Drugs** (medicine), a list of **Patients** (the people they help), and a history of **Orders** (when a patient gets a drug).

---

## Exploring the Folders (The "How it's Built")

Think of the project like a house. Here is what each "room" does:

### 1. The Brains (The `java` Folder)
Located at: `app/src/main/java/com/example/quickbill/`
This is where the logic lives.
*   **activities**: These are the individual "Screens." For example, there's a screen for logging in and another for adding a new drug.
*   **database**: This is the app's "Long-term Memory." When you save a patient's name, it's stored here so the app remembers it even if you turn off the phone.
*   **fragments**: These are "Mini-screens" that sit inside larger ones, helping the app look organized.
*   **models**: These are "Checklists." They define what information we need. For a drug, the checklist says we need a Name, a Price, and a Quantity.

### 2. The Closet (The `res` Folder)
Located at: `app/src/main/res/`
This is where the "Appearance" is kept.
*   **layout**: These are the "Blueprints." They decide where buttons go and how big the text is.
*   **drawable**: This is the "Picture Frame" where icons (like the home icon) and images are kept.
*   **values**: This is the "Style Guide."
    *   `colors.xml`: All the blues and whites used in the app.
    *   `strings.xml`: Every piece of text you see on screen (like "Drug Name" or "Save Patient"). This makes it easy to change "Customer" to "Patient" all at once!
*   **menu**: These define the lists of options you see, like the one that pops up when you click the 3 dots.

### 3. The Front Gate (The `AndroidManifest.xml`)
This is the "Rules of the House." it tells the phone which screen to show first (the Login screen) and what permissions the app needs.

---

## How to use QuickBill

1.  **Inventory (Drugs Tab)**: View your current stock. Tap the "+" button to add a new medication.
2.  **Patients Tab**: Keep track of who you are serving. Add their name and contact info here.
3.  **Making a Sale (Orders)**: Go to "Create Order." Pick a Patient, pick a Drug, and enter the quantity. The app automatically does the math and reduces your stock levels!
4.  **Security**: Use the Login screen to ensure only authorized staff can access the pharmacy records.

---
*QuickBill: Making pharmacy management simple.*
