package com.example.rentcar.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.rentcar.data.dao.ProductsDao
import com.example.rentcar.data.dao.UserDao
import com.example.rentcar.models.api.ProductAPIModel
import com.example.rentcar.models.user.RoleModel
import com.example.rentcar.models.user.UserModel

@Database(
    entities = [
        ProductAPIModel::class,
        UserModel::class,
        RoleModel::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getProductsDao(): ProductsDao
    abstract fun getUserDao(): UserDao

    companion object {
        const val DATABASE_NAME = "car_database"
    }
}