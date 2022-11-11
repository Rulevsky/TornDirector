package com.example.torndirector

import android.app.Application
import com.example.torndirector.room.EmployeesDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TornDirectorApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val employeesDatabase by lazy { EmployeesDatabase.getInstance(this, applicationScope)}
    val repository by lazy { EmployeeRepository(employeesDatabase.employeeDatabaseDao()) }


}