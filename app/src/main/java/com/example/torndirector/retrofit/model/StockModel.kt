package com.example.torndirector.retrofit.model

import com.google.gson.annotations.SerializedName

class StockModel(
    @SerializedName("company_stock")
    val companyStock: Map<String, StockDetailsModel>
) {
}

data class StockDetailsModel(
    @SerializedName("in_stock")
    val inStock: Long,
    @SerializedName("in_order")
    val inOrder: Long,
)