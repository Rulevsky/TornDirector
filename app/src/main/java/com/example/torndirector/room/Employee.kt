package com.example.torndirector.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employees_table")
class Employee(
    @PrimaryKey(autoGenerate = true)
    var employeeKey: Int,

    @ColumnInfo(name = "Name")
    var employeeName: String,

    @ColumnInfo(name = "Efectiveness")
    var employeeefEctiveness: Long,

    @ColumnInfo(name = "Last action")
    var employeeLastAction: Long,
) {
}