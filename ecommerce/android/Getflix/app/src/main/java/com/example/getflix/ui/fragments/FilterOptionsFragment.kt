package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.getflix.R
import com.example.getflix.databinding.FragmentFilterOptionsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.slider.RangeSlider
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
        binding.priceSlider.setValues(0.0f, 100.0f)

        var rating = -1
        binding.rg.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { _, checkedId ->
                    when (checkedId) {
                        R.id.rb_0 -> rating = 0
                        R.id.rb_1 -> rating = 1
                        R.id.rb_2 -> rating = 2
                        R.id.rb_3 -> rating = 3
                        R.id.rb_4 -> rating = 4
                    }
                })

        var values = arrayListOf<Int>()
        binding.priceSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                // TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                values = arrayListOf<Int>()
                for (price in binding.priceSlider.values)
                    values.add(price.toInt())
                binding.price.text = values[0].toString() + " - " + values[1].toString() + " TL"
            }
        })


        val checkedBrandItems = booleanArrayOf(false, false, false, false, false,
                false, false, false, false, false, false)
        binding.btnBrand.setOnClickListener {
            val multiItems = arrayOf("Brand 1", "Brand 2", "Brand 3", "Brand 4", "Brand 5",
                    "Brand 6", "Brand 7", "Brand 8", "Brand 9", "Brand 10", "Brand 11")


            var brands = arrayListOf<String>()
            MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_color)
                    .setTitle("Brands")
                    .setPositiveButton("Ok") { dialog, which ->
                        brands = arrayListOf<String>()
                        checkedBrandItems.forEachIndexed { index, b ->
                            if (b)
                                brands.add(multiItems[index])
                        }
                        if (brands.size == 0)
                            binding.brands.text = "None"
                        else
                            binding.brands.text = brands.joinToString()
                    }
                    .setNeutralButton("Cancel") { dialog, which ->
                        // Respond to neutral button press
                    }
                    .setMultiChoiceItems(multiItems, checkedBrandItems) { dialog, which, checked ->
                        // Respond to item chosen
                    }
                    .setIcon(R.drawable.menu_category)
                    .show()
        }

        var vendors = arrayListOf<String>()
        val checkedVendorItems = booleanArrayOf(false, false, false, false, false,
                false, false, false, false, false, false)
        binding.btnVendor.setOnClickListener {
            val multiItems = arrayOf("Vendor 1", "Vendor 2", "Vendor 3", "Vendor 4", "Vendor 5",
                    "Vendor 6", "Vendor 7", "Vendor 8", "Vendor 9", "Vendor 10", "Vendor 11")


            MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_color)
                    .setTitle("Vendors")
                    .setPositiveButton("Ok") { dialog, which ->
                        vendors = arrayListOf<String>()
                        checkedVendorItems.forEachIndexed { index, b ->
                            if (b)
                                vendors.add(multiItems[index])
                        }
                        if (vendors.size == 0)
                            binding.vendors.text = "None"
                        else
                            binding.vendors.text = vendors.joinToString()

                    }
                    .setNeutralButton("Cancel") { dialog, which ->
                        // Respond to neutral button press
                    }
                    .setMultiChoiceItems(multiItems, checkedVendorItems) { dialog, which, checked ->

                    }
                    .setIcon(R.drawable.menu_category)
                    .show()
        }

        binding.complete.setOnClickListener {
            // navController.popBackStack()
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.bottom_nav!!.visibility = View.VISIBLE
    }


}