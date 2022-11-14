package com.example.torndirector.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock_table")
data class Stock (
    @PrimaryKey(autoGenerate = true)
    val stockKey: Int,

    @ColumnInfo(name = "in Stock")
    val inStock: Long,

    @ColumnInfo(name = "in order")
    val inOrder:Long,

    @ColumnInfo(name = "In stock today")
    val inStockToday: Long = inStock + inOrder
)
{
}