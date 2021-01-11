package com.example.getflix.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getflix.activities.MainActivity
import com.example.getflix.models.ProductModel
import com.example.getflix.models.ProductReviewListModel
import com.example.getflix.models.ReviewModel
import com.example.getflix.service.GetflixApi
import com.example.getflix.service.requests.CardProAddRequest
import com.example.getflix.service.requests.CardProUpdateRequest
import com.example.getflix.service.responses.CardProAddResponse
import com.example.getflix.service.responses.CardProUpdateResponse
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel : ViewModel() {
    private val _recommendedProducts = MutableLiveData<MutableList<ProductModel>?>()
    val recommendedProducts: LiveData<MutableList<ProductModel>?>
        get() = _recommendedProducts

    private val _imageURLs = MutableLiveData<MutableList<String>>()
    val imageURLs: LiveData<MutableList<String>>
        get() = _imageURLs

    private val _amount = MutableLiveData<Int>()
    val amount: LiveData<Int>
        get() = _amount


    private val _navigateBack = MutableLiveData<Boolean>()
    val navigateBack: LiveData<Boolean>
        get() = _navigateBack

    private val _isSaved = MutableLiveData<Boolean>()
    val isSaved: LiveData<Boolean>
        get() = _isSaved

    private val _reviews = MutableLiveData<List<ReviewModel>?>()
    val reviews: LiveData<List<ReviewModel>?>
        get() = _reviews


    private val _product = MutableLiveData<ProductModel?>()
    val product: LiveData<ProductModel?>
        get() = _product

    private val _addedToShoppingCart = MutableLiveData<Boolean>()
    val addedToShoppingCart: LiveData<Boolean>
        get() = _addedToShoppingCart

    init {
        _amount.value = 1
        _isSaved.value = false
        _product.value = null
        _addedToShoppingCart.value = false
        _recommendedProducts.value = null
        _reviews.value = null
        getRecommendedProducts(5)
    }

    fun getProductReviews() {

        GetflixApi.getflixApiService.getReviewOfProduct(_product.value!!.id)
            .enqueue(object :
                Callback<ProductReviewListModel> {
                override fun onFailure(call: Call<ProductReviewListModel>, t: Throwable) {
                    _reviews.value = null
                }

                override fun onResponse(
                    call: Call<ProductReviewListModel>,
                    response: Response<ProductReviewListModel>
                ) {
                    _reviews.value = response.body()?.reviews
                    println(_reviews.value)

                }
            }
            )
    }

    fun getProduct(productId: Int) {
        GetflixApi.getflixApiService.getProduct(productId)
            .enqueue(object :
                Callback<ProductModel> {
                override fun onFailure(call: Call<ProductModel>, t: Throwable) {
                    _product.value = null
                }

                override fun onResponse(
                    call: Call<ProductModel>,
                    response: Response<ProductModel>
                ) {
                    _product.value = response.body()
                    if (_product.value != null) {
                        getProductReviews()
                    }
                }
            }
            )

    }

    fun addCustomerCartProduct(amount: Int, proId: Int) {
        GetflixApi.getflixApiService.addCustomerCartProduct(
            "Bearer " + MainActivity.StaticData.user!!.token,
            MainActivity.StaticData.user!!.id,
            CardProAddRequest(proId, amount)
        )
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
                    _navigateBack.value = true
                }
            }
            )
    }

    fun addToShoppingCart(shoppingCartId: Int, productId: Int) {
        GetflixApi.getflixApiService.updateCustomerCartProduct(
            "Bearer " + MainActivity.StaticData.user!!.token,
            MainActivity.StaticData.user!!.id, shoppingCartId, CardProUpdateRequest(
                productId,
                _amount.value!!
            )
        )
            .enqueue(object :
                Callback<CardProUpdateResponse> {
                override fun onFailure(call: Call<CardProUpdateResponse>, t: Throwable) {
                    _addedToShoppingCart.value = false
                }

                override fun onResponse(
                    call: Call<CardProUpdateResponse>,
                    response: Response<CardProUpdateResponse>
                ) {
                    _addedToShoppingCart.value = false
                }
            }
            )
    }

    fun decreaseAmount() {
        if (_amount.value!! > 1) {
            _amount.value = _amount.value?.dec()
        }
    }

    fun increaseAmount() {

        _amount.value = _amount.value?.inc()
    }

    fun onSaveClick() {
        _isSaved.value = _isSaved.value?.not()
    }

    fun getRecommendedProducts(numberOfProducts: Int) {
        GetflixApi.getflixApiService.getProducts(numberOfProducts)
            .enqueue(object :
                Callback<List<ProductModel>> {
                override fun onFailure(call: Call<List<ProductModel>>, t: Throwable) {
                    _recommendedProducts.value = null

                }

                override fun onResponse(
                    call: Call<List<ProductModel>>,
                    response: Response<List<ProductModel>>
                ) {
                    _recommendedProducts.value = response.body() as MutableList<ProductModel>?
                }
            }
            )
    }

    fun resetNavigate() {
        _navigateBack.value = false
    }
}