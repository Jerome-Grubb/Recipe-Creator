package com.example.myapplication.IngredientsData

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.Food
import com.example.myapplication.data.FoodDao

@Database(entities = [Ingredients::class], version = 1, exportSchema = false)
abstract class IngredientsDatabase: RoomDatabase() {

    abstract fun ingredientsDao(): IngredientsDao

    companion object {
        @Volatile
        private var INSTANCE: IngredientsDatabase? = null

        fun getDatabase(context: Context): IngredientsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        IngredientsDatabase::class.java,
                        "ingredients_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}