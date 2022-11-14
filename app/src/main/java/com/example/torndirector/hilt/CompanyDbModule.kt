package com.example.torndirector.hilt

import android.content.Context
import androidx.room.Insert
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.torndirector.room.Company
import com.example.torndirector.room.CompanyDatabase
import com.example.torndirector.room.CompanyDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CompanyDbModule {
    @Singleton
    @Provides
    fun provideCompanyDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        CompanyDatabase::class.java,
        "company_table"
    ).build()


    @Singleton
    @Provides
    fun provideCompanyDao(db: CompanyDatabase) = db.companyDatabaseDao()

}

