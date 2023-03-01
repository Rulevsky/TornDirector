package com.example.torndirector.utils


import android.util.Log
import com.example.torndirector.repositories.CompanyRepository
import com.example.torndirector.repositories.EmployeeRepository
import com.example.torndirector.repositories.SettingsRepository
import com.example.torndirector.repositories.StockRepository
import com.example.torndirector.retrofit.Common
import com.example.torndirector.retrofit.RetrofitServices
import com.example.torndirector.retrofit.model.*
import com.example.torndirector.room.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject

class FetchingDataClass @Inject constructor(
    val companyRepository: CompanyRepository,
    val employeeRepository: EmployeeRepository,
    val settingsRepository: SettingsRepository,
    val stockRepository: StockRepository
) {
    lateinit var fetchedEmployeesMap: Map<String, EmployeeModel>

    fun fetching() {
        CoroutineScope(SupervisorJob()).launch(Dispatchers.Main) {
            var key: String = getApiKey(settingsRepository)
            fetchCompanyData(key, companyRepository)
            fetchStockData(key, stockRepository)
            fetchEmployeesData(key, employeeRepository)
        }
        Log.e("tag", "fetching data class")
    }

    suspend fun getApiKey(settingsRepository: SettingsRepository): String {
        var apiKey: String? = null
        try {
            apiKey = settingsRepository.findSettingsByName("apiKey").settingsValue.toString()
        } catch (e: Exception) {
        }

        if (apiKey == null) {
            apiKey = "Enter ApiKey"
        }
        return apiKey
    }




    private fun fetchCompanyData(key: String, companyRepository: CompanyRepository) {
        val cService: RetrofitServices = Common.retrofitService
        cService.getCompanyDetails("profile", key)
            .enqueue(object : Callback<CompanyModel> {
                override fun onResponse(
                    call: Call<CompanyModel>,
                    response: Response<CompanyModel>
                ) {
                    CoroutineScope(SupervisorJob()).launch() {
                        try {
                            companyRepository.insert(
                                Company(
                                    0,
                                    response.body()!!.company.rating,
                                    response.body()!!.company.employeesHired,
                                    response.body()!!.company.employeesCapacity,
                                    response.body()!!.company.dailyIncome,
                                    response.body()!!.company.weeklyIncome
                                )
                            )
                        } catch (e: Exception) { //showErrorApiKey()
                        }
                    }
                    Log.e("company repos", response.body()?.company?.weeklyIncome.toString())
                }

                override fun onFailure(call: Call<CompanyModel>, t: Throwable) {
                    Log.e("onFailure", "failed", t)
                }
            })
    }

    private fun fetchStockData(key: String, stockRepository: StockRepository) {
        val sService: RetrofitServices = Common.retrofitService
        sService.getStockDetails("stock", key)
            .enqueue(object : Callback<StockModel> {
                override fun onResponse(call: Call<StockModel>, response: Response<StockModel>) {
                    // maybe better launch(lazy) to don't insert 0 values in db before forEach ends
                    CoroutineScope(SupervisorJob()).launch() {
                        stockRepository.clear()
                        var inStockGeneral: Long = 0
                        var inOrderGeneral: Long = 0
                        try {
                            response.body()!!.companyStock.forEach { entry ->
                                inStockGeneral += entry.value.inStock
                                inOrderGeneral += entry.value.inOrder
                            }
                            stockRepository.insert(
                                Stock(
                                    0,
                                    inStockGeneral,
                                    inOrderGeneral
                                )
                            )
                        } catch (e: Exception) { //showErrorApiKey()
                        }
                    }
                    Log.e("stock repos", response.body()?.companyStock.toString())
                }

                override fun onFailure(call: Call<StockModel>, t: Throwable) {
                    Log.e("onFailure", "failed", t)
                }
            })
    }

    private fun fetchEmployeesData(key: String, employeeRepository: EmployeeRepository) {
        val mService: RetrofitServices = Common.retrofitService
        mService.getEmployees(selection = "employees", key = key)
            .enqueue(object : Callback<EmployeesModel> {
                override fun onResponse(
                    call: Call<EmployeesModel>,
                    response: Response<EmployeesModel>
                ) {
                    CoroutineScope(SupervisorJob()).launch() {
                        try {
                            fetchedEmployeesMap = response.body()!!.employeesList
                            var i = 1

                            fetchedEmployeesMap.forEach { entry ->
                                mapToList(
                                    employeeRepository,
                                    i,
                                    entry.value.name,
                                    entry.value.total.total,
                                    entry.value.relative.relative
                                )
                                i++
                            }
                        } catch (e: Exception) { //showErrorApiKey()
                        }
                    }
                    Log.e("employee repos", response.body()?.employeesList.toString())
                }

                override fun onFailure(call: Call<EmployeesModel>, t: Throwable) {
                    Log.e("onFailure", "failed", t)
                }
            })

    }

    private fun mapToList(
        employeeRepository: EmployeeRepository,
        key: Int,
        name: String,
        effectiveness: String,
        lastAction: String
    ) {
        CoroutineScope(SupervisorJob()).launch() {
            employeeRepository.clear()
            employeeRepository.insert(Employee(key, name, effectiveness, lastAction))
        }
    }
}