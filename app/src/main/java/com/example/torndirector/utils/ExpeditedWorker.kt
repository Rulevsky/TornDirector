package com.example.torndirector.utils

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher

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
}