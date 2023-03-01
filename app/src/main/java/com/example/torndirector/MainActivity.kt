package com.example.torndirector

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import androidx.work.*
import androidx.work.WorkManager
import com.example.torndirector.retrofit.model.*
import com.example.torndirector.room.*
import com.example.torndirector.ui.company.CompanyFragment
import com.example.torndirector.ui.emploeeListView.EmployeesListFragment
import com.example.torndirector.ui.settings.SettingsFragment
import com.example.torndirector.utils.ExpeditedWorker
import com.example.torndirector.utils.FetchingDataClass
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/*
Pull to refresh
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavBar: BottomNavigationView

    @Inject
    lateinit var fetchingDataClass : FetchingDataClass


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getData(this)

        bottomNavBar = findViewById(R.id.bottom_navigation)
        bottomNavBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.companyAction -> {
                    onCompanyAction()
                    true
                }
                R.id.employeesAction -> {
                    onEmployeeAction()
                    true
                }
                R.id.settingsAction -> {
                    onSettingsAction()
                    true
                }
                else -> false
            }
        }
    }

    private fun onEmployeeAction() {
        supportFragmentManager.commitNow {
            replace(R.id.fragment_container_view, EmployeesListFragment())
        }
    }

    private fun onCompanyAction() {
        supportFragmentManager.commitNow {
            replace(R.id.fragment_container_view, CompanyFragment())
        }
    }

    private fun onSettingsAction() {
        supportFragmentManager.commitNow {
            replace(R.id.fragment_container_view, SettingsFragment())
        }
    }

    private fun getData(activity: MainActivity) {
        val periodicRequest = PeriodicWorkRequestBuilder<ExpeditedWorker>(
            1, TimeUnit.HOURS)
            .build()
        WorkManager.getInstance(applicationContext).enqueue(periodicRequest)
    }

}
