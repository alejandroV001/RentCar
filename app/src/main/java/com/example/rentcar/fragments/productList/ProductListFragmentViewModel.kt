package com.example.rentcar.fragments.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.example.rentcar.adapters.CartItemListAdapter
import com.example.rentcar.data.repositories.products.ProductsRepository
import com.example.rentcar.data.repositories.products.ProductsRepositoryLocal
import com.example.rentcar.data.repositories.users.UserRepository
import com.example.rentcar.helpers.VolleyRequestQueue
import com.example.rentcar.helpers.extensions.logErrorMessage
import com.example.rentcar.managers.SharedPrefsManager
import com.example.rentcar.models.CartItemModel
import com.example.rentcar.models.api.ProductAPIModel
import com.example.rentcar.models.api.toCartItems
import com.example.rentcar.BuildConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListFragmentViewModel @Inject constructor(
    private val productRepository: ProductsRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val products = productRepository.getAllProducts().map { productsList ->
        adapter.updateList(productsList.toCartItems())
    }

    val adapter = CartItemListAdapter(listOf())

    val users = userRepository.getPlebUsers()
    val usersAdmin = userRepository.getAdminUsers()

    fun getCartItems() {
        val url = "${BuildConfig.BASE_URL}products"

        val stringRequest = object: StringRequest(
            Request.Method.GET,
            url,
            { response ->
                "success".logErrorMessage()
                handleProductsResponse(response)
            },
            {
                "That didn't work!".logErrorMessage()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers: MutableMap<String, String> = HashMap()

                SharedPrefsManager.readToken()?.let { token ->
                    headers["Authorization"] = token
                }

                return headers
            }
        }

        VolleyRequestQueue.addToRequestQueue(stringRequest)
    }

    private fun handleProductsResponse(response: String) {
        val collectionType = object : TypeToken<List<ProductAPIModel>>() {}.type
        val responseJsonArray = Gson().fromJson<List<ProductAPIModel>>(response, collectionType)

        viewModelScope.launch(Dispatchers.IO) {
            productRepository.insertAllProducts(responseJsonArray)
        }
    }
}