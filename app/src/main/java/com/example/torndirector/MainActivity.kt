package com.example.torndirector

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.commitNow
import com.example.torndirector.repositories.CompanyRepository
import com.example.torndirector.repositories.EmployeeRepository
import com.example.torndirector.repositories.StockRepository
import com.example.torndirector.retrofit.Common
import com.example.torndirector.retrofit.RetrofitServices
import com.example.torndirector.retrofit.model.*
import com.example.torndirector.room.*

import com.example.torndirector.ui.company.CompanyFragment
import com.example.torndirector.ui.emploeeListView.EmployeesListFragment
import com.example.torndirector.ui.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
/*
Make Settings : apikey, description
Pull to refresh

fix: concatenation in CompanyFragment in hiredEmployees
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var companyDatabaseDao: CompanyDatabaseDao
    @Inject
    lateinit var companyRepository: CompanyRepository
    @Inject
    lateinit var stockRepository: StockRepository
    @Inject
    lateinit var employeeRepository: EmployeeRepository

    private val key = "XLeZozdhkemWL4hl"
    lateinit var fetchedEmployeesMap: Map<String, EmployeeModel>
    private lateinit var bottomNavBar: BottomNavigationView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavBar = findViewById(R.id.bottom_navigation)
        fetching()
        bottomNavBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.companyAction -> {
                    onCompanyAction()
                    true
                }
                R.id.employeesAction -> {
                    onEmployeeAction()
                    true
                }
                R.id.settingsAction -> {
                    onSettingsAction()
                    true
                }
                else -> false
            }
        }
    }

    private fun onEmployeeAction() {
        supportFragmentManager.commitNow {
            replace(R.id.fragment_container_view, EmployeesListFragment())
        }
    }
    private fun onCompanyAction() {
        supportFragmentManager.commitNow {
            replace(R.id.fragment_container_view, CompanyFragment())
        }
    }
    private fun onSettingsAction() {
        supportFragmentManager.commitNow {
            replace(R.id.fragment_container_view, SettingsFragment())
        }
    }
    //Do some Network Requests
    private fun fetching() {
            fetchCompanyData()
            fetchStockData()
            fetchEmployeesData()
    }

    private fun fetchCompanyData() {
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

    private fun fetchStockData() {
        val sService: RetrofitServices = Common.retrofitService
        sService.getStockDetails("stock", key)
            .enqueue(object : Callback<StockModel> {
                override fun onResponse(call: Call<StockModel>, response: Response<StockModel>) {
                    // maybe better launch(lazy) to don't insert 0 values in db before forEach ends
                    CoroutineScope(SupervisorJob()).launch {
                        stockRepository.clear()
                        var inStockGeneral: Long = 0
                        var inOrderGeneral: Long = 0
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
                override fun onResponse(
                    call: Call<EmployeesList>,
                    response: Response<EmployeesList>
                ) {
                    fetchedEmployeesMap = response.body()!!.employeesList
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

    private fun mapToList(key: Int, name: String, effectiveness: String, lastAction: String) {
        CoroutineScope(SupervisorJob()).launch {
            employeeRepository.clear()
            employeeRepository.insert(Employee(key, name, effectiveness, lastAction))
        }
    }
}
