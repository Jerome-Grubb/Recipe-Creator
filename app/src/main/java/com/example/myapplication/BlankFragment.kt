package com.example.myapplication

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.IngredientsData.Ingredients
import com.example.myapplication.IngredientsData.IngredientsViewModal
import com.example.myapplication.data.Food
import com.example.myapplication.data.FoodViewModel
import com.example.myapplication.recycleStuff.IngredientAdapter
import kotlinx.android.synthetic.main.fragment_blank.*
import kotlinx.android.synthetic.main.fragment_blank.view.*
import kotlinx.android.synthetic.main.fragment_first.view.*


class BlankFragment : Fragment() {

    private lateinit var mFoodViewModel: FoodViewModel
    private lateinit var mIngredientsViewModal: IngredientsViewModal
    private lateinit var ingredientsList: ArrayList<Ingredients>
    private var foodId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This callback will only be called when MyFragment is at least Started.
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            for (i in ingredientsList) {
                deleteIngredient(i)
            }
            findNavController().navigate(R.id.action_BlankFragment_to_FirstFragment)
        }

        // The callback can be enabled or disabled here or in the lambda
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_blank, container, false)
        ingredientsList = ArrayList()
        activity?.setTitle("Create Recipe")

        val adapter = IngredientAdapter(this)
        val recyclerView = view.recyclerView2
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mFoodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)
        mFoodViewModel.readAllData.observe(viewLifecycleOwner, Observer { food ->
            foodId = food.size + 1
        })
        mIngredientsViewModal = ViewModelProvider(this).get(IngredientsViewModal::class.java)
        mIngredientsViewModal.readAllData.observe(viewLifecycleOwner, Observer { ingredients ->
            for (i in ingredients) {
                if (i.foodId == foodId && !ingredientsList.contains(i)) {
                    ingredientsList.add(i)
                }
            }
            adapter.setData(ingredientsList)
        })

        view.button3.setOnClickListener {
            insertDataToDatabase()
        }
        return view
    }

    private fun insertDataToDatabase() {
        val name = editTextTextPersonName.text.toString()
        val description = editTextTextPersonName2.text.toString()
        val method = editTextTextMultiLine.text.toString()
        val rating = ratingBar2.rating

        if (inputCheck(name, description)) {
            val food = Food(0, name, description, method, foodId, rating)
            mFoodViewModel.addFood(food)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_BlankFragment_to_FirstFragment)
        } else {
            Toast.makeText(
                requireContext(),
                "Please fill out all of the mandatory fields.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun deleteIngredient(ingredient: Ingredients) {
        ingredientsList.remove(ingredient)
        mIngredientsViewModal.deleteIngredients(ingredient.id)
    }

    private fun inputCheck(name: String, description: String): Boolean {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(description))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = view.findViewById<View>(R.id.editTextTextPersonName) as TextView
        val description = view.findViewById<View>(R.id.editTextTextPersonName2) as TextView

        view.findViewById<Button>(R.id.button)?.setOnClickListener {
            addNewIngredient()
        }
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
                foodId
            )
            mIngredientsViewModal.addIngredients(ingredients)
        }

        builder.show()
    }
}