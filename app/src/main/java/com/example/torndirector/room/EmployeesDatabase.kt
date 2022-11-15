package com.example.torndirector.room


import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Employee::class], version = 1, exportSchema = false)
abstract class EmployeesDatabase : RoomDatabase() {

    abstract fun employeeDatabaseDao(): EmployeeDatabaseDao
}

