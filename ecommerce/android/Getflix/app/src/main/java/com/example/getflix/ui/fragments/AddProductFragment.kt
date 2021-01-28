package com.example.getflix.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.databinding.FragmentAddProductBinding
import com.example.getflix.doneAlert
import com.example.getflix.service.requests.AddProductRequest
import com.example.getflix.ui.viewmodels.VendorHomeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.io.ByteArrayOutputStream


@Suppress("DEPRECATION")
class AddProductFragment : Fragment() {

    private lateinit var binding: FragmentAddProductBinding
    private lateinit var bitmap: Bitmap
    private lateinit var viewModel: VendorHomeViewModel
    var imageList = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_product,
            container, false
        )
        activity?.toolbar!!.toolbar_title.text = "Add Product"

        binding.longDesc.setText("ThisisalongdescThisisalongdescThisisalongdescThisisalongdescThisisalongdescThisisalongdescThisisalongdescThisisalongdesc")

        viewModel = ViewModelProvider(this).get(VendorHomeViewModel::class.java)
        binding.image1.setOnClickListener {
            var intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 20)
        }
        binding.image2.setOnClickListener {
            var intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 21)
        }
        binding.image3.setOnClickListener {
            var intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 22)
        }

        binding.btnAdd.setOnClickListener {
            var list = imageList.toList()
            val addRequest = AddProductRequest(binding.name.text.toString(),
                binding.price.text.toString().toInt(),
                binding.stockAmount.text.toString().toInt(),binding.shortDesc.text.toString(),
                binding.longDesc.text.toString(),0.1,
                binding.brandId.text.toString().toInt(),binding.subcategoryId.text.toString().toInt(),list)

            viewModel.addVendorProduct(addRequest)
        }

        viewModel.navigateBack.observe(viewLifecycleOwner, Observer{
            if(it) {
                doneAlert(this, "Product is added successfully", ::navigateBack)
                viewModel.resetNavigate()
            }
        })

        return binding.root
    }

    private fun imageToString() {
        var stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
        imageList.add(Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT))
    }

    private fun navigateBack() {
        view?.findNavController()?.popBackStack()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==20 && resultCode == RESULT_OK && data!=null) {
            binding.image1.setImageURI(data.data)
           // var path = data.data
            println(data.data.toString())
            bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver,data.data)
            imageToString()

        } else if(requestCode==21 && resultCode == RESULT_OK && data!=null) {
            binding.image2.setImageURI(data.data)
            // var path = data.data
            println(data.data.toString())
            bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver,data.data)
            imageToString()

        } else if(requestCode==22 && resultCode == RESULT_OK && data!=null) {
            binding.image3.setImageURI(data.data)
            // var path = data.data
            println(data.data.toString())
            bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver,data.data)
            imageToString()


        }


    }
}