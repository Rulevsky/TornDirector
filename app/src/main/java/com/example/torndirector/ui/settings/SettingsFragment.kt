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
import com.example.torndirector.repositories.SettingsRepository
import com.example.torndirector.room.Settings
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.settings_fragment) {
    @Inject
    lateinit var settingsRepository: SettingsRepository

    var key : String = "init"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        key = readApiKey()
    }

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
        saveBtn.setOnClickListener { onSaveBtnClick(apiKeyEditText) }
        apiKeyEditText.setText(key)

        Log.e("tag", "proverka sveazi")

    }

    private fun onSaveBtnClick(apiKey: EditText) {
        CoroutineScope(SupervisorJob()).launch() { writeApiKey(apiKey.text.toString()) }
        Log.e("tag", "savebtn")
    }

    private fun readApiKey(): String {
        CoroutineScope(SupervisorJob()).launch() {
            var settings = settingsRepository.findSettingsByName("apiKey")
            if (settings != null) {
                key = settings.settingsValue
            } else {
                key = "EnterKey"
                settingsRepository.insert(Settings(1, "apiKey", key))
            }
        }
        return key
    }

    private fun writeApiKey(apiKey: String) {
        CoroutineScope(SupervisorJob()).launch() {
            settingsRepository.update(Settings(1, "apiKey", apiKey.toString()))
            Log.e("tag", "write")
        }
    }
}


