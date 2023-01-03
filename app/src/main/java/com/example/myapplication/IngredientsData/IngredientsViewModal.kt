package com.example.myapplication.IngredientsData

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Food
import com.example.myapplication.data.FoodDatabase
import com.example.myapplication.data.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IngredientsViewModal(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Ingredients>>
    private val repository: IngredientsRepository

    init {
        val ingredientsDao = IngredientsDatabase.getDatabase(application).ingredientsDao()
        repository = IngredientsRepository(ingredientsDao)
        readAllData = repository.readAllData
    }

    fun addIngredients(ingredients: Ingredients) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addIngredients(ingredients)
        }
    }

    fun updateIngredients(ingredients: Ingredients) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateIngredients(ingredients)
        }
    }

    fun deleteIngredients(ingredients: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteIngredients(ingredients)
        }
    }
}