 package com.example.myapplication

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.appcompat.widget.SearchView;
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.Food
import com.example.myapplication.data.FoodViewModel
import com.example.myapplication.recycleStuff.FoodAdapter
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_first.view.*
import java.util.*
import kotlin.collections.ArrayList


 /**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

//    private var mRecyclerView: RecyclerView? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
//    private var model: Communicator? = null
    private lateinit var mFoodViewModel: FoodViewModel
    var arrayList = ArrayList<Food>()
    var displayList = ArrayList<Food>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        displayList = arrayListOf()
        activity?.setTitle("Home")

        val adapter = FoodAdapter()
        val recyclerView = view.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mFoodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)
        mFoodViewModel.readAllData.observe(viewLifecycleOwner, Observer { food ->
            arrayList = ArrayList(food)
            displayList.addAll(arrayList)
            adapter.setData(displayList)
        })

        setHasOptionsMenu(true)
        return view
    }

     override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

         inflater.inflate(R.menu.menu_main, menu)
         val menuItem = menu!!.findItem(R.id.search)
         var recyclerViewTest = view?.recyclerView

         if (menuItem != null) {
             val searchView = menuItem.actionView as SearchView

             searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                 override fun onQueryTextSubmit(query: String?): Boolean {
                     return true
                 }

                 override fun onQueryTextChange(newText: String?): Boolean {
                     if (newText!!.isNotEmpty()) {

                         displayList.clear()
                         val search = newText.toLowerCase(Locale.getDefault())
                         arrayList.forEach {

                             if (it.name.toLowerCase(Locale.getDefault()).contains(search)) {
                                 displayList.add(it)
                             }

                             recyclerViewTest?.adapter!!.notifyDataSetChanged()
                         }
                     } else {
                         displayList.clear()
                         displayList.addAll(arrayList)
                         recyclerViewTest?.adapter!!.notifyDataSetChanged()
                     }

                     return true
                 }
             })
         }

         return super.onCreateOptionsMenu(menu, inflater)
     }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.addIngredientsButton).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_ThirdFragment)
        }

    }
}