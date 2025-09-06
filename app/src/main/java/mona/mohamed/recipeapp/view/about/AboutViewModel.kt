package mona.mohamed.recipeapp.view.about

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutViewModel(private val context: Context) : ViewModel() {

    private val sharedPref = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)

    private val _isDarkMode = MutableLiveData<Boolean>()
    val isDarkMode: LiveData<Boolean> = _isDarkMode

    private val _notificationsEnabled = MutableLiveData<Boolean>()
    val notificationsEnabled: LiveData<Boolean> = _notificationsEnabled

    init {
        loadPreferences()
    }

    private fun loadPreferences() {
        _isDarkMode.value = sharedPref.getBoolean("dark_mode", false)
        _notificationsEnabled.value = sharedPref.getBoolean("notifications", true)
    }

    fun setDarkMode(enabled: Boolean) {
        _isDarkMode.value = enabled
        sharedPref.edit().putBoolean("dark_mode", enabled).apply()
    }

    fun setNotifications(enabled: Boolean) {
        _notificationsEnabled.value = enabled
        sharedPref.edit().putBoolean("notifications", enabled).apply()
    }

    fun clearAllData() {
        sharedPref.edit().clear().apply()
    }
}