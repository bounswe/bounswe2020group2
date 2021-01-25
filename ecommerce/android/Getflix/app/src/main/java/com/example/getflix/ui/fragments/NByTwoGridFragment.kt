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
import com.example.getflix.databinding.FragmentNByTwoGridBinding
import com.example.getflix.ui.adapters.VendorPageProductAdapter
import com.example.getflix.ui.viewmodels.NByTwoViewModel
import kotlinx.android.synthetic.main.activity_main.*


class NByTwoGridFragment : Fragment() {

    private lateinit var binding: FragmentNByTwoGridBinding
    private lateinit var nByTwoViewModel: NByTwoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.toolbar_lay!!.visibility = View.GONE
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_n_by_two_grid,
            container, false
        )
        binding.lifecycleOwner = this
        nByTwoViewModel = ViewModelProvider(this).get(NByTwoViewModel::class.java)

        val adapter = VendorPageProductAdapter()
        binding.vendorProductsInTwoColumns.adapter = adapter
        val layoutManager = GridLayoutManager(requireContext(),2)
        binding.vendorProductsInTwoColumns.layoutManager = layoutManager
        nByTwoViewModel.products.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                adapter.submitList(it)
            }
        })

        return binding.root
    }

}