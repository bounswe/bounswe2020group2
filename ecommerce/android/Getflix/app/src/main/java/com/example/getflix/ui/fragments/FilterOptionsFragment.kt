package com.example.getflix.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.databinding.FragmentFilterOptionsBinding
import com.example.getflix.ui.fragments.FilterOptionsFragmentDirections.Companion.actionFilterOptionsFragmentToSubcategoryFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class FilterOptionsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.bottom_nav!!.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentFilterOptionsBinding>(
                inflater, R.layout.fragment_filter_options,
                container, false)


        activity?.toolbar!!.toolbar_title.text = getString(R.string.filter_options)
        binding.priceSlider.value = 0.0f
        val args = FilterOptionsFragmentArgs.fromBundle(requireArguments())
        val brandsl = args.brands.toList()
        val vendorsl = args.vendors.toList()
        val subId = args.subId

        var rating: Float = 0F
        binding.ratingText.text = "0.0 +"
        binding.rating.onRatingBarChangeListener = object : RatingBar.OnRatingBarChangeListener {
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


        val checkedBrandItems = BooleanArray(brandsl.size) { false }
        var brands = arrayListOf<String>()
        binding.btnBrand.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_color)
                    .setTitle("Brands")
                    .setPositiveButton("Ok") { dialog, which ->
                        brands = arrayListOf<String>()
                        checkedBrandItems.forEachIndexed { index, b ->
                            if (b)
                                brands.add(brandsl[index])
                        }
                        if (brands.size == 0)
                            binding.brands.text = "None"
                        else
                            binding.brands.text = brands.joinToString()
                    }
                    .setNeutralButton("Cancel") { dialog, which ->
                        // Respond to neutral button press
                    }
                    .setMultiChoiceItems(brandsl.toTypedArray(), checkedBrandItems) { dialog, which, checked ->

                    }
                    .setIcon(R.drawable.menu_category)
                    .show()
        }

        var vendors = arrayListOf<String>()
        val checkedVendorItems = BooleanArray(vendorsl.size) { false }
        binding.btnVendor.setOnClickListener {

            MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_color)
                    .setTitle("Vendors")
                    .setPositiveButton("Ok") { dialog, which ->
                        vendors = arrayListOf<String>()
                        checkedVendorItems.forEachIndexed { index, b ->
                            if (b)
                                vendors.add(vendorsl[index])
                        }
                        if (vendors.size == 0)
                            binding.vendors.text = "None"
                        else
                            binding.vendors.text = vendors.joinToString()

                    }
                    .setNeutralButton("Cancel") { dialog, which ->
                        // Respond to neutral button press
                    }
                    .setMultiChoiceItems(vendorsl.toTypedArray(), checkedVendorItems) { dialog, which, checked ->

                    }
                    .setIcon(R.drawable.menu_category)
                    .show()
        }

        binding.complete.setOnClickListener {
            view?.findNavController()!!.navigate(actionFilterOptionsFragmentToSubcategoryFragment(subId, null,value.toString(),brands.toTypedArray(),
            vendors.toTypedArray(),rating.toString()))
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.bottom_nav!!.visibility = View.VISIBLE
    }


}