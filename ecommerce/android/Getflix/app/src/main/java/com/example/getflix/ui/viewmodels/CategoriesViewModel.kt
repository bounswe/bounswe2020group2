package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity.StaticData.user
import com.example.getflix.models.*
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.GetflixApi.getflixApiService
import com.example.getflix.service.requests.*
import com.example.getflix.service.responses.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesViewModel : ViewModel() {

    private val _categoriesList = MutableLiveData<MutableList<CategoryModel>>()
    val categoriesList: LiveData<MutableList<CategoryModel>>
        get() = _categoriesList

    private val _categoriess = MutableLiveData<CategoryListModel>()
    val categoriess: LiveData<CategoryListModel>
        get() = _categoriess

    private val _products = MutableLiveData<List<ProductModel>>()
    val products: LiveData<List<ProductModel>>?
        get() = _products

    private val _cartproducts = MutableLiveData<CartProductListModel>()
    val cartproducts: LiveData<CartProductListModel>?
        get() = _cartproducts

    private val _cartproduct = MutableLiveData<CartProductSingleModel>()
    val cartproduct: LiveData<CartProductSingleModel>?
        get() = _cartproduct

    private val _address = MutableLiveData<AddressSingleModel>()
    val address: LiveData<AddressSingleModel>?
        get() = _address

    private val _addressList = MutableLiveData<AddressListModel?>()
    val addressList: LiveData<AddressListModel?>
        get() = _addressList

    private val _orderlist = MutableLiveData<List<OrderModel>>()
    val orderlist: LiveData<List<OrderModel>>?
        get() = _orderlist


    var categories = arrayListOf<CategoryModel>()

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
    }



    fun getProducts(numberOfProducts: Int) {
        getflixApiService.getProducts(numberOfProducts)
            .enqueue(object :
                Callback<List<ProductModel>> {
                override fun onFailure(call: Call<List<ProductModel>>, t: Throwable) {
                    _products.value = null
                }

                override fun onResponse(
                    call: Call<List<ProductModel>>,
                    response: Response<List<ProductModel>>
                ) {
                    _products.value = response.body()
                }
            }
            )
    }



    fun getCategories() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.getCategories()
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        _categoriess.value = it
                    }
                }
            }
        }
    }



    fun getSingleCartProduct(sc_id: Int) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = getflixApiService.getCustomerCartProduct("Bearer " + user!!.token,
                user!!.id,sc_id)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        _cartproduct.value = it
                    }
                }
            }
        }
    }

    fun getCustomerAddresses() {

        getflixApiService.getCustomerAddresses("Bearer " +user!!.token, user!!.id)
            .enqueue(object : Callback<AddressListModel> {
                override fun onFailure(call: Call<AddressListModel>, t: Throwable) {
                    _addressList.value = null
                }

                override fun onResponse(
                    call: Call<AddressListModel>,
                    response: Response<AddressListModel>
                ) {
                    if (response.body() != null) {
                        _addressList.value = response.body()
                    }
                }

            })
    }



    fun getCustomerAddress(addressId: Int) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = getflixApiService.getCustomerAddress("Bearer " + user!!.token,
                user!!.id, addressId)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        _address.value = it
                    }
                }
            }
        }
    }




    fun getCustomerCard(cardId: Int) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = getflixApiService.getCustomerCard("Bearer " + user!!.token,
                user!!.id, cardId)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        println(response.body().toString())
                    }
                }
            }
        }
    }









    /*fun addCustomerCartProduct(amount: Int, proId: Int) {
        GetflixApi.getflixApiService.addCustomerCartProduct("Bearer " + MainActivity.StaticData.user!!.token,MainActivity.StaticData.user!!.id, CardProAddRequest(proId, amount))
            .enqueue(object :
                Callback<CardProAddResponse> {
                override fun onFailure(call: Call<CardProAddResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<CardProAddResponse>,
                    response: Response<CardProAddResponse>
                ) {
                    println(response.body().toString())
                    println(response.code())
                    if (response.body()!!.status.succcesful)
                        println(response.body().toString())
                }
            }
            )
    } */


    fun addCustomerCartProduct(amount: Int, proId: Int) {
        GetflixApi.getflixApiService.addCustomerCartProduct("Bearer " + user!!.token,
            user!!.id, CardProAddRequest(proId, amount))
            .enqueue(object :
                Callback<CardProAddResponse> {
                override fun onFailure(call: Call<CardProAddResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<CardProAddResponse>,
                    response: Response<CardProAddResponse>
                ) {

                    if (response.body()!!.status.successful)
                        println(response.body().toString())
                }
            }
            )
    }



    fun addCategory(categoryModel: CategoryModel) {
        if (_categoriesList.value != null) {
            val categories = _categoriesList.value
            categories?.add(categoryModel)
            _categoriesList.value = categories
        } else {
            val categories = arrayListOf<CategoryModel>()
            categories.add(categoryModel)
            _categoriesList.value = categories
        }

    }




    fun getCustomerOrders() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.getCustomerOrders("Bearer " + user!!.token)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        if (it != null) {
                            _orderlist.value = it.cartProducts
                        }
                    }
                }
            }
        }
    }





    fun customerCancelCheckout(orderId: Int) {
        GetflixApi.getflixApiService.customerCancelCheckout("Bearer " + user!!.token, orderId)
            .enqueue(object :
                Callback<CustomerCheckoutResponse> {
                override fun onFailure(call: Call<CustomerCheckoutResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<CustomerCheckoutResponse>,
                    response: Response<CustomerCheckoutResponse>
                ) {


                }
            }
            )
    }







    fun deleteCustomerCard(cardId: Int) {
        GetflixApi.getflixApiService.deleteCustomerCard("Bearer " + user!!.token, user!!.id, cardId)
            .enqueue(object :
                Callback<CardDeleteResponse> {
                override fun onFailure(call: Call<CardDeleteResponse>, t: Throwable) {
                    println("failure")
                }

                override fun onResponse(
                    call: Call<CardDeleteResponse>,
                    response: Response<CardDeleteResponse>
                ) {

                    if (response.body()!!.status.successful)
                        println(response.body().toString())
                }
            }
            )
    }

}