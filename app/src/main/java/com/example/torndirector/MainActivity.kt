package com.example.torndirector

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.commitNow
import com.example.torndirector.repositories.CompanyRepository
import com.example.torndirector.repositories.StockRepository
import com.example.torndirector.retrofit.Common
import com.example.torndirector.retrofit.RetrofitServices
import com.example.torndirector.retrofit.model.*
import com.example.torndirector.room.*

import com.example.torndirector.ui.company.CompanyFragment
import com.example.torndirector.ui.emploeeListView.EmployeesListFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/*
check
insert and update repos and db
sum in inStock, inOrder, TotalDaily
ui of company fragment
clear code

*/

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var companyDatabaseDao: CompanyDatabaseDao

    @Inject
    lateinit var companyRepository: CompanyRepository

    @Inject
    lateinit var stockRepository: StockRepository

    private lateinit var companyDetails: CompanyDetails
    private lateinit var stockDetailsModel: StockDetailsModel
    private val key = "XLeZozdhkemWL4hl"
    private lateinit var employeeDatabase: EmployeesDatabase
    lateinit var fetchedEmployeesMap: Map<String, EmployeeModel>
    //lateinit var bottomNavBar: BottomNavigationView

    lateinit var companyAction: BottomNavigationItemView
    lateinit var employeesAction: BottomNavigationItemView
    lateinit var settingsAction: BottomNavigationItemView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        companyAction = findViewById(R.id.companyAction)
        employeesAction = findViewById(R.id.employeesAction)
        settingsAction = findViewById(R.id.settingsAction)

        employeeDatabase =
            EmployeesDatabase.getInstance(applicationContext, CoroutineScope(Dispatchers.IO))

        fetching()

        companyAction.setOnClickListener { companyActionPressed() }
        employeesAction.setOnClickListener { onEmployeeAction() }
        settingsAction.setOnClickListener { employeeActionPressed() }

    }


    private fun onEmployeeAction() {
        supportFragmentManager.commitNow {
            replace(R.id.fragment_container_view, EmployeesListFragment())
        }
    }

    fun companyActionPressed() {

        supportFragmentManager.commitNow {
            replace(R.id.fragment_container_view, CompanyFragment())

        }
    }

    fun employeeActionPressed() {
        supportFragmentManager.commitNow {
            replace(R.id.fragment_container_view, EmployeesListFragment())
        }
    }

    private fun fetching() {
        Thread {
            //Do some Network Request
            fetchCompanyData()
            fetchStockData()
            fetchEmployeesData()
        }.start()
    }

    fun fetchCompanyData() {
        val cService: RetrofitServices = Common.retrofitService
        cService.getCompanyDetails("profile", key)
            .enqueue(object : Callback<CompanyModel> {
                override fun onResponse(
                    call: Call<CompanyModel>,
                    response: Response<CompanyModel>
                ) {

                    CoroutineScope(SupervisorJob()).launch {
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
                    }
                }

                override fun onFailure(call: Call<CompanyModel>, t: Throwable) {
                    Log.e("onFailure", "failed", t)
                }

            })
    }

    fun fetchStockData() {
        val sService: RetrofitServices = Common.retrofitService
        sService.getStockDetails("stock", key)
            .enqueue(object : Callback<StockModel> {
                override fun onResponse(call: Call<StockModel>, response: Response<StockModel>) {
                    CoroutineScope(SupervisorJob()).launch {
                        var i = 1
                            stockRepository.clear()
                            response.body()!!.companyStock.forEach{ entry ->
                            stockRepository.insert(Stock(0, entry.value.inStock, entry.value.inOrder))
                        }
                    }

                }

                override fun onFailure(call: Call<StockModel>, t: Throwable) {
                    Log.e("onFailure", "failed", t)
                }

            })

    }

    private fun fetchEmployeesData() {
        val mService: RetrofitServices = Common.retrofitService
        mService.getEmployees(selection = "employees", key = key)
            .enqueue(object : Callback<EmployeesList> {
                override fun onResponse(call: Call<EmployeesList>, response: Response<EmployeesList>) {
                    fetchedEmployeesMap = response.body()!!.employeersList
                    var i = 1
                    fetchedEmployeesMap.forEach { entry ->
                        mapToList(
                            i,
                            entry.value.name,
                            entry.value.total.total,
                            entry.value.relative.relative
                        )
                        i++
                    }
                }

                override fun onFailure(call: Call<EmployeesList>, t: Throwable) {
                    Log.e("onFailure", "failed", t)
                }
            })
    }

    fun mapToList(key: Int, name: String, effectiveness: String, lastAction: String) {
        CoroutineScope(SupervisorJob()).launch {
            employeeDatabase.employeeDatabaseDao().clear()
            employeeDatabase.employeeDatabaseDao()
                .insert(Employee(key, name, effectiveness, lastAction))
        }
    }

}



//                    CoroutineScope(SupervisorJob()).launch {
//                        stockRepository.clear()
//                        var i = 1
//                        response.body()!!.forEach { entry ->
//                            stockRepository.insert(Stock(i, entry.inStock, entry.inOrder))
//                        }
//
//                    }