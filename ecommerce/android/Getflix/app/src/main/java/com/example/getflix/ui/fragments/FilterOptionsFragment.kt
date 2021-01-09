package com.example.getflix.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.databinding.FragmentFilterOptionsBinding
import com.example.getflix.ui.adapters.SubCategoryAdapter
import com.example.getflix.ui.fragments.FilterOptionsFragmentDirections.Companion.actionFilterOptionsFragmentToSubcategoryFragment
import com.example.getflix.ui.viewmodels.SubCategoryViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class FilterOptionsFragment : Fragment() {

    private lateinit var viewModel: SubCategoryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.bottom_nav!!.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentFilterOptionsBinding>(
            inflater, R.layout.fragment_filter_options,
            container, false
        )

        val args = FilterOptionsFragmentArgs.fromBundle(requireArguments())
        val sub = args.subId
        val query = args.query
        val sortBy = args.sortBy
        val sortOrder = args.sortOrder

        var subId: Int? = null

        if(sub=="null")
            subId=null
        else if(sub!=null)
            subId = sub.toInt()

        val brandsl = arrayListOf<String>()
        val vendorsl = arrayListOf<String>()
        viewModel = ViewModelProvider(this).get(SubCategoryViewModel::class.java)
        viewModel.searchByFilter(query, subId, null, null, null, sortBy, sortOrder)

        viewModel.productList.observe(viewLifecycleOwner, {

            for (product in it!!) {
                if (!brandsl.contains(product.brand.id.toString() + " " + product.brand.name))
                    brandsl.add(product.brand.id.toString() + " " + product.brand.name)
                if (!vendorsl.contains(product.vendor.id.toString() + " " + product.vendor.name))
                    vendorsl.add(product.vendor.id.toString() + " " + product.vendor.name)
            }



            activity?.toolbar!!.toolbar_title.text = getString(R.string.filter_options)
            binding.priceSlider.value = 0.0f


            var rating: Float = 0F
            binding.ratingText.text = "0.0 +"
            binding.rating.onRatingBarChangeListener =
                object : RatingBar.OnRatingBarChangeListener {
                    override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
                        binding.ratingText.text = "$p1 +"
                        rating = p1
                    }
                }

            var value = 0.0
            binding.priceSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: Slider) {
                    // TODO("Not yet implemented")
                }

                override fun onStopTrackingTouch(slider: Slider) {
                    value = binding.priceSlider.value.toString().toDouble()
                    binding.price.text = "$value TL"
                }
            })


            var checkedBrandItem = 1
            var brandNames = arrayListOf<String>()
            println("SIZE " + brandsl.size)
            for (brand in brandsl) {
                var ind = brand.indexOf(" ")
                var tempbrand = brand.substring(ind + 1)
                brandNames.add(tempbrand)
            }
            var brand: String? = null
            var vendor: String? = null
            binding.btnBrand.setOnClickListener {
                MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_color)
                    .setTitle("Brands")
                    .setPositiveButton("Ok") { dialog, which ->
                        binding.brands.text = brandNames[checkedBrandItem]
                        brand = brandsl[checkedBrandItem].split(" ").toTypedArray()[0]
                    }
                    .setNeutralButton("Cancel") { dialog, which ->
                        // Respond to neutral button press
                    }
                    .setSingleChoiceItems(
                        brandNames.toTypedArray(),
                        checkedBrandItem
                    ) { dialog, which ->
                        checkedBrandItem = which
                    }
                    .setIcon(R.drawable.menu_category)
                    .show()
            }

            var checkedVendorItem = 1
            var vendorNames = arrayListOf<String>()
            for (vendor in vendorsl) {
                var ind = vendor.indexOf(" ")
                var tempvendor = vendor.substring(ind + 1)
                vendorNames.add(tempvendor)
            }

            binding.btnVendor.setOnClickListener {

                MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_color)
                    .setTitle("Vendors")
                    .setPositiveButton("Ok") { dialog, which ->
                        binding.vendors.text = vendorNames[checkedVendorItem]
                        vendor = vendorsl[checkedVendorItem].split(" ").toTypedArray()[0]
                    }
                    .setNeutralButton("Cancel") { dialog, which ->
                        // Respond to neutral button press
                    }
                    .setSingleChoiceItems(
                        vendorNames.toTypedArray(),
                        checkedVendorItem
                    ) { dialog, which ->
                        checkedVendorItem = which
                    }
                    .setIcon(R.drawable.menu_category)
                    .show()
            }

            binding.complete.setOnClickListener {
                view?.findNavController()!!.navigate(
                    actionFilterOptionsFragmentToSubcategoryFragment(
                        subId.toString(), query, value.toString(), brand,
                        vendor, rating.toString()
                    )
                )
            }


        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.bottom_nav!!.visibility = View.VISIBLE
    }


}