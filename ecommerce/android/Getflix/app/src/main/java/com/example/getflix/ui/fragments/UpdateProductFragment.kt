package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.databinding.FragmentUpdateProductBinding
import com.example.getflix.databinding.FragmentVendorHomeBinding
import com.example.getflix.doneAlert
import com.example.getflix.models.PhoneModel
import com.example.getflix.service.requests.AddressUpdateRequest
import com.example.getflix.service.requests.VendorProUpdateRequest
import com.example.getflix.ui.viewmodels.AddressViewModel
import com.example.getflix.ui.viewmodels.VendorHomeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class UpdateProductFragment : Fragment() {

    private lateinit var binding: FragmentUpdateProductBinding
    private lateinit var viewModel: VendorHomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update_product,
                container, false)
        activity?.toolbar!!.toolbar_title.text = "Update Product"

        val args = UpdateProductFragmentArgs.fromBundle(requireArguments())
        val product = args.product
        viewModel = ViewModelProvider(this).get(VendorHomeViewModel::class.java)

        binding.editableId.setText(product.id.toString())
        binding.editableName.setText(product.name)
        binding.editablePrice.setText(product.price.toString())
        binding.editableStockamount.setText(product.stock_amount.toString())
        binding.editableShortdescription.setText(product.short_description)
        binding.editableLongdescription.setText(product.long_description)
        binding.editableDiscount.setText(product.discount.toString())
        binding.editableBrandid.setText(product.brand.id.toString())
        binding.editableSubcategoryid.setText(product.subcategory.id.toString())


        binding.btnAdd.setOnClickListener {
            val updateRequest = VendorProUpdateRequest(binding.editableId.text.toString().toInt(),
                binding.editableName.text.toString(),binding.editablePrice.text.toString().toDouble(),
                binding.editableStockamount.text.toString().toInt(),binding.editableShortdescription.text.toString(),
                binding.editableLongdescription.text.toString(),binding.editableDiscount.text.toString().toDouble(),
                binding.editableBrandid.text.toString().toInt(),binding.editableSubcategoryid.text.toString().toInt())
           println(binding.editableLongdescription.text.toString())
            viewModel.updateVendorProduct(updateRequest)
        }

        viewModel.navigateBack.observe(viewLifecycleOwner, {
            if(it) {
                doneAlert(this, "Address is updated successfully", ::navigateBack)
                viewModel.resetNavigate()
            }
        })

        return binding.root
    }

    private fun navigateBack() {
        view?.findNavController()?.popBackStack()
    }


}