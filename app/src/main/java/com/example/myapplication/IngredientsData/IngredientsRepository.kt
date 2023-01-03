package com.example.myapplication.IngredientsData

import androidx.lifecycle.LiveData
import com.example.myapplication.data.Food
import com.example.myapplication.data.FoodDao

class IngredientsRepository(private val ingredientsDao: IngredientsDao) {
    val readAllData: LiveData<List<Ingredients>> = ingredientsDao.readAllData()

    suspend fun addIngredients(ingredients: Ingredients) {
        ingredientsDao.addIngredients(ingredients)
    }

    suspend fun updateIngredients(ingredients: Ingredients) {
        ingredientsDao.updateIngredients(ingredients)
    }

    suspend fun deleteIngredients(ingredients: Int) {
        ingredientsDao.deleteIngredients(ingredients)
    }
}