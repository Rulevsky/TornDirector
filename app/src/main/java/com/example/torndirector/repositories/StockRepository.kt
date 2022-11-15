package com.example.torndirector.repositories

import androidx.annotation.WorkerThread
import com.example.torndirector.room.Stock
import com.example.torndirector.room.StockDatabaseDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class StockRepository @Inject constructor(
    private val stockDatabaseDao: StockDatabaseDao
) {
    val stock: Flow<Stock> = stockDatabaseDao.getStockDetails()

    @WorkerThread
    suspend fun insert(stock: Stock){
        stockDatabaseDao.insert(stock)
    }
    @WorkerThread
    suspend fun update(stock: Stock){
        stockDatabaseDao.update(stock)
    }
    @WorkerThread
    suspend fun clear(){
        stockDatabaseDao.clear()
    }
}