package com.example.torndirector.hilt

import android.content.Context
import androidx.room.Room
import com.example.torndirector.room.StockDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StockModule {
    @Singleton
    @Provides
    fun provideCompanyDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        StockDatabase::class.java,
        "stock_table"
    ).build()

    @Singleton
    @Provides
    fun provideStockDao(db: StockDatabase) = db.stockDatabaseDao()
}