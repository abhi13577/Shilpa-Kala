package com.artisan.shilpakala.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.NumberFormat
import java.util.Locale

class ImageProcessor(private val context: Context) {

    /**
     * Processes the image by adding branding overlays and text, then saves it.
     */
    fun processAndSaveBrandedImage(
        sourceUri: Uri,
        artisanName: String,
        productName: String,
        woodType: String,
        price: Double
    ): Uri? {
        val originalBitmap = loadBitmapFromUri(sourceUri) ?: return null

        // Create a mutable copy to draw on
        val brandedBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(brandedBitmap)
        val width = canvas.width.toFloat()
        val height = canvas.height.toFloat()

        // 1. Draw Decorative Border
        val borderPaint = Paint().apply {
            color = Color.parseColor("#D4AF37") // Gold
            style = Paint.Style.STROKE
            strokeWidth = width * 0.02f
        }
        canvas.drawRect(0f, 0f, width, height, borderPaint)

        // 2. Draw Warm Brown Overlay (Bottom 25%)
        val overlayHeight = height * 0.25f
        val overlayPaint = Paint().apply {
            color = Color.argb(180, 101, 67, 33) // Warm Brown with transparency
            style = Paint.Style.FILL
        }
        canvas.drawRect(0f, height - overlayHeight, width, height, overlayPaint)

        // 3. Setup Text Paints
        val margin = width * 0.05f
        var currentY = height - overlayHeight + margin

        // "Handmade in Karnataka" - Bold White
        val titlePaint = Paint().apply {
            color = Color.WHITE
            textSize = width * 0.045f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }
        canvas.drawText("Handmade in Karnataka", margin, currentY, titlePaint)
        currentY += titlePaint.textSize * 1.2f

        // Artisan Name - Gold
        val artisanPaint = Paint().apply {
            color = Color.parseColor("#FFD700") // Bright Gold
            textSize = width * 0.04f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
            isAntiAlias = true
        }
        canvas.drawText("By: $artisanName", margin, currentY, artisanPaint)
        currentY += artisanPaint.textSize * 1.5f

        // Product Details - White
        val detailPaint = Paint().apply {
            color = Color.WHITE
            textSize = width * 0.035f
            isAntiAlias = true
        }
        val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
        val priceText = currencyFormatter.format(price)

        canvas.drawText("$productName | $woodType", margin, currentY, detailPaint)
        canvas.drawText(priceText, width - margin - detailPaint.measureText(priceText), currentY, detailPaint)

        // 4. Save the Result
        return saveBitmapToExternalStorage(brandedBitmap)
    }

    private fun loadBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun saveBitmapToExternalStorage(bitmap: Bitmap): Uri? {
        val fileName = "Branded_${System.currentTimeMillis()}.jpg"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return try {
            val file = File(storageDir, fileName)
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()

            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
