package com.example.myapplication.data

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "food_table")
data class Food (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val method: String,
    val foodId: Int,
    val rating: Float): Parcelable