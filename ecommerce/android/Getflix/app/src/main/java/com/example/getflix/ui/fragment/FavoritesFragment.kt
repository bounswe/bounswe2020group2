package com.example.getflix.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.FragmentFavoritesBinding
import com.example.getflix.model.ProductModel
import com.example.getflix.ui.adapter.FavoritesAdapter


class FavoritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentFavoritesBinding>(
            inflater, R.layout.fragment_favorites,
            container, false
        )

        val recView = binding?.favoritesList as RecyclerView
        val products = arrayListOf(ProductModel(1,"a phone","Electronics"))
        val productListAdapter = FavoritesAdapter(products)
        recView.adapter = productListAdapter
        recView.setHasFixedSize(true)

        return binding.root
    }


}