package com.example.getflix.ui.fragments

import android.location.Geocoder
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.getflix.OrderStatus
import com.example.getflix.R
import com.example.getflix.databinding.FragmentVendorOrderBinding
import com.example.getflix.models.VendorOrderModel
import com.example.getflix.ui.viewmodels.VendorOrderViewModel
import com.example.getflix.vendorOrderModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class VendorOrderFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentVendorOrderBinding
    private lateinit var vendorOrderViewModel: VendorOrderViewModel
    private lateinit var currentOrder: VendorOrderModel
    private lateinit var fragmentGoogleMap: GoogleMap


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.toolbar!!.toolbar_title.text = getString(R.string.orders_vendor)
        currentOrder = vendorOrderModel!!
        vendorOrderModel = null



        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_vendor_order,
            container, false
        )

        vendorOrderViewModel = ViewModelProvider(this).get(VendorOrderViewModel::class.java)
        binding.lifecycleOwner = this

        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation

        val radius = resources.getDimension(R.dimen.cornerSize)
        val spinnerRadius = resources.getDimension(R.dimen.spinnerCornerSize)

        val constraintLayout = binding.orderImageLayout
        val shapeAppearanceModel = ShapeAppearanceModel()
            .toBuilder()
            .setTopLeftCorner(CornerFamily.CUT, radius)
            .setTopRightCorner(CornerFamily.CUT, radius)
            .setBottomLeftCorner(CornerFamily.CUT, radius)
            .setBottomRightCorner(CornerFamily.CUT, radius)
            .build()

        val shapeAppearanceModelForSpinner = ShapeAppearanceModel()
            .toBuilder()
            .setTopLeftCorner(CornerFamily.CUT, spinnerRadius)
            .setTopRightCorner(CornerFamily.CUT, spinnerRadius)
            .setBottomLeftCorner(CornerFamily.CUT, spinnerRadius)
            .setBottomRightCorner(CornerFamily.CUT, spinnerRadius)
            .build()

        val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
        shapeDrawable.setFillColor(
            ContextCompat.getColorStateList(
                requireContext(),
                R.color.vendor_background
            )
        )
        val shapeDrawableForSpinner = MaterialShapeDrawable(shapeAppearanceModelForSpinner)
        shapeDrawableForSpinner.setFillColor(
            ContextCompat.getColorStateList(
                requireContext(),
                R.color.white
            )
        )

        shapeDrawable.elevation = 4.5.toFloat()
        shapeDrawableForSpinner.elevation = 4.5.toFloat()
        ViewCompat.setBackground(constraintLayout, shapeDrawable)
        ViewCompat.setBackground(binding.spinnerLayout, shapeDrawableForSpinner)

        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.order_status,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)

        binding.spinner3.adapter = adapter
        val status = when (currentOrder.status) {
            OrderStatus.ACCEPTED.status -> OrderStatus.ACCEPTED.status
            OrderStatus.DELIVERED.status -> OrderStatus.DELIVERED.value
            OrderStatus.AT_CARGO.status -> OrderStatus.AT_CARGO.value
            else -> OrderStatus.CANCELLED.value
        }
        binding.spinner3.setSelection(
            adapter.getPosition(
                status
            )
        )
        binding.spinner3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val newStatusValue = parent?.getItemAtPosition(position)
                val newStatus = when (newStatusValue) {
                    OrderStatus.ACCEPTED.value -> OrderStatus.ACCEPTED.status
                    OrderStatus.DELIVERED.value -> OrderStatus.DELIVERED.status
                    OrderStatus.AT_CARGO.value -> OrderStatus.AT_CARGO.status
                    else -> OrderStatus.CANCELLED.status
                }
                if (currentOrder.status.equals(newStatus).not()) {
                    vendorOrderViewModel.changeStatusOfOrder(currentOrder.id, currentOrder.status)
                }
            }
        }
        vendorOrderViewModel.onStatusChanged.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.not()) {
                    Toast.makeText(
                        context,
                        "Oops! We could not change the status of your order...",
                        Toast.LENGTH_SHORT
                    )
                }
                vendorOrderViewModel.onStatusChangeCompleted()
            }
        })

        Picasso.get().load(currentOrder.product.images[0]).into(binding.imageView4)
        binding.lifecycleOwner = this
        binding.curOrderAmount.text = "x" + currentOrder.amount.toString()
        binding.curOrderDate.text =
            currentOrder.purchaseDate.substring(0, 10) + " " + currentOrder.purchaseDate.substring(11, 19)
        binding.orderName.text = currentOrder.product.name
        binding.discountRate.text = currentOrder.product.discount.toString()
        binding.priceBeforeDiscountVal.text = currentOrder.product.price.toString() + " TL"
        binding.priceAfterDiscountVal.text = currentOrder.product.priceDiscounted.toString() + " TL"
        binding.curOrderTotalPrice.text = (currentOrder.product.priceDiscounted * currentOrder.amount).toString() + " TL"
        binding.orderOwnerName.text = currentOrder.address.name
        binding.orderOwnerSurname.text = currentOrder.address.surname
        binding.orderOwnerAddress.text = currentOrder.address.address
        binding.orderOwnerCountry.text = currentOrder.address.country
        binding.orderOwnerProvince.text = currentOrder.address.province
        binding.orderOwnerZipCode.text = currentOrder.address.zipCode
        binding.phone.text = currentOrder.address.phone.countryCode + " " + currentOrder.address.phone.number

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        return binding.root
    }
    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onMapReady(googleMap : GoogleMap?) {
        fragmentGoogleMap = googleMap!!
        val fullAdress = currentOrder.address.address + " "+ currentOrder.address.province + " " + currentOrder.address.city + " " + currentOrder.address.country  + " " + currentOrder.address.zipCode
        val geocoder = Geocoder(requireContext())
        var latitude : Double = 0.0
        var longtitude  : Double = 0.0
        try {
            var geoResults = geocoder.getFromLocationName(fullAdress, 1)
            while (geoResults.size == 0) {
                geoResults = geocoder.getFromLocationName(fullAdress, 1)
            }
            if (geoResults.size>0) {
                val addr = geoResults.get(0)
                latitude = addr.getLatitude()
                longtitude = addr.getLongitude()
            }
            val latLng = LatLng(latitude,longtitude)
            val zoomLevel = 16.0.toFloat()
            fragmentGoogleMap.addMarker(MarkerOptions().position(latLng).title("Order Address"))
            fragmentGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoomLevel))

        } catch (e : Exception){
            println(e.message)
        }


    }

}