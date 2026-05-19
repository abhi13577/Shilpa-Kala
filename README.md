🪵 Shilpa-Kala — Product Photography & Branding App
> **Empowering Artisans Digitally**
![Platform](https://img.shields.io/badge/Platform-Android-green) ![Language](https://img.shields.io/badge/Language-Kotlin-blue) ![UI](https://img.shields.io/badge/UI-Jetpack%20Compose-purple) ![Architecture](https://img.shields.io/badge/Architecture-MVVM-orange) ![Database](https://img.shields.io/badge/Database-Room-red)
---
📖 About the Project
Shilpa-Kala is an Android mobile application developed for traditional artisans of Karnataka — particularly wood carvers and toy makers — who produce high-quality handcrafted products but struggle to present them professionally in the digital marketplace.
The app provides a guided camera system and automatic image branding that helps artisans capture professional product photos and add their artisan identity, product details, and the iconic "Handmade in Karnataka" label — all without any photography knowledge, and completely offline.
---
🎯 Problem Statement
Traditional artisans of Karnataka lack the knowledge, tools, and resources to present their products professionally in the digital marketplace. Poor-quality product photos shared on WhatsApp and social media fail to attract premium buyers, undermine the perceived value of their craft, and leave skilled artisans economically disadvantaged despite the cultural and artistic significance of their work.
---
✨ Features
📷 Guided Camera — Live camera with a golden alignment box to help artisans frame their product perfectly
🎨 Auto Branding — Automatically adds warm-styled overlay with "Handmade in Karnataka" label, artisan name, product name, wood type, and price
🖼️ Gallery — View all branded images in a clean grid layout
📤 Direct Sharing — Share branded images directly to WhatsApp, Facebook, and other platforms
🔒 Offline First — All core features work completely without internet connection
⚙️ Branding Setup — Save artisan name and branding details once and reuse across all photos
---
🛠️ Technologies Used
Technology	Purpose
Kotlin	Primary programming language
Jetpack Compose	Modern declarative UI framework
CameraX	Guided camera with overlay support
Room Database	Local offline storage for images and artisan details
KSP	Kotlin Symbol Processing for Room annotation processing
MVVM Architecture	Clean separation of UI and business logic
Android Bitmap API	Image processing and branding overlay generation
Coil	Efficient image loading in gallery
FileProvider	Secure image sharing to external apps
Jetpack Navigation Compose	Screen-to-screen navigation
StateFlow & Coroutines	UI state management and background operations
Material Design 3	Modern UI components and theming
---
🏗️ Project Structure
```
app/
└── src/main/java/com/artisan/shilpakala/
    ├── MainActivity.kt
    ├── data/
    │   └── AppDatabase.kt          # Room entities, DAOs, Database
    ├── viewmodel/
    │   └── AppViewModel.kt         # Business logic and state management
    ├── ui/
    │   ├── SplashScreen.kt         # Animated splash screen
    │   ├── HomeScreen.kt           # Main dashboard
    │   ├── CameraScreen.kt         # Guided camera with overlay
    │   ├── BrandingScreen.kt       # Add product details and branding
    │   ├── GalleryScreen.kt        # View and share branded images
    │   ├── SettingsScreen.kt       # Save artisan name and branding config
    │   └── Navigation.kt           # App navigation graph
    └── utils/
        └── ImageProcessor.kt       # Bitmap processing and overlay logic
```
---
📱 App Screens
Screen	Description
Splash Screen	Animated logo with tagline "Empowering Artisans Digitally"
Home Dashboard	Three quick-access cards — Capture, Gallery, Branding Setup
Camera Screen	Live camera preview with golden guide rectangle overlay
Branding Screen	Enter artisan name, product name, wood type, price
Gallery Screen	Grid of all saved branded images with share and delete
Settings Screen	Save default artisan name and branding configuration
---
🗄️ Database Schema
Table	Fields
branded_images	id, imagePath, artisanName, productName, woodType, price
product_details	id, productName, woodType, price, description
branding_config	id, artisanName, labelText
---
🚀 Getting Started
Prerequisites
Android Studio (latest version)
Android device running Android 8.0 (API 26) or higher
USB Debugging enabled on your device
Installation
Clone the repository
```bash
git clone https://github.com/your-username/shilpa-kala.git
```
Open in Android Studio
```
File → Open → Select the cloned folder
```
Sync Gradle
```
Click "Sync Now" when prompted
```
Run the app
```
Connect your Android device → Click the ▶ Run button
```
Permissions Required
`CAMERA` — For capturing product photos
`READ_MEDIA_IMAGES` — For accessing saved images
`WRITE_EXTERNAL_STORAGE` — For saving branded images (Android 8 only)
---
📊 Evaluation Parameters
Parameter	Weightage
Functionality	30%
UI/UX Design	25%
Code Quality	20%
Innovation	15%
Presentation	10%
---
🔮 Future Scope
🤖 GenAI Integration — Using Gemini API to automatically enhance product backgrounds
🔍 Smart Detection — AI-based identification of product type through camera
🛒 E-commerce Integration — Direct product listing on online marketplaces
☁️ Cloud Backup — Online storage and sync of branded images
---
👨‍💻 Developer
Field	Details
Name	Abhishek Hujaratti
USN	1AT22CS004
College	Atria Institute of Technology
Project	Shilpa-Kala (Self-Employment)
---
