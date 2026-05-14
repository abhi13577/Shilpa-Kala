package com.artisan.shilpakala

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.artisan.shilpakala.ui.ShilpaKalaNavigation
import com.artisan.shilpakala.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Earthy background color for the whole app window if needed
            val viewModel: AppViewModel = viewModel()
            ShilpaKalaNavigation(viewModel = viewModel)
        }
    }
}
