package mona.mohamed.recipeapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mona.mohamed.recipeapp.data.repository.AuthRepository

class AboutViewModel(
    private val context: Context,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val sharedPref = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)

    private val _isDarkMode = MutableLiveData<Boolean>()
    val isDarkMode: LiveData<Boolean> = _isDarkMode

    private val _notificationsEnabled = MutableLiveData<Boolean>()
    val notificationsEnabled: LiveData<Boolean> = _notificationsEnabled

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> = _userEmail

    init {
        loadPreferences()
        loadUserData()
    }

    private fun loadPreferences() {
        _isDarkMode.value = sharedPref.getBoolean("dark_mode", false)
        _notificationsEnabled.value = sharedPref.getBoolean("notifications", true)
    }

    private fun loadUserData() {
        val currentUser = authRepository.getCurrentUser()
        if (currentUser != null) {
            _userName.value = authRepository.getCurrentUserName(currentUser) ?: "Recipe App User"
            _userEmail.value = authRepository.getCurrentUserEmail(currentUser) ?: "user@example.com"
        } else {
            _userName.value = "Recipe App User"
            _userEmail.value = "user@example.com"
        }
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