package com.example.torndirector.retrofit

object Common {
    private val BASE_URL = "https://api.torn.com/company/"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}