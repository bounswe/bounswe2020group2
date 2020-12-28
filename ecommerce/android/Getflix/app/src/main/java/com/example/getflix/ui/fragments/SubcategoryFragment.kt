package com.example.getflix.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.FragmentSubcategoryBinding
import com.example.getflix.ui.adapters.SubCategoryAdapter
import com.example.getflix.ui.fragments.SubcategoryFragmentDirections.Companion.actionSubcategoryFragmentToFilterOptionsFragment
import com.example.getflix.ui.viewmodels.SubCategoryViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_subcategory.*


class SubcategoryFragment : Fragment() {

    private lateinit var viewModel: SubCategoryViewModel
    private var filter = false
    private lateinit var binding: FragmentSubcategoryBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_subcategory,
                container, false
        )

        val args = SubcategoryFragmentArgs.fromBundle(requireArguments())
        val subId = args.subId
        viewModel = ViewModelProvider(this).get(SubCategoryViewModel::class.java)
        viewModel.searchBySubcategory(subId)
        //binding.viewmodel = SubCategoryViewModel()
        val recView = binding?.productList as RecyclerView

        val manager = GridLayoutManager(activity, 2)
        recView.layoutManager = manager

        viewModel.productList.observe(viewLifecycleOwner, {
            val productListAdapter = SubCategoryAdapter(it!!,this)
            recView.adapter = productListAdapter
            recView.setHasFixedSize(true)
        })


        /*for (product in products) {
            viewModel.addProduct(product)
        }*/

        binding.btnFilter.setOnClickListener {
            filter = true
            view?.findNavController()!!.navigate(actionSubcategoryFragmentToFilterOptionsFragment())
        }

        /*viewModel.productList.observe(viewLifecycleOwner, {
            it?.let {
                productListAdapter.submitList(it)
            }
        })*/

        val sorts = resources.getStringArray(R.array.SortBy)
        val spinner = binding.spinner
        val adapter = ArrayAdapter(activity?.applicationContext!!, android.R.layout.simple_spinner_dropdown_item, sorts)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.sort(parent?.getItemAtPosition(position).toString())

            }

        }

        return binding.root
    }


}