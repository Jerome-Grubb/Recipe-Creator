package com.example.myapplication

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.IngredientsData.Ingredients
import com.example.myapplication.data.Food
import com.example.myapplication.data.FoodViewModel
import com.example.myapplication.IngredientsData.IngredientsViewModal
import com.example.myapplication.recycleStuff.UpdateIngredientAdapter
import kotlinx.android.synthetic.main.fragment_blank.view.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import kotlinx.android.synthetic.main.fragment_update.view.recyclerView2

class updateFragment : Fragment() {

    private val args by navArgs<updateFragmentArgs>()
    private lateinit var mFoodViewModel: FoodViewModel
    private lateinit var mIngredientsViewModal: IngredientsViewModal
    private lateinit var ingredientsList: ArrayList<Ingredients>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        ingredientsList = ArrayList()

        activity?.setTitle("Update Recipe")

        val adapter = UpdateIngredientAdapter(this)
        val recyclerView = view.recyclerView2
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mFoodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)
        mIngredientsViewModal = ViewModelProvider(this).get(IngredientsViewModal::class.java)
        mIngredientsViewModal.readAllData.observe(viewLifecycleOwner, Observer { ingredients ->
            for (i in ingredients) {
                if (i.foodId == args.currentFood.foodId && !ingredientsList.contains(i)) {
                    ingredientsList.add(i)
                }
            }
            adapter.setData(ingredientsList)
        })

        view.nameUpdate.setText(args.currentFood.name)
        view.nameDescription.setText(args.currentFood.description)
        view.editTextTextMultiLine2.setText(args.currentFood.method)
        view.ratingBar3.setRating(args.currentFood.rating)

        view.button4.setOnClickListener {
            updateItem()
        }

        view.findViewById<Button>(R.id.button5)?.setOnClickListener {
            addNewIngredient()
        }

        setHasOptionsMenu(true)
        return view
    }

    fun deleteIngredient(ingredient: Ingredients) {
        mIngredientsViewModal.deleteIngredients(ingredient.id)
    }

    private fun updateItem() {
        val name = nameUpdate.text.toString()
        val description = nameDescription.text.toString()
        val method = editTextTextMultiLine2.text.toString()
        val rating = ratingBar3.rating

        if (inputCheck(name, description)) {
            val updatedFood = Food(args.currentFood.id, name, description, method, args.currentFood.foodId, rating)
            mFoodViewModel.updateFood(updatedFood)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_FirstFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT)
        }
    }

    private fun inputCheck(name: String, description: String): Boolean {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(description))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteFood()
        }
        if (item.itemId == R.id.menu_share) {
                var ingString = ""
                for (i in ingredientsList) {
                    ingString += "\n" + i.name + ": " + i.quantity
                }
                val intent= Intent()
                intent.action=Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT,"""Hey Check out this Recipe:
                    |Title: ${args.currentFood.name}
                    |Description: ${args.currentFood.description}
                    |Method: ${args.currentFood.method}
                    |Ingredients: ${ingString}
                """.trimMargin())
                intent.type="text/plain"
                startActivity(Intent.createChooser(intent,"Share To:"))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteFood() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_, _ ->
            mFoodViewModel.deleteFood(args.currentFood)
            Toast.makeText(requireContext(), "Sucessfully reomved: ${args.currentFood.name}", Toast.LENGTH_SHORT).show()
            for (i in ingredientsList) {
                deleteIngredient(i)
            }
            ingredientsList = ArrayList()
            findNavController().navigate(R.id.action_updateFragment_to_FirstFragment)
        }
        builder.setNegativeButton("No") {_,_ -> }
        builder.setTitle("Delete ${args.currentFood.name}?")
        builder.setMessage("Are you sure you want to delete ${args.currentFood.name}")
        builder.create().show()
    }

    private fun addNewIngredient() {
        val builder = AlertDialog.Builder(context)

        val form = layoutInflater.inflate(R.layout.add_ingredient, null, false)
        builder.setView(form)

        val nameBox: EditText = form.findViewById(R.id.nameBox)
        val slackBox: EditText = form.findViewById(R.id.slackBox)

        builder.setPositiveButton("Add") { _, _ ->
            val ingredients = Ingredients(
                0,
                nameBox.text.toString(),
                slackBox.text.toString(),
                args.currentFood.foodId
            )
            mIngredientsViewModal.addIngredients(ingredients)
        }

        builder.show()
    }
}