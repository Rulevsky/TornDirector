package com.example.torndirector.ui.settings

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.torndirector.R

import com.example.torndirector.utils.getApiKey


class SettingsFragment : Fragment(R.layout.settings_fragment) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apiKeyEditText = view.findViewById<EditText>(R.id.apiKeyEditText)
        val saveBtn = view.findViewById<Button>(R.id.saveBtn)
        saveBtn.setOnClickListener { onSaveBtnClick(apiKeyEditText)}
        apiKeyEditText.setText(getApiKey(activity, resources))


    }

    private fun onSaveBtnClick(apiKey: EditText) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(R.string.saved_apikey_key.toString(), apiKey.text.toString())
            apply()
        }
    }

//    private fun getApiKey(): String? {
//        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
//        val defaultValue = resources.getString(R.string.enter_apikey)
//        val apiKeyText = sharedPref?.getString(R.string.saved_apikey_key.toString(), defaultValue)
//        Log.e("tag", apiKeyText.toString())
//        return apiKeyText
//    }


}


