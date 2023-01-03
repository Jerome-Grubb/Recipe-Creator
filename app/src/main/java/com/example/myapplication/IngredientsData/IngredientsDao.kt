package com.example.myapplication.IngredientsData

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.data.Food

@Dao
interface IngredientsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addIngredients(ingredients: Ingredients)

    @Update
    suspend fun updateIngredients(ingredients: Ingredients)

    @Query("DELETE FROM ingredients_table WHERE id=:arg0")
    suspend fun deleteIngredients(arg0: Int)

    @Query("SELECT * FROM ingredients_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Ingredients>>
}