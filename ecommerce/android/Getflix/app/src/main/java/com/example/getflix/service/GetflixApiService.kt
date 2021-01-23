package com.example.getflix.service

import com.example.getflix.models.*
import com.example.getflix.service.requests.*
import com.example.getflix.service.responses.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


const val BASE_URL = "http://ec2-18-223-113-236.us-east-2.compute.amazonaws.com:8000/"

private val requestInterceptor = Interceptor { chain ->

    val url = chain.request()
            .url()
            .newBuilder()
            .build()

    val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
    return@Interceptor chain.proceed(request)
}

private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .build()


private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


interface GetflixApiService {


    @Headers("Content-Type: application/json")
    @POST("regularlogin")
    fun getUserInformation(@Body userData: LoginRequest): Call<LoginResponse>


    @Headers("Content-Type: application/json")
    @POST("regularsignup")
    fun signUp(@Body signUpRequest: SignUpRequest): Call<SignUpResponse>

    @GET("products/homepage/{numberOfProducts}")
    fun getProducts(@Path("numberOfProducts") numberOfProducts: Int): Call<List<ProductModel>>

    @GET("product/{productId}")
    fun getProduct(@Path("productId") productId: Int): Call<ProductModel>

    @GET("customer/{customerId}/shoppingcart")
    fun getCustomerAllCartProducts(@Header("Authorization") token: String, @Path("customerId") customerId: Int): Call<CartProductListModel?>

    @GET("customer/{customerId}/shoppingcart/{sc_item_id}")
    suspend fun getCustomerCartProduct(@Header("Authorization") token: String, @Path("customerId") customerId: Int,@Path("sc_item_id") sc_item_id: Int): Response<CartProductSingleModel>

    @Headers("Content-Type: application/json")
    @POST("customer/{customerId}/shoppingcart")
    fun addCustomerCartProduct(@Header("Authorization") token: String, @Path("customerId") customerId: Int,@Body cardproData: CardProAddRequest): Call<CardProAddResponse>

    @Headers("Content-Type: application/json")
    @PUT("customer/{customerId}/shoppingcart/{sc_item_id}")
    fun updateCustomerCartProduct(@Header("Authorization") token: String, @Path("customerId") customerId: Int,@Path("sc_item_id") sc_item_id: Int,@Body cardproData: CardProUpdateRequest): Call<CardProUpdateResponse>

    @Headers("Content-Type: application/json")
    @DELETE("customer/{customerId}/shoppingcart/{sc_item_id}")
    fun deleteCustomerCartProduct(@Header("Authorization") token: String, @Path("customerId") customerId: Int,@Path("sc_item_id") sc_item_id: Int): Call<CardProDeleteResponse>

    @GET("categories")
    suspend fun getCategories(): Response<CategoryListModel>


    @GET("customer/{customerId}/addresses")
    fun getCustomerAddresses(@Header("Authorization") token: String, @Path("customerId") customerId: Int): Call<AddressListModel>

    @Headers("Content-Type: application/json")
    @POST("customer/{customerId}/addresses")
    fun addCustomerAddress(@Header("Authorization") token: String, @Path("customerId") customerId: Int,@Body addressData: AddressAddRequest): Call<AddressAddResponse>

    @GET("customer/{customerId}/addresses/{addressId}")
    suspend fun getCustomerAddress(@Header("Authorization") token: String, @Path("customerId") customerId: Int,@Path("addressId") addressId: Int): Response<AddressSingleModel>

    @Headers("Content-Type: application/json")
    @PUT("customer/{customerId}/addresses/{address_id}")
    fun updateCustomerAddress(@Header("Authorization") token: String, @Path("customerId") customerId: Int,@Path("address_id") address_id: Int,@Body addressData: AddressUpdateRequest): Call<AddressUpdateResponse>

    @Headers("Content-Type: application/json")
    @DELETE("customer/{customerId}/addresses/{address_id}")
    fun deleteCustomerAddress(@Header("Authorization") token: String, @Path("customerId") customerId: Int,@Path("address_id") address_id: Int): Call<AddressDeleteResponse>

    @GET("customer/{customerId}/cards")
    fun getCustomerCards(@Header("Authorization") token: String, @Path("customerId") customerId: Int): Call<CardListModel>

    @Headers("Content-Type: application/json")
    @POST("customer/{customerId}/cards")
    fun addCustomerCard(@Header("Authorization") token: String, @Path("customerId") customerId: Int,@Body cardData: CardAddRequest): Call<CardAddResponse>

    @GET("customer/{customerId}/cards/{cardId}")
    suspend fun getCustomerCard(@Header("Authorization") token: String, @Path("customerId") customerId: Int,@Path("cardId") cardId: Int): Response<CardSingleModel>

    @Headers("Content-Type: application/json")
    @PUT("customer/{customerId}/cards/{card_id}")
    fun updateCustomerCard(@Header("Authorization") token: String, @Path("customerId") customerId: Int,@Path("card_id") card_id: Int,@Body cardData: CardUpdateRequest): Call<CardUpdateResponse>

    @Headers("Content-Type: application/json")
    @DELETE("customer/{customerId}/cards/{card_id}")
    fun deleteCustomerCard(@Header("Authorization") token: String, @Path("customerId") customerId: Int,@Path("card_id") address_id: Int): Call<CardDeleteResponse>


    @GET("customer/orders")
    suspend fun getCustomerOrders(@Header("Authorization") token: String): Response<CustomerOrderListModel>

    @Headers("Content-Type: application/json")
    @POST("search/products")
    fun searchProductsBySubcategory(@Body cardData: ProSearchBySubcategoryRequest): Call<ProSearchBySubcategoryResponse>

    // puts shopping cart items into purchase and order table
    @Headers("Content-Type: application/json")
    @POST("checkout/payment")
    fun customerCheckout(@Header("Authorization") token: String, @Body checkoutData: CustomerCheckoutRequest): Call<CustomerCheckoutResponse>

    // cancel specific order
    @Headers("Content-Type: application/json")
    @POST("checkout/cancelorder/{id}")
    fun customerCancelCheckout(@Header("Authorization") token: String, @Path("id") orderId: Int): Call<CustomerCheckoutResponse>

    // get prices of shopping cart
    @GET("checkout/details")
    fun getCustomerCartPrice(@Header("Authorization") token: String): Call<CustomerCartPriceModel?>

    @GET("review")
    fun getReviewOfProduct(@Query("product") productId: Int) : Call<ProductReviewListModel>

    @Headers("Content-Type: application/json")
    @POST("search/products")
    fun searchProductsByVendor(@Body cardData: ProSearchByVendorRequest): Call<ProSearchByVendorResponse>

    @Headers("Content-Type: application/json")
    @POST("search/products")
    fun searchProductsByQuery(@Body cardData: ProSearchByQueryRequest): Call<ProSearchByVendorResponse>

    @Headers("Content-Type: application/json")
    @POST("search/products")
    fun searchProductsSort(@Body cardData: ProSearchSortRequest): Call<ProSearchByVendorResponse>

    @Headers("Content-Type: application/json")
    @PUT("vendor/product")
    fun updateVendorProduct(@Header("Authorization") token: String, @Body vendorProData: VendorProUpdateRequest): Call<VendorProUpdateResponse>

    @GET("vendor/order")
    fun getVendorOrders(@Header("Authorization") token: String): Call<VendorOrderResponse>

    @Headers("Content-Type: application/json")
    @PUT("vendor/order")
    fun updateOrderStatus(@Header("Authorization")token : String , @Body vendorOrderStatusRequest: VendorOrderStatusRequest): Call<Status>

    @GET("lists")
    suspend fun getLists(@Header("Authorization") token: String): Response<ListsModel>

    @Headers("Content-Type: application/json")
    @POST("lists")
    fun createList(@Header("Authorization") token: String, @Body listData: CreateListRequest): Call<CreateListResponse>

}

object GetflixApi {
    val getflixApiService: GetflixApiService by lazy { retrofit.create(GetflixApiService::class.java) }
}
