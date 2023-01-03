package com.example.myapplication

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.IngredientsData.Ingredients
import com.example.myapplication.IngredientsData.IngredientsViewModal
import com.example.myapplication.recycleStuff.ViewIngredientAdapter
import kotlinx.android.synthetic.main.fragment_first.view.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import kotlinx.android.synthetic.main.fragment_view.view.*


class ViewFragment : Fragment() {
    private val args by navArgs<ViewFragmentArgs>()
    private lateinit var mIngredientsViewModal: IngredientsViewModal
    private lateinit var listy: ArrayList<Ingredients>

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragmentma
        val view = inflater.inflate(R.layout.fragment_view, container, false)

        listy = ArrayList()

        activity?.setTitle("View Recipe")

        view.textView14.setText(args.currentFood.name)
        view.textView15.setText(args.currentFood.description)
        view.textView17.setText(args.currentFood.method)
        view.textView17.setMovementMethod(ScrollingMovementMethod())
        view.ratingBar.setRating(args.currentFood.rating)

        val adapter = ViewIngredientAdapter()
        val recyclerView = view.recycler_view_view
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mIngredientsViewModal = ViewModelProvider(this).get(IngredientsViewModal::class.java)
        mIngredientsViewModal.readAllData.observe(viewLifecycleOwner, Observer { ingredients ->
            for (i in ingredients) {
                if (i.foodId == args.currentFood.foodId) {
                    listy.add(i)
                }
            }
            adapter.setData(listy)
        })

        return view
    }


}