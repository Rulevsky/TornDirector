package com.example.torndirector

import androidx.annotation.WorkerThread
import com.example.torndirector.room.Employee
import com.example.torndirector.room.EmployeeDatabaseDao
import kotlinx.coroutines.flow.Flow

class EmployeeRepository(
    private val employeeDatabaseDao: EmployeeDatabaseDao
)
{
    val allEmployees : Flow<List<Employee>> = employeeDatabaseDao.getAllEmployees()

    @WorkerThread
    suspend fun insert(employee: Employee){
        employeeDatabaseDao.insert(employee)
    }

    @WorkerThread
    suspend fun update(employee: Employee){
        employeeDatabaseDao.update(employee)
    }


}