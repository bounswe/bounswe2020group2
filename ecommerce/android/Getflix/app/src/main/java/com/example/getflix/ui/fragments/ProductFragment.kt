package com.example.getflix.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.FragmentProductBinding
import com.example.getflix.ui.adapters.RecommenderAdapter
import com.example.getflix.ui.viewmodels.ProductViewModel
import kotlinx.android.synthetic.main.fragment_product.*


class ProductFragment : Fragment() {
    private lateinit var productViewModel: ProductViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentProductBinding>(
                inflater, R.layout.fragment_product,
                container, false
        )


        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        val adapter = RecommenderAdapter()

        binding.lifecycleOwner = this
        binding.recommendedProducts.adapter = adapter
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        binding.recommendedProducts.layoutManager = layoutManager
        binding.decrease.setOnClickListener {
            productViewModel.decreaseAmount()
        }
        binding.increase.setOnClickListener {
            productViewModel.increaseAmount()
        }
        productViewModel.amount.observe(viewLifecycleOwner, Observer {
            binding.amount.text = it?.toString()
        })
        productViewModel.recommendedProducts.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        return binding.root
    }


}
