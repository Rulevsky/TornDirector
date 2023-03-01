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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@HiltWorker
class ExpeditedWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val fetchingDataClass: FetchingDataClass
):
CoroutineWorker(appContext, workerParams){
    @Deprecated("use withContext(...) inside doWork() instead.")
    override val coroutineContext: CoroutineDispatcher
        get() = super.coroutineContext

    override suspend fun doWork(): Result {
        return try {
            sendLog()
            fetchingDataClass.fetching()
            Result.success()
        } catch (e: Exception) {
            Log.e("tag", e.toString())
            Result.failure()
        }
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return super.getForegroundInfo()
    }


    fun sendLog(){
        val time : String = Calendar.getInstance().time.toString()
        val logData = LogModel("xiaomi", "background", time)
        val jsonString = Gson().toJson(logData)
        Log.e("log", "string = $jsonString")
        val lService: RetrofitServices = Common.retrofitService
        lService.addLog(jsonString).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.e("log", "onresponse")
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("log_F", t.toString())
            }
        })
    }
}