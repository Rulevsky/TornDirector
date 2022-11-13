package com.example.torndirector

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.commitNow
import com.example.torndirector.retrofit.Common
import com.example.torndirector.retrofit.RetrofitServices
import com.example.torndirector.retrofit.model.EmployeeModel
import com.example.torndirector.retrofit.model.EmployeesList
import com.example.torndirector.ui.emploeeListView.EmployeesViewActivity
import com.example.torndirector.room.Employee
import com.example.torndirector.room.EmployeesDatabase
import com.example.torndirector.ui.emploeeListView.MainFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarMenu
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
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

        companyAction.setOnClickListener{companyActionPressed()}
        employeesAction.setOnClickListener{onEmployeeAction()}
        settingsAction.setOnClickListener{employeeActionPressed()}

    }

    private fun onEmployeeAction() {
        supportFragmentManager.commitNow {
            replace(R.id.fragment_container_view, EmployeesViewActivity())

        }
    }

    fun companyActionPressed() {

        supportFragmentManager.commitNow {
            replace(R.id.fragment_container_view, MainFragment())

        }
    }

    fun employeeActionPressed() {
        supportFragmentManager.commitNow {
            replace(R.id.fragment_container_view, EmployeesViewActivity())
        }
    }

    fun settingsActionPressed() {
    }


    private fun fetching() {
        Thread {
            //Do some Network Request
            fetchData()
        }.start()
    }

    private fun fetchData() {
        val mService: RetrofitServices = Common.retrofitService
        mService.getEmployees(selection = "employees", key = "XLeZozdhkemWL4hl")
            .enqueue(object : Callback<EmployeesList> {
                override fun onResponse(
                    call: Call<EmployeesList>,
                    response: Response<EmployeesList>
                ) {

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
