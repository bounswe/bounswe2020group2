package com.example.getflix.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.FragmentProductBinding
import com.example.getflix.ui.adapters.ImageAdapter
import com.example.getflix.ui.adapters.RecommenderAdapter
import com.example.getflix.ui.viewmodels.ProductViewModel
import kotlinx.android.synthetic.main.fragment_product.view.*
import me.relex.circleindicator.CircleIndicator2


class ProductFragment: Fragment() {
    private lateinit var productViewModel: ProductViewModel
    private lateinit var binding: FragmentProductBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
         binding = DataBindingUtil.inflate<FragmentProductBinding>(
                inflater, R.layout.fragment_product,
                container, false
        )


        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        val recommenderAdapter = RecommenderAdapter()
        val imageAdapter = ImageAdapter()
        binding.lifecycleOwner = this

        binding.recommendedProducts.adapter = recommenderAdapter
        var layoutManagerForRecommenderAdapter: RecyclerView.LayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recommendedProducts.layoutManager = layoutManagerForRecommenderAdapter

        binding.images.adapter = imageAdapter
        val layoutManagerForImageAdapter = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.images.layoutManager = layoutManagerForImageAdapter

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding.images)

        val indicator: CircleIndicator2 = binding.circleIndicator
        indicator.attachToRecyclerView(binding.images, pagerSnapHelper)


        binding.like.setOnClickListener {
            productViewModel.onLikeClick()
        }
        binding.imageView7.setOnClickListener {
            val scrollView = binding.scrollView
            val targetView = binding.detailsTitle
            scrollView.scrollTo(0,targetView.y.toInt())
        }
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
            recommenderAdapter.submitList(it)
        })
        productViewModel.imageURLs.observe(viewLifecycleOwner, Observer {
            imageAdapter.submitList(it)
        })
        productViewModel.isLiked.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.like.setImageResource(R.drawable.ic_filled_like)
            }else{
                binding.like.setImageResource(R.drawable.ic_like)
            }
        })
        return binding.root
    }

    fun setVendorRating(rating : Float){
        if (rating > 1){
            binding.star1.setImageResource(R.drawable.ic_filled_star)
        }
        if (rating > 2){
            binding.star2.setImageResource(R.drawable.ic_filled_star)
        }
        if (rating > 3){
            binding.star3.setImageResource(R.drawable.ic_filled_star)
        }
        if (rating > 4){
            binding.star4.setImageResource(R.drawable.ic_filled_star)
        }
        if (rating.toInt() == 5){
            binding.star5.setImageResource(R.drawable.ic_filled_star)
        }
    }

    fun setProductRating(rating : Float){
        if (rating > 1){
            binding.star1.setImageResource(R.drawable.ic_filled_star)
        }
        if (rating > 2){
            binding.star2.setImageResource(R.drawable.ic_filled_star)
        }
        if (rating > 3){
            binding.star2.setImageResource(R.drawable.ic_filled_star)
        }
        if (rating > 4){
            binding.star2.setImageResource(R.drawable.ic_filled_star)
        }
        if (rating.toInt() == 5){
            binding.star2.setImageResource(R.drawable.ic_filled_star)
        }
    }

}
