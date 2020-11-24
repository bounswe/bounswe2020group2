package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.FragmentFavoritesBinding
import com.example.getflix.models.ProductModel
import com.example.getflix.ui.adapters.FavoritesAdapter
import com.example.getflix.ui.viewmodels.FavoritesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class FavoritesFragment : Fragment() {

    private lateinit var viewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentFavoritesBinding>(
            inflater, R.layout.fragment_favorites,
            container, false
        )

        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        binding.viewmodel = FavoritesViewModel()
        activity?.toolbar!!.toolbar_title.text = getString(R.string.favorites)
        val recView = binding?.favoritesList as RecyclerView
        val products = arrayListOf(ProductModel(1,"iPhone",2,null,null),
            ProductModel(1,"Bag",2,null,null))
        val productListAdapter = FavoritesAdapter(products)
        recView.adapter = productListAdapter
        recView.setHasFixedSize(true)

        for(product in products) {
            viewModel.addProduct(product)
        }

        viewModel.productList.observe(viewLifecycleOwner, {
            it?.let {
                productListAdapter.submitList(it)
            }
        })

        return binding.root
    }


}