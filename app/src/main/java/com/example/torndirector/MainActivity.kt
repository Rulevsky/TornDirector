package com.example.torndirector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.torndirector.retrofit.Common
import com.example.torndirector.retrofit.RetrofitServices
import com.example.torndirector.retrofit.model.EmployeeModel
import com.example.torndirector.retrofit.model.EmployeesList
import com.example.torndirector.retrofit.ui.emploeeListView.EmployeesViewActivity
import com.example.torndirector.room.Employee
import com.example.torndirector.room.EmployeesDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var employeeDatabase : EmployeesDatabase
    lateinit var fetchedEmployeesMap: Map<String,EmployeeModel>

    private lateinit var magicButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        employeeDatabase = EmployeesDatabase.getInstance(applicationContext, CoroutineScope(Dispatchers.IO))
        magicButton = findViewById(R.id.buttonMagic)
        magicButton.setOnClickListener { onMagicBtnPress() }
        fetching()
    }

    private fun onMagicBtnPress() {
        val intent = Intent(this, EmployeesViewActivity::class.java)
        startActivity(intent)
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
                    fetchedEmployeesMap.forEach{ entry ->
                        mapToList(i, entry.value.name, entry.value.total.total, entry.value.relative.relative)
                        i++
                    }
                }

                override fun onFailure(call: Call<EmployeesList>, t: Throwable) {
                    Log.e("onFailure", "failed", t)
                }
            })
    }

    fun mapToList(key: Int, name: String, effectiveness: String, lastAction: String){
       CoroutineScope(SupervisorJob()).launch {
           employeeDatabase.employeeDatabaseDao().clear()
           employeeDatabase.employeeDatabaseDao().insert(Employee( key, name, effectiveness, lastAction)) }
    }
}