package com.example.torndirector.retrofit.model

import com.google.gson.annotations.SerializedName


data class LogModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("time")
    val time: String
)
