package com.example.myapplication.recycleStuff

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.IngredientsData.Ingredients
import com.example.myapplication.R
import com.example.myapplication.updateFragment
import kotlinx.android.synthetic.main.food_item.view.testingName
import kotlinx.android.synthetic.main.upgrade_ingredient_view.view.*

class UpdateIngredientAdapter(blankFragment: updateFragment) : RecyclerView.Adapter<UpdateIngredientAdapter.MyViewHolder>() {
    private var fragment = blankFragment
    private var mExampleList = emptyList<Ingredients>()
    private var testList = ArrayList<Ingredients>()
    private var anotherLIst = emptyList<Ingredients>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.upgrade_ingredient_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: Ingredients = mExampleList[position]
        holder.itemView.testingName.text = currentItem.name
        holder.itemView.testing3.text = currentItem.quantity

        holder.itemView.button2.setOnClickListener {
            fragment.deleteIngredient(currentItem)
            testList = ArrayList(this.mExampleList)
            testList.remove(currentItem)
            anotherLIst = testList.toList()
            setData(anotherLIst)
        }


    }

    fun setData(ingredients: List<Ingredients>) {
        this.mExampleList = ingredients
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mExampleList.size
    }
}