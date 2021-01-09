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
import com.example.getflix.models.BrandModel
import com.example.getflix.ui.adapters.SubCategoryAdapter
import com.example.getflix.ui.fragments.SubcategoryFragmentDirections.Companion.actionSubcategoryFragmentToFilterOptionsFragment
import com.example.getflix.ui.viewmodels.SubCategoryViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_subcategory.*


class SubcategoryFragment : Fragment() {

    private lateinit var viewModel: SubCategoryViewModel
    private lateinit var binding: FragmentSubcategoryBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_subcategory,
            container, false
        )

        val args = SubcategoryFragmentArgs.fromBundle(requireArguments())
        viewModel = ViewModelProvider(this).get(SubCategoryViewModel::class.java)

        val query = args.query
        var sub = args.subId
        val brand = args.brand
        val vendor = args.vendor
        val rating = args.rating?.toFloat()
        val price = args.prices
        var sortBy: String? = null
        var sortOrder: String? = null
        var subId: Int? = null

        if(sub=="null")
            subId=null
        else if(sub!=null)
            subId = sub.toInt()


        viewModel.searchByFilter(
            query,
            subId,
            vendor?.toInt(),
            rating?.toDouble(),
            price?.toDouble(),
            null,
            null
        )



        //viewModel.searchBySubcategory(subId)
        val recView = binding?.productList as RecyclerView

        val manager = GridLayoutManager(activity, 2)
        recView.layoutManager = manager


        viewModel.productList.observe(viewLifecycleOwner, {
            val productListAdapter = SubCategoryAdapter(it!!, this)
            recView.adapter = productListAdapter
            recView.setHasFixedSize(true)

        })


        binding.btnFilter.setOnClickListener {
            view?.findNavController()!!.navigate(
                actionSubcategoryFragmentToFilterOptionsFragment(
                    subId.toString(),query,sortBy,sortOrder
                )
            )
        }


        val sorts = resources.getStringArray(R.array.SortBy)
        val spinner = binding.spinner
        val adapter = ArrayAdapter(
            activity?.applicationContext!!,
            android.R.layout.simple_spinner_dropdown_item,
            sorts
        )
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 1) {
                    sortBy = "best-sellers"
                    sortOrder = "decreasing"
                    viewModel.searchByFilter(
                        query,
                        subId,
                        null,
                        null,
                        null,
                        sortBy,
                        sortOrder
                    )
                } else if (position == 2) {
                    sortBy = "newest-arrivals"
                    sortOrder = "decreasing"
                    viewModel.searchByFilter(
                        query,
                        subId,
                        null,
                        null,
                        null,
                        sortBy,
                        sortOrder
                    )
                } else if (position == 3) {
                    sortBy = "price"
                    sortOrder = "increasing"
                    viewModel.searchByFilter(query, subId, null, null, null, sortBy, sortOrder)
                } else if (position == 4) {
                    sortBy = "price"
                    sortOrder = "decreasing"
                    viewModel.searchByFilter(query, subId, null, null, null, sortBy, sortOrder)
                } else if (position == 5) {
                    sortBy = "average-customer-review"
                    sortOrder = "decreasing"
                    viewModel.searchByFilter(
                        query,
                        subId,
                        null,
                        null,
                        null,
                        sortBy,
                        sortOrder
                    )
                } else if (position == 6) {
                    sortBy = "number-of-comments"
                    sortOrder = "decreasing"
                    viewModel.searchByFilter(
                        query,
                        subId,
                        null,
                        null,
                        null,
                        sortBy,
                        sortOrder
                    )
                }

            }

        }

        return binding.root
    }


}