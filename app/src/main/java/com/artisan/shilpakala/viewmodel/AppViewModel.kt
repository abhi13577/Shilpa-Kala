package com.artisan.shilpakala.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.artisan.shilpakala.data.AppDatabase
import com.artisan.shilpakala.data.BrandedImage
import com.artisan.shilpakala.data.BrandingConfig
import com.artisan.shilpakala.utils.ImageProcessor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val imageProcessor = ImageProcessor(application)

    private val _brandedImages = MutableStateFlow<List<BrandedImage>>(emptyList())
    val brandedImages: StateFlow<List<BrandedImage>> = _brandedImages.asStateFlow()

    private val _brandingConfig = MutableStateFlow<BrandingConfig?>(null)
    val brandingConfig: StateFlow<BrandingConfig?> = _brandingConfig.asStateFlow()

    private val _lastBrandedUri = MutableStateFlow<Uri?>(null)
    val lastBrandedUri: StateFlow<Uri?> = _lastBrandedUri.asStateFlow()

    init {
        loadImages()
        loadBranding()
    }

    fun loadImages() {
        viewModelScope.launch {
            db.brandedImageDao().getAllImages().collectLatest { images ->
                _brandedImages.value = images
            }
        }
    }

    fun loadBranding() {
        viewModelScope.launch {
            db.brandingDao().getConfig().collectLatest { config ->
                _brandingConfig.value = config
            }
        }
    }

    fun saveBranding(artisanName: String, label: String = "Handmade in Karnataka") {
        viewModelScope.launch {
            val config = BrandingConfig(artisanName = artisanName, labelText = label)
            db.brandingDao().insertConfig(config)
        }
    }

    fun brandAndSaveImage(
        sourceUri: Uri,
        artisanName: String,
        productName: String,
        woodType: String,
        price: Double
    ) {
        viewModelScope.launch {
            val brandedUri = imageProcessor.processAndSaveBrandedImage(
                sourceUri, artisanName, productName, woodType, price
            )

            if (brandedUri != null) {
                val brandedImage = BrandedImage(
                    imagePath = brandedUri.toString(),
                    artisanName = artisanName,
                    productName = productName,
                    woodType = woodType,
                    price = price
                )
                db.brandedImageDao().insertImage(brandedImage)
                _lastBrandedUri.value = brandedUri
            }
        }
    }

    fun deleteImage(image: BrandedImage) {
        viewModelScope.launch {
            db.brandedImageDao().deleteImage(image)
        }
    }
}
