package com.example.myapplication.recycleStuff

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.FirstFragmentDirections
import com.example.myapplication.R
import com.example.myapplication.data.Food
import kotlinx.android.synthetic.main.food_item.view.*


class FoodAdapter: RecyclerView.Adapter<FoodAdapter.MyViewHolder>() {

    private var mExampleList = emptyList<Food>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.food_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: Food = mExampleList[position]
        holder.itemView.testingName.text = currentItem.name
        holder.itemView.textViewDescription.text = currentItem.description

        holder.itemView.CardViewFood.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToViewFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

        holder.itemView.imageButton.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(food: List<Food>) {
        this.mExampleList = food
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mExampleList.size
    }
}