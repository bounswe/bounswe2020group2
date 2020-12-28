package com.example.getflix.ui.fragments


import android.graphics.Paint
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
import com.example.getflix.ui.adapters.CommentAdapter
import com.example.getflix.ui.adapters.ImageAdapter
import com.example.getflix.ui.adapters.RecommenderAdapter
import com.example.getflix.ui.viewmodels.ProductViewModel
import me.relex.circleindicator.CircleIndicator2


class ProductFragment : Fragment() {
    private lateinit var binding: FragmentProductBinding
    private lateinit var productViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentProductBinding>(
            inflater, R.layout.fragment_product,
            container, false
        )
        val args = ProductFragmentArgs.fromBundle(requireArguments())
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        productViewModel.getProduct(args.productId)
        productViewModel.getProductReviews(args.productId)

        val recommenderAdapter = RecommenderAdapter()
        val imageAdapter = ImageAdapter()
        val commentAdapter = CommentAdapter()

        binding.lifecycleOwner = this

        binding.recommendedProducts.adapter = recommenderAdapter
        var layoutManagerForRecommenderAdapter: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recommendedProducts.layoutManager = layoutManagerForRecommenderAdapter

        binding.images.adapter = imageAdapter
        val layoutManagerForImageAdapter =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.images.layoutManager = layoutManagerForImageAdapter

        binding.comments.adapter = commentAdapter
        val layoutForCommentAdapter = LinearLayoutManager(activity)
        binding.comments.layoutManager = layoutForCommentAdapter

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding.images)

        val indicator: CircleIndicator2 = binding.circleIndicator
        indicator.attachToRecyclerView(binding.images, pagerSnapHelper)

        binding.like.setOnClickListener {
            productViewModel.onLikeClick()
        }
        binding.imageView7.setOnClickListener {
            val scrollView = binding.scrollView
            val targetView = binding.longDescription
            scrollView.scrollTo(0, targetView.top)
        }
        binding.imageView6.setOnClickListener {
            val scrollView = binding.scrollView
            val targetView = binding.comments
            scrollView.scrollTo(0, targetView.top)
        }
        binding.decrease.setOnClickListener {
            productViewModel.decreaseAmount()
        }

        binding.increase.setOnClickListener {
            productViewModel.increaseAmount()
        }
        binding.addToCart.setOnClickListener {
            productViewModel.addToShoppingCart(1, args.productId)
        }

        productViewModel.amount.observe(viewLifecycleOwner, Observer {
            binding.amount.text = it?.toString()
            binding.amount.text = it?.toString()
        })
        productViewModel.recommendedProducts.observe(viewLifecycleOwner, Observer {
            recommenderAdapter.submitList(it)
        })

        var comments = arrayListOf<String>(
            
        )


        productViewModel.reviews.observe(viewLifecycleOwner, {
            for(review in it) {
                comments.add(review.comment)
                println(review.comment)
            }
            commentAdapter.submitList(comments)
        })


        productViewModel.product.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.product = it
                binding.brand.text = it.brand.name
                binding.productName.text = it.name
                imageAdapter.submitList(it.images)
                binding.oldPrice.text = it.price.toString() + " TL"
                binding.oldPrice.setPaintFlags(binding.oldPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
                binding.price.text = it.priceDiscounted.toString() + " TL"
                binding.longDescription.text = it.long_description
                binding.shortDescription.text = it.short_description
                binding.rating.text = it.rating.toString()
                binding.totalRating.text = "(" + it.rating_count.toString() + ")"
                binding.vendorDetail.text = it.vendor.name
                binding.productCategory.text = it.category.name
                binding.productSubcategory.text = it.subcategory.name
                binding.circleIndicator.createIndicators(it.images.size, 0)
                setProductRating(it.rating)
                commentAdapter.submitList(comments)
            }

        })
        productViewModel.isLiked.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.like.setImageResource(R.drawable.ic_filled_like)
            } else {
                binding.like.setImageResource(R.drawable.ic_like)
            }
        })
        return binding.root

    }

    fun setProductRating(rating: Double) {
        if (rating >= 1) {
            binding.star1.setImageResource(R.drawable.ic_filled_star)
        }
        if (rating >= 2) {
            binding.star2.setImageResource(R.drawable.ic_filled_star)
        }
        if (rating >= 3) {
            binding.star3.setImageResource(R.drawable.ic_filled_star)
        }
        if (rating >= 4) {
            binding.star4.setImageResource(R.drawable.ic_filled_star)
        }
        if (rating.toInt() == 5) {
            binding.star5.setImageResource(R.drawable.ic_filled_star)
        }
    }

}
