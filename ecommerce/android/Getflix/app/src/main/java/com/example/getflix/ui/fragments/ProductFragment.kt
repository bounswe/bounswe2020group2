package com.example.getflix.ui.fragments


import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Paint
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.*
import com.example.getflix.activities.MainActivity
import com.example.getflix.databinding.FragmentProductBinding
import com.example.getflix.doneAlert
import com.example.getflix.service.requests.CreateListRequest
import com.example.getflix.ui.adapters.CommentAdapter
import com.example.getflix.ui.adapters.ImageAdapter
import com.example.getflix.ui.adapters.RecommenderAdapter
import com.example.getflix.ui.fragments.ProductFragmentDirections.Companion.actionProductFragmentToUpdateProductFragment
import com.example.getflix.ui.fragments.ProductFragmentDirections.Companion.actionProductFragmentToVendorPageFragment
import com.example.getflix.ui.viewmodels.ListViewModel
import com.example.getflix.ui.viewmodels.ProductViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText


import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import me.relex.circleindicator.CircleIndicator2


class ProductFragment : Fragment() {
    private lateinit var binding: FragmentProductBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var listViewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_product,
            container, false
        )
        val args = ProductFragmentArgs.fromBundle(requireArguments())
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        productViewModel.getProduct(args.productId)

        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel.getCustomerLists()

        activity?.toolbar_lay!!.visibility = View.GONE

        val recommenderAdapter = RecommenderAdapter()
        val imageAdapter = ImageAdapter()
        val commentAdapter = CommentAdapter()

        if(MainActivity.StaticData.user!!.role=="VENDOR") {
            binding.addToCart.setImageResource(R.drawable.ic_delete_pro)
            binding.buyNow.text = "UPDATE"
            binding.save.visibility = View.INVISIBLE
            binding.recommendedProducts.visibility = View.GONE
            binding.reviewSection.visibility=View.GONE
            binding.increase.visibility = View.GONE
            binding.amount.visibility = View.GONE
            binding.decrease.visibility = View.GONE
        } else {
            binding.addToCart.setImageResource(R.drawable.ic_black_cart)
            binding.buyNow.text = "BUY NOW"
            binding.save.visibility = View.VISIBLE
            binding.recommendedProducts.visibility = View.VISIBLE
            binding.reviewSection.visibility = View.VISIBLE
            binding.increase.visibility = View.VISIBLE
            binding.amount.visibility = View.VISIBLE
            binding.decrease.visibility = View.VISIBLE
        }

        binding.buyNow.setOnClickListener {
            if(MainActivity.StaticData.user!!.role!="CUSTOMER") {
                if(productViewModel.product.value!=null)
                view?.findNavController()!!.navigate(actionProductFragmentToUpdateProductFragment(productViewModel.product.value!!))
            }
        }

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
        var listNames = arrayListOf<String>()
        var listIds = arrayListOf<Int>()
        var checkedList = 0
        binding.save.setOnClickListener {
            //productViewModel.onSaveClick()
            listViewModel.listOfLists.observe(viewLifecycleOwner, Observer {
                for(list in it.lists) {
                    if(!listNames.contains(list.name)) {
                        listNames.add(list.name)
                        listIds.add(list.id)
                    }
                }

                var prev = MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_color)
                    prev.setTitle("Select a List")
                    prev.setSingleChoiceItems(
                        listNames.toTypedArray(),
                        checkedList
                    ) { dialog, which ->
                        checkedList = which
                    }
                    prev.setPositiveButton("Ok") { dialog, which ->
                        dialog.dismiss()
                        listViewModel.addProductToList(listIds[checkedList],args.productId)
                        doneAlert(this,"The product is added to your list successfully!",null)
                    }
                    prev.setIcon(R.drawable.accepted_list)
                    prev.setNegativeButton("Cancel") { dialog, which ->
                    }
                    prev.setNeutralButton("Add New List") { dialog1, which ->
                        var dialog = AlertDialog.Builder(context,R.style.MaterialAlertDialog_color)
                        var dialogView = layoutInflater.inflate(R.layout.custom_dialog,null)
                        var edit = dialogView.findViewById<TextInputEditText>(R.id.name)
                        dialog.setView(dialogView)
                        dialog.setCancelable(true)
                        dialog.setIcon(R.drawable.ic_pencil)
                        dialog.setTitle("Add A List")
                        dialog.setNegativeButton("Cancel") { dialogInterface: DialogInterface, i: Int -> }
                        dialog.setPositiveButton("Create") { dialogInterface: DialogInterface, i: Int ->
                            hideKeyboard(requireActivity())
                            listViewModel.createList(CreateListRequest(edit.text.toString()))
                        }
                        dialog.show()
                    }
                  prev.show()

            })
        }

        binding.imageView7.setOnClickListener {
            val scrollView = binding.scrollView
            val targetView = binding.detailsTitle
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



        binding.vendorDetail.setOnClickListener {
            if(MainActivity.StaticData.user!!.role!="VENDOR") {
                val vendor = productViewModel.product.value!!.vendor
                view?.findNavController()!!
                    .navigate(actionProductFragmentToVendorPageFragment(vendor))
            }
        }

        binding.increase.setOnClickListener {
            productViewModel.increaseAmount()
        }
        binding.addToCart.setOnClickListener {
            if(MainActivity.StaticData.isVisitor) {
                infoAlert(this, "You should be logged in to add product to your shopping cart")
            } else if(MainActivity.StaticData.user!!.role=="CUSTOMER"){
                productViewModel.addCustomerCartProduct(
                    binding.amount.text.toString().toInt(),
                    args.productId
                )
            } else {
                println("iddd " + args.productId)
                productViewModel.deleteProduct(args.productId)
            }
        }

        productViewModel.navigateBack.observe(viewLifecycleOwner, Observer{
            if(it) {
                doneAlert(this, "Product is added to your shopping cart!", ::navigateBack)
                productViewModel.resetNavigate()
            }
        })

        productViewModel.deletePro.observe(viewLifecycleOwner, Observer{
            if(it) {
                doneAlert(this, "Product is deleted successfully!", ::navigateBack)
                productViewModel.resetDeletePro()
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

                val ratingString = it.rating.toString()
                if(ratingString.length > 4)
                    binding.rating.text = ratingString.substring(0,4)
                else
                    binding.rating.text = ratingString

                binding.totalRating.text = "(" + it.rating_count.toString() + ")"
                binding.vendorDetail.paintFlags = Paint.UNDERLINE_TEXT_FLAG;
                binding.vendorDetail.text = it.vendor.name
                binding.productCategory.text = it.category.name
                binding.productSubcategory.text = it.subcategory.name
                binding.circleIndicator.createIndicators(it.images.size, 0)
                setProductRating(it.rating)
            }

        })
        productViewModel.isSaved.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.save.setImageResource(R.drawable.saved_product)
            } else {
                binding.save.setImageResource(R.drawable.nonsaved_product)
            }
        })

        binding.productReviewButton.setOnClickListener {
            productViewModel.addReview(binding.commentRating.numStars, binding.productCommentText.text.toString())
        }
        productViewModel.onCompleteReview.observe(viewLifecycleOwner, Observer {
            if(it != null && it!!.status.successful) {
                productViewModel.resetOnCompleteReview()
                productViewModel.getProductReviews()
            }
            else if (it!=null && it.status.successful.not()){
                infoAlert(this, it.status!!.message!!)
                productViewModel.resetOnCompleteReview()
            }
        })

        binding.btnBack.setOnClickListener {
            view?.findNavController()!!.popBackStack()
        }

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

    override fun onStop() {
        super.onStop()
        activity?.toolbar_lay!!.visibility = View.VISIBLE
    }


}
