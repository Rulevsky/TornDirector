package com.example.torndirector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.torndirector.retrofit.Common
import com.example.torndirector.retrofit.RetrofitServices
import com.example.torndirector.retrofit.model.EmployeesList
import com.example.torndirector.room.Employee
import com.example.torndirector.room.EmployeeDatabaseDao
import com.example.torndirector.room.EmployeesDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var employeeDatabase : EmployeesDatabase
    lateinit var fetchedEmployee: List<Employee>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        employeeDatabase = EmployeesDatabase.getInstance(applicationContext, CoroutineScope(Dispatchers.IO))

        gogogo()

    }

    fun gogogo() {
        Thread {
            //Do some Network Request
            fetchData()
            employeeDatabase.employeeDatabaseDao()
        }.start()
    }


    fun fetchData() {
        var mService: RetrofitServices = Common.retrofitService
        var fetchedData: String
        mService.getEmployees(selection = "employees", key = "XLeZozdhkemWL4hl")
            .enqueue(object : Callback<EmployeesList> {
                override fun onResponse(
                    call: Call<EmployeesList>,
                    response: Response<EmployeesList>
                ) {

                    fetchedData = response.body()!!.employeersList.toString()



                    Log.e("response", fetchedData)
                }

                override fun onFailure(call: Call<EmployeesList>, t: Throwable) {
                    Log.e("response", "failed", t)
                }
            })
    }

}