package com.example.torndirector.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface EmployeeDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
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