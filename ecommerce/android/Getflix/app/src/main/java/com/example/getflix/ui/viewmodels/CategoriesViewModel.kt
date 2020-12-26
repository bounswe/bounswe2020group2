package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity
import com.example.getflix.models.*
import com.example.getflix.service.GetflixApi
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

    private val _addresslist = MutableLiveData<AddressListModel>()
    val addresslist: LiveData<AddressListModel>?
        get() = _addresslist


    var categories = arrayListOf<CategoryModel>()

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
    }

    fun getCustomerCartProducts() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.getCustomerAllCartProducts("Bearer " + MainActivity.StaticData.user!!.token,MainActivity.StaticData.user!!.id)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    println("succesfull mu")
                    response.body().let { it ->
                        _cartproducts.value = it
                        println(_cartproducts.value.toString())
                    }
                }
            }
        }
    }

    fun getCategories() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.getCategories()
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        _categoriess.value = it
                        println(_categoriess.value.toString())
                    }
                }
            }
        }
    }

    fun getSingleCartProduct(sc_id: Int) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.getCustomerCartProduct("Bearer " + MainActivity.StaticData.user!!.token,MainActivity.StaticData.user!!.id,sc_id)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        _cartproduct.value = it
                        println(_cartproduct.value.toString())
                    }
                }
            }
        }
    }

    fun getCustomerAddresses() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.getCustomerAddresses("Bearer " + MainActivity.StaticData.user!!.token,MainActivity.StaticData.user!!.id)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        _addresslist.value = it
                        println(_addresslist.value.toString())
                    }
                }
            }
        }
    }



    fun getCustomerAddress(addressId: Int) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.getCustomerAddress("Bearer " + MainActivity.StaticData.user!!.token,MainActivity.StaticData.user!!.id, addressId)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        _address.value = it
                        println(_address.value.toString())
                    }
                }
            }
        }
    }


   

    fun getCustomerCard(cardId: Int) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.getCustomerCard("Bearer " + MainActivity.StaticData.user!!.token,MainActivity.StaticData.user!!.id, cardId)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        println(response.body().toString())
                    }
                }
            }
        }
    }




    fun updateCustomerCartProduct(amount: Int, scId: Int, proId: Int) {
        GetflixApi.getflixApiService.updateCustomerCartProduct("Bearer " + MainActivity.StaticData.user!!.token,MainActivity.StaticData.user!!.id, scId, CardProUpdateRequest(proId, amount))
                .enqueue(object :
                        Callback<CardProUpdateResponse> {
                    override fun onFailure(call: Call<CardProUpdateResponse>, t: Throwable) {

                    }

                    override fun onResponse(
                            call: Call<CardProUpdateResponse>,
                            response: Response<CardProUpdateResponse>
                    ) {
                        println(response.body().toString())
                        println(response.code())
                        if (response.body()!!.status.succcesful)
                            println(response.body().toString())
                    }
                }
                )
    }

    fun deleteCustomerCartProduct(scId: Int) {
        GetflixApi.getflixApiService.deleteCustomerCartProduct("Bearer " + MainActivity.StaticData.user!!.token,MainActivity.StaticData.user!!.id, scId)
                .enqueue(object :
                        Callback<CardProDeleteResponse> {
                    override fun onFailure(call: Call<CardProDeleteResponse>, t: Throwable) {
                        println("failure")
                    }

                    override fun onResponse(
                            call: Call<CardProDeleteResponse>,
                            response: Response<CardProDeleteResponse>
                    ) {
                        println(response.body().toString())
                        println(response.code())
                        if (response.body()!!.status.succcesful)
                            println(response.body().toString())
                    }
                }
                )
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
    }

    fun searchBySubcategory(subId: Int) {
        GetflixApi.getflixApiService.searchProductsBySubcategory(ProSearchBySubcategoryRequest(subId))
            .enqueue(object :
                Callback<ProSearchBySubcategoryResponse> {
                override fun onFailure(call: Call<ProSearchBySubcategoryResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<ProSearchBySubcategoryResponse>,
                    response: Response<ProSearchBySubcategoryResponse>
                ) {
                    println(response.body().toString())
                    println(response.code())

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



    fun setCategories(products: MutableList<ProductModel>) {
        val catList = arrayListOf<CategoryModel>()
        for (pro in products) {
            if (!catList.any { pro.category.name == it.name }) {
                val subCats = arrayListOf<SubcategoryModel>()
                val products = arrayListOf<ProductModel>()
                products.add(pro)
               // subCats.add(SubcategoryModel(pro.subcategory, products))
               // catList.add(CategoryModel(pro.category, subCats))
            } else {
                for (cat in catList) {
                    if (cat.name == pro.category.name) {
                        var ind = catList.indexOf(cat)
                        if (!catList[ind].subcategories!!.any { pro.subcategory.name == it.name }) {
                            val products = arrayListOf<ProductModel>()
                            products.add(pro)
                           // catList[ind].subCats.add(SubcategoryModel(pro.subcategory, products))
                        }
                    }
                }
            }
        }
        _categoriesList.value = catList
    }

    fun getCustomerCards() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.getCustomerCards("Bearer " + MainActivity.StaticData.user!!.token,MainActivity.StaticData.user!!.id)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        println(it.toString())
                    }
                }
            }
        }
    }

    fun getCustomerOrders() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = GetflixApi.getflixApiService.getCustomerOrders("Bearer " + MainActivity.StaticData.user!!.token)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        println(it.toString())
                    }
                }
            }
        }
    }





    fun deleteCustomerCard(cardId: Int) {
        GetflixApi.getflixApiService.deleteCustomerCard("Bearer " + MainActivity.StaticData.user!!.token,MainActivity.StaticData.user!!.id, cardId)
                .enqueue(object :
                        Callback<CardDeleteResponse> {
                    override fun onFailure(call: Call<CardDeleteResponse>, t: Throwable) {
                        println("failure")
                    }

                    override fun onResponse(
                            call: Call<CardDeleteResponse>,
                            response: Response<CardDeleteResponse>
                    ) {
                        println(response.body().toString())
                        println(response.code())
                        if (response.body()!!.status.succcesful)
                            println(response.body().toString())
                    }
                }
                )
    }

}