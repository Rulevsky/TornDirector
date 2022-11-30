package com.example.torndirector.utils


import android.content.Context
import android.content.res.Resources
import android.util.Log

import androidx.fragment.app.FragmentActivity
import com.example.torndirector.R


    fun getApiKey(activity: FragmentActivity?, resources: Resources): String? {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val defaultValue = resources.getString(R.string.enter_apikey)
        val apiKeyText = sharedPref?.getString(R.string.saved_apikey_key.toString(), defaultValue)
        Log.e("tag", apiKeyText.toString())
        return apiKeyText
    }

