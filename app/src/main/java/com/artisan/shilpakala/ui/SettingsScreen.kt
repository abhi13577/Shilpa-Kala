package com.artisan.shilpakala.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.artisan.shilpakala.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: AppViewModel,
    onBack: () -> Unit
) {
    val brandingConfig by viewModel.brandingConfig.collectAsState()
    var artisanName by remember { mutableStateOf(brandingConfig?.artisanName ?: "") }
    var showSuccess by remember { mutableStateOf(false) }

    LaunchedEffect(brandingConfig) {
        brandingConfig?.let { artisanName = it.artisanName }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Branding Setup", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF5D3A1A))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            Text("Artisan Profile", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = artisanName,
                onValueChange = { artisanName = it },
                label = { Text("Default Artisan Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.saveBranding(artisanName)
                    showSuccess = true
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5D3A1A))
            ) {
                Text("Save Changes")
            }

            if (showSuccess) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Branding details saved successfully!", color = Color(0xFF2E7D32))
            }

            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = onBack) {
                Text("Back to Home")
            }
        }
    }
}
