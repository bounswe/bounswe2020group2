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
    private var filter = false
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
        val subId = args.subId
        val brandsl = args.brands?.toList()
        val vendorsl = args.vendors?.toList()
        val rating = args.rating?.toFloat()
        val prices = args.prices?.toList()
        if (brandsl != null)
            println("xxx" + brandsl.toString())
        if (vendorsl != null)
            println("xxx" + vendorsl.toString())
        if (rating != null)
            println("xxx" + rating.toString())
        if (prices != null)
            println("xxx" + prices.toString())
        viewModel = ViewModelProvider(this).get(SubCategoryViewModel::class.java)
        viewModel.searchBySubcategory(subId)
        val recView = binding?.productList as RecyclerView

        val manager = GridLayoutManager(activity, 2)
        recView.layoutManager = manager

        val brands = arrayListOf<String>()
        val vendors = arrayListOf<String>()
        viewModel.productList.observe(viewLifecycleOwner, {
            val productListAdapter = SubCategoryAdapter(it!!, this)
            recView.adapter = productListAdapter
            recView.setHasFixedSize(true)
            for (product in it!!) {
                if (!brands.contains(product.brand.name))
                    brands.add(product.brand.name)
                if (!vendors.contains(product.vendor.name))
                    vendors.add(product.vendor.name)
            }
            println(brands)
            println(vendors)
        })


        binding.btnFilter.setOnClickListener {
            filter = true
            view?.findNavController()!!.navigate(
                actionSubcategoryFragmentToFilterOptionsFragment(
                    subId, brands.toTypedArray(), vendors.toTypedArray()
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
                    viewModel.sort(subId, "best-sellers", "decreasing")
                } else if (position == 2) {
                    viewModel.sort(subId, "newest-arrivals", "decreasing")
                } else if (position == 3) {
                    viewModel.sort(subId, "price", "increasing")
                } else if (position == 4) {
                    viewModel.sort(subId, "price", "decreasing")
                } else if (position == 5) {
                    viewModel.sort(subId, "average-customer-review", "decreasing")
                } else if (position == 6) {
                    viewModel.sort(subId, "number-of-comments", "decreasing")
                }

            }

        }

        return binding.root
    }


}