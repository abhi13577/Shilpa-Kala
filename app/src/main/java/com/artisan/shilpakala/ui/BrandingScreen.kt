package com.artisan.shilpakala.ui

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.artisan.shilpakala.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrandingScreen(
    imageUri: Uri,
    viewModel: AppViewModel,
    onBack: () -> Unit,
    onDone: () -> Unit
) {
    val brandingConfig by viewModel.brandingConfig.collectAsState()

    var productName by remember { mutableStateOf("") }
    var woodType by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var artisanName by remember { mutableStateOf(brandingConfig?.artisanName ?: "") }

    LaunchedEffect(brandingConfig) {
        brandingConfig?.let { artisanName = it.artisanName }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Branding", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF5D3A1A))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Captured Product",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = artisanName,
                onValueChange = { artisanName = it },
                label = { Text("Artisan Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                value = productName,
                onValueChange = { productName = it },
                label = { Text("Product Name (e.g., Elephant Statue)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                value = woodType,
                onValueChange = { woodType = it },
                label = { Text("Wood Type (e.g., Sandalwood)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Price (₹)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val priceVal = price.toDoubleOrNull() ?: 0.0
                    viewModel.brandAndSaveImage(imageUri, artisanName, productName, woodType, priceVal)
                    onDone()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37))
            ) {
                Text("Apply Branding & Save", color = Color.Black, fontWeight = FontWeight.Bold)
            }

            TextButton(onClick = onBack) {
                Text("Back", color = Color.Gray)
            }
        }
    }
}
