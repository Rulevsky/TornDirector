package com.example.torndirector.hilt

import android.content.Context
import androidx.room.Room
import com.example.torndirector.room.CompanyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CompanyModule {
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

