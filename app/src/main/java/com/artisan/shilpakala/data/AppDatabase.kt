package com.artisan.shilpakala.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "product_details")
data class ProductDetails(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productName: String,
    val woodType: String,
    val price: Double,
    val description: String
)

@Entity(tableName = "branding_config")
data class BrandingConfig(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val artisanName: String,
    val labelText: String = "Handmade in Karnataka"
)

@Entity(tableName = "branded_images")
data class BrandedImage(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val imagePath: String,
    val artisanName: String,
    val productName: String,
    val woodType: String,
    val price: Double
)

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductDetails)

    @Query("SELECT * FROM product_details")
    fun getAllProducts(): Flow<List<ProductDetails>>

    @Delete
    suspend fun deleteProduct(product: ProductDetails)
}

@Dao
interface BrandingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConfig(config: BrandingConfig)

    @Query("SELECT * FROM branding_config LIMIT 1")
    fun getConfig(): Flow<BrandingConfig?>

    @Delete
    suspend fun deleteConfig(config: BrandingConfig)
}

@Dao
interface BrandedImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: BrandedImage)

    @Query("SELECT * FROM branded_images ORDER BY id DESC")
    fun getAllImages(): Flow<List<BrandedImage>>

    @Delete
    suspend fun deleteImage(image: BrandedImage)
}

@Database(
    entities = [ProductDetails::class, BrandingConfig::class, BrandedImage::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun brandingDao(): BrandingDao
    abstract fun brandedImageDao(): BrandedImageDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "shilpa_kala_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
