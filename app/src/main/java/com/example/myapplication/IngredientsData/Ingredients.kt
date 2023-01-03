package com.example.myapplication.IngredientsData

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "ingredients_table")
data class Ingredients (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val quantity: String,
    val foodId: Int
): Parcelable