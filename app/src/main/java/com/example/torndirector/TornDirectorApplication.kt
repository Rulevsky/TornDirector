package com.example.torndirector

import android.app.Application
import com.example.torndirector.room.EmployeesDatabase
import dagger.Provides
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton


@HiltAndroidApp

class TornDirectorApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val employeesDatabase by lazy { EmployeesDatabase.getInstance(this, applicationScope)}
    val repository by lazy { EmployeeRepository(employeesDatabase.employeeDatabaseDao()) }


}