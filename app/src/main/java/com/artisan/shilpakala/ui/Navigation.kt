package com.artisan.shilpakala.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.artisan.shilpakala.viewmodel.AppViewModel

@Composable
fun ShilpaKalaNavigation(viewModel: AppViewModel) {
    var showSplash by remember { mutableStateOf(true) }
    var capturedUri by remember { mutableStateOf<Uri?>(null) }
    val navController = rememberNavController()
    val context = LocalContext.current

    // Permission launcher for Camera
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            navController.navigate("camera")
        } else {
            Toast.makeText(context, "Camera permission is required to take product photos", Toast.LENGTH_SHORT).show()
        }
    }

    if (showSplash) {
        SplashScreen(onNavigate = { showSplash = false })
    } else {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen(
                    onCaptureClick = {
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    },
                    onGalleryClick = { navController.navigate("gallery") },
                    onSettingsClick = { navController.navigate("settings") }
                )
            }

            composable("camera") {
                CameraScreen(
                    onImageCaptured = { uri ->
                        capturedUri = uri
                        CoroutineScope(Dispatchers.Main).launch {
                            navController.navigate("branding")
                        }
                    },
                    onBack = { navController.popBackStack() }
                )
            }

            composable("branding") {
                capturedUri?.let { uri ->
                    BrandingScreen(
                        imageUri = uri,
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() },
                        onDone = {
                            // Navigate to gallery and clear backstack up to home
                            navController.navigate("gallery") {
                                popUpTo("home") { inclusive = false }
                            }
                        }
                    )
                }
            }

            composable("gallery") {
                GalleryScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable("settings") {
                SettingsScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
