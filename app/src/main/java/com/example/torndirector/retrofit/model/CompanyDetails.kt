package com.example.torndirector.retrofit.model

import com.google.gson.annotations.SerializedName

data class CompanyDetails(
    @SerializedName("rating")
    val rating: String,

    @SerializedName("employees_hired")
    val employeesHired: String,

    @SerializedName("employees_capacity")
    val employeesCapacity: String,

    @SerializedName("daily_income")
    val dailyIncome: String,

    @SerializedName("weekly_income")
    val weeklyIncome: String,
) {

}