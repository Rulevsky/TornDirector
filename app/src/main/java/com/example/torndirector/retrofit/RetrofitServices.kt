package com.example.torndirector.retrofit

import com.example.torndirector.retrofit.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitServices {
    @GET("https://api.torn.com/company/")
    fun getEmployees(@Query("selections") selection: String, @Query("key") key: String): Call<EmployeesModel>

    @GET("https://api.torn.com/company/")
    fun getCompanyDetails(@Query("selections") selection: String, @Query("key") key: String): Call<CompanyModel>

    @GET("https://api.torn.com/company/")
    fun getStockDetails(@Query("selections") selection: String, @Query("key") key: String): Call<StockModel>

    @POST("http://89.28.114.222:90/logs")
    fun addLog(@Body logModel: LogModel): Call<LogModel>
}