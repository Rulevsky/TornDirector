package com.example.torndirector.retrofit.model

import com.google.gson.annotations.SerializedName

data class EmployeeModel(
    @SerializedName("name")
    val name: String,


    @SerializedName("effectiveness")
    val total: Effectiveness,
    @SerializedName("last_action")
    val relative: LastAction

) {
}

data class Effectiveness(
    @SerializedName("total")
    val total: String
) {
}

data class LastAction(
    @SerializedName("relative")
    val relative: String
)