package com.example.getflix.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.activities.MainActivity
import com.example.getflix.activities.MainActivity.StaticData.user
import com.example.getflix.databinding.FragmentOrderProductsBinding
import com.example.getflix.doneAlert
import com.example.getflix.infoAlert
import com.example.getflix.models.*
import com.example.getflix.service.requests.ReviewRequest
import com.example.getflix.ui.adapters.OrderProductsAdapter
import com.example.getflix.ui.viewmodels.OrderPurchasedViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_register.view.*


class OrderProductsFragment : Fragment() {

    private lateinit var viewModel: OrderPurchasedViewModel
    private lateinit var dialog: Dialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentOrderProductsBinding>(
            inflater, R.layout.fragment_order_products,
            container, false
        )
        activity?.toolbar!!.toolbar_title.text = getString(R.string.order_products)

        viewModel = ViewModelProvider(this).get(OrderPurchasedViewModel::class.java)
        val recView = binding?.listProductList as RecyclerView
        val args = OrderProductsFragmentArgs.fromBundle(requireArguments())
        val productList = args.products.toCollection(ArrayList())
        println(productList.toString())


        val listAdapter = OrderProductsAdapter(productList)
        recView.adapter = listAdapter
        recView.setHasFixedSize(true)

        for (product in productList) {
            viewModel.addOrderPurchased(product)
        }

        viewModel.purchasedProductList.observe(viewLifecycleOwner, Observer {
            it?.let {
                listAdapter.submitList(it)
            }
        })


        listAdapter.currentOrder.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                dialog = Dialog(requireContext())
                dialog.setContentView(R.layout.comment_dialog)
                dialog.window?.setBackgroundDrawableResource(R.drawable.back)
                dialog.window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                dialog.setCancelable(true)
                val reviewButton = dialog.findViewById<TextView>(R.id.review_button)
                val cancelButton = dialog.findViewById<TextView>(R.id.cancel_button)

                val comment = dialog.findViewById<EditText>(R.id.editTextComment)
                val ratingBar = dialog.findViewById<RatingBar>(R.id.comment_rating_bar)
                cancelButton.setOnClickListener {
                    dialog.dismiss()
                }
                val orderPurchasedModel = it
                reviewButton.setOnClickListener {
                    var request = ReviewRequest(
                        user!!.id,
                        orderPurchasedModel!!.product.id,
                        orderPurchasedModel!!.vendor.id,
                        ratingBar.numStars,
                        comment.text.toString()
                    )
                    viewModel.addReview(request)
                    dialog.dismiss()

                }
                dialog.show()

            }
        })

        viewModel.onCompleteReview.observe(viewLifecycleOwner, Observer {

            if(it!=null) {
                listAdapter.currentOrder.value = null
                if (it != null && it!!.status.successful) {
                    doneAlert(this,"Thank you for your review!",null)
                } else if (it != null && it.status.successful.not()) {
                    infoAlert(this, it.status!!.message!!)
                }
                viewModel.resetOnCompleteReview()
            }
        })

        return binding.root
    }

}