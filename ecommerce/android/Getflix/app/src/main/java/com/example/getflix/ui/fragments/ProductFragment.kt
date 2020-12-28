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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.FragmentProductBinding
import com.example.getflix.doneAlert
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
            //productViewModel.addToShoppingCart(1, args.productId)
            productViewModel.addCustomerCartProduct(binding.amount.text.toString().toInt(), args.productId)
        }

        productViewModel.navigateBack.observe(viewLifecycleOwner, {
            if(it) {
                doneAlert(this, "Product is added to your shopping cart!", ::navigateBack)
                productViewModel.resetNavigate()
            }
        })

        productViewModel.amount.observe(viewLifecycleOwner, Observer {
            binding.amount.text = it?.toString()
            binding.amount.text = it?.toString()
        })
        productViewModel.recommendedProducts.observe(viewLifecycleOwner, Observer {
            recommenderAdapter.submitList(it)
        })


        productViewModel.reviews.observe(viewLifecycleOwner, Observer{
            if (it!= null) {
                commentAdapter.submitList(it)
            }
        })


        productViewModel.product.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                productViewModel.getProductReviews()
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

    private fun navigateBack() {
        view?.findNavController()!!.popBackStack()
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
