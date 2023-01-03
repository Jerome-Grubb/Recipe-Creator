package com.example.myapplication.recycleStuff

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.IngredientsData.Ingredients
import com.example.myapplication.R
import kotlinx.android.synthetic.main.food_item.view.*

class ViewIngredientAdapter : RecyclerView.Adapter<ViewIngredientAdapter.MyViewHolder>() {
    private var mExampleList = emptyList<Ingredients>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_ingredient, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: Ingredients = mExampleList[position]
        holder.itemView.testingName.text = currentItem.name
        holder.itemView.textViewDescription.text = currentItem.quantity
    }

    fun setData(ingredients: List<Ingredients>) {
        this.mExampleList = ingredients
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mExampleList.size
    }
}