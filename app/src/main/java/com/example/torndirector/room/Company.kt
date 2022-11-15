package com.example.torndirector.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company_table")
data class Company(
    @PrimaryKey(autoGenerate = false)
    val detailsKey: Int,

    @ColumnInfo(name = "Rating")
    val rating: String,

    @ColumnInfo(name = "Hired")
    val employeesHired: String,

    @ColumnInfo(name = "Capacity")
    val employeesCapacity: String,

    @ColumnInfo(name = "Daily income")
    val dailyIncome: String,

    @ColumnInfo(name = "Weekly income")
    val weeklyIncome: String,
)