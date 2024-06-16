package com.example.rentcar.models.api

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rentcar.helpers.Utils.Constants.ApiArguments.ARG_API_CATEGORY
import com.example.rentcar.helpers.Utils.Constants.ApiArguments.ARG_API_TITLE
import com.example.rentcar.models.CartItemModel
import com.example.rentcar.models.CategoryModel
import com.example.rentcar.models.ProductModel
import com.google.gson.annotations.SerializedName

@Entity("product_model")
class ProductAPIModel(
    @PrimaryKey
    val id: String,
    @SerializedName(ARG_API_TITLE)
    val name: String,
    val description: String,
    @SerializedName(ARG_API_CATEGORY)
    val categoryName: String
)

fun List<ProductAPIModel>.toCartItems(): List<CartItemModel> {
    val items = mutableListOf<CartItemModel>()

    this.groupBy { it.categoryName }
        .forEach {
            val categoryModel = CategoryModel(
                id = it.key,
                title = it.key,
                description = it.key
            )

            val products = it.value.map { apiModel ->
                ProductModel(
                    id = apiModel.id,
                    title = apiModel.name,
                    description = apiModel.description
                )
            }

            items.add(categoryModel)
            items.addAll(products)
        }

    return items
}