package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
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
        val products = arrayListOf( ProductModel(1,"Bag","100 TL",null,"Vıntage Bag"),
                ProductModel(1,"iPhone 7","4815 TL",null,"Best Phone"),
                ProductModel(1,"Pullover","36 TL",null,"Black Pullover"),
                ProductModel(1,"Notebook","32 TL",null,"Spiral Notebook"),
                ProductModel(1,"Pencil","13 TL",null,"Black Pencil"),
                ProductModel(1,"Skirt","30 TL",null,"Vıntage Skirt"),
                ProductModel(1,"T-Shirt","23 TL",null,"Vıntage T-Shirt"),
                ProductModel(1,"Book","20 TL",null,"Bestseller Book")   ,
                ProductModel(1,"T-Shirt","23 TL",null,"Black T-Shirt"),
                ProductModel(1,"Book","20 TL",null,"Bestseller Book"))
        val productListAdapter = FavoritesAdapter(products)
        recView.adapter = productListAdapter
        recView.setHasFixedSize(true)

        for(product in products) {
            viewModel.addProduct(product)
        }

        viewModel.productList.observe(viewLifecycleOwner, Observer{
            it?.let {
                productListAdapter.submitList(it)
            }
        })

        return binding.root
    }


}
