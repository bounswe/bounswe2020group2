package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.getflix.R
import com.example.getflix.databinding.FragmentNByThreeGridBinding
import com.example.getflix.ui.adapters.VendorPageProductAdapter
import com.example.getflix.ui.adapters.VendorPageProductSmallAdapter
import com.example.getflix.ui.viewmodels.NByThreeViewModel
import kotlinx.android.synthetic.main.activity_main.*


class NByThreeGridFragment : Fragment() {

    private lateinit var binding: FragmentNByThreeGridBinding
    private lateinit var nByThreeViewModel: NByThreeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_n_by_three_grid,
            container, false
        )

        activity?.toolbar_lay!!.visibility = View.GONE
        binding.lifecycleOwner = this
        nByThreeViewModel = ViewModelProvider(this).get(NByThreeViewModel::class.java)

        val adapter = VendorPageProductSmallAdapter()
        binding.vendorProductsInThreeColumns.adapter = adapter
        val layoutManager = GridLayoutManager(requireContext(),3)
        binding.vendorProductsInThreeColumns.layoutManager = layoutManager

        nByThreeViewModel.products.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                adapter.submitList(it)
            }
        })

        return binding.root
    }

}