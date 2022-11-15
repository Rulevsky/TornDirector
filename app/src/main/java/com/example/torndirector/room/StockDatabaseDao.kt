package com.example.torndirector.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
@Dao
interface StockDatabaseDao {
    @Insert
    suspend fun insert(stock: Stock)
    @Update
    suspend fun update(stock: Stock)
    @Query("DELETE FROM stock_table")
    suspend fun clear()
    @Query("SELECT * from stock_table ORDER BY stockKey ASC")
    fun getStockDetails(): Flow<Stock>
}