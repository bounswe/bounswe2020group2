package com.example.getflix.ui.fragments


import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Paint
import android.os.Bundle
import android.text.InputType
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
import com.example.getflix.activities.MainActivity
import com.example.getflix.databinding.FragmentProductBinding
import com.example.getflix.doneAlert
import com.example.getflix.hideKeyboard
import com.example.getflix.infoAlert
import com.example.getflix.service.requests.CreateListRequest
import com.example.getflix.ui.adapters.CommentAdapter
import com.example.getflix.ui.adapters.ImageAdapter
import com.example.getflix.ui.adapters.RecommenderAdapter
import com.example.getflix.ui.fragments.ProductFragmentDirections.Companion.actionProductFragmentToVendorPageFragment
import com.example.getflix.ui.viewmodels.ListViewModel
import com.example.getflix.ui.viewmodels.ProductViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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

            MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_color)
                .setTitle("Select a List")
                .setSingleChoiceItems(
                    listNames.toTypedArray(),
                    checkedList
                ) { dialog, which ->
                    checkedList = which
                }
                .setPositiveButton("Ok") { dialog, which ->
                    println(checkedList.toString())
                    println(listIds[checkedList])
                    dialog.dismiss()
                    listViewModel.addProductToList(listIds[checkedList],args.productId)
                    doneAlert(this,"The product is added to your list successfully!",null)
                }
                .setIcon(R.drawable.accepted_list)
                .setNegativeButton("Cancel") { dialog, which ->
                }
                .setNeutralButton("Add New List") { dialog1, which ->
                    dialog1.dismiss()
                    var dialog = AlertDialog.Builder(context,R.style.MaterialAlertDialog_color)
                    var dialogView = layoutInflater.inflate(R.layout.custom_dialog,null)
                    var edit = dialogView.findViewById<TextInputEditText>(R.id.name)
                    dialog.setView(dialogView)
                    dialog.setCancelable(true)
                    dialog.setIcon(R.drawable.ic_pencil)
                    dialog.setTitle("Add A List")
                    dialog.setNegativeButton("Cancel") { dialogInterface: DialogInterface, i: Int -> }
                    dialog.setPositiveButton("Create") { dialogInterface: DialogInterface, i: Int ->
                        println(edit.text.toString())
                        hideKeyboard(requireActivity())
                        listViewModel.createList(CreateListRequest(edit.text.toString()))
                        doneAlert(this,"A new list is created successfully!",null)
                    }
                    dialog.show()
                }
                .show()
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
            var id = 3
            view?.findNavController()!!.navigate(actionProductFragmentToVendorPageFragment(id))
        }

        binding.increase.setOnClickListener {
            productViewModel.increaseAmount()
        }
        binding.addToCart.setOnClickListener {
            if(MainActivity.StaticData.isVisitor) {
                infoAlert(this, "You should be logged in to add product to your shopping cart")
            } else {
                productViewModel.addCustomerCartProduct(
                    binding.amount.text.toString().toInt(),
                    args.productId
                )
            }
        }

        productViewModel.navigateBack.observe(viewLifecycleOwner, Observer{
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
        productViewModel.isSaved.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.save.setImageResource(R.drawable.saved_product)
            } else {
                binding.save.setImageResource(R.drawable.nonsaved_product)
            }
        })

        productViewModel.onCompleteReview.observe(viewLifecycleOwner, Observer {
            if(it!=null  ) {
                println(it)
                productViewModel.getProductReviews()
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
