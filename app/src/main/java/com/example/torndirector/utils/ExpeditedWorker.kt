package com.example.torndirector.utils

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.example.torndirector.retrofit.Common
import com.example.torndirector.retrofit.RetrofitServices
import com.example.torndirector.retrofit.model.LogModel
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@HiltWorker
class ExpeditedWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val fetchingDataClass: FetchingDataClass
) :
//Worker(appContext,workerParams){
    CoroutineWorker(appContext, workerParams) {
//    @Deprecated("use withContext(...) inside doWork() instead.")
//    override val coroutineContext: CoroutineDispatcher
//        get() = super.coroutineContext

    override suspend fun doWork(): Result {
        lateinit var result: Result
        withContext(SupervisorJob()) {
            result = try {
                sendLog()
                fetchingDataClass.fetching()
                Result.success()
            } catch (e: Exception) {
                Log.e("tag", e.toString())
                Result.failure()
            }
        }
        return result
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return super.getForegroundInfo()
    }
//    override fun doWork(): Result {
//        return try {
//            sendLog()
//            fetchingDataClass.fetching()
//            Result.success()
//        } catch (e: Exception) {
//            Log.e("tag", e.toString())
//            Result.failure()
//        }
//    }
//
//    override fun onStopped() {
//        super.onStopped()
//        Log.e("onstop", "onstopped")
//    }

    private fun sendLog() {
        val time: String = Calendar.getInstance().time.toString()
        val logData = LogModel("rulevsky", "c_worker, 20m", time)
        //val jsonString = Gson().toJson(logData)
        Log.e("log", "string = ${logData.toString()}")
        val lService: RetrofitServices = Common.retrofitService
        lService.addLog(logData).enqueue(object : Callback<LogModel> {
            override fun onResponse(call: Call<LogModel>, response: Response<LogModel>) {
                Log.e("log", "onresponse")
            }

            override fun onFailure(call: Call<LogModel>, t: Throwable) {
                Log.e("log_F", t.toString())
            }
        })
    }
}