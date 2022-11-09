package com.example.torndirector.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface EmployeeDatabaseDao {
    @Insert
    suspend fun insert(employee: Employee)
    @Update
    suspend fun update(employee: Employee)
    @Query("SELECT * from employees_table WHERE employeeKey = :key")
    suspend fun get(key: Int): Employee?
    @Query("DELETE FROM employees_table")
    suspend fun clear()

    @Query("SELECT * from employees_table ORDER BY employeeKey ASC" )
    fun getAllEmployees(): Flow<List<Employee>>
}