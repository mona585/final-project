package mona.mohamed.recipeapp.view

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import mona.mohamed.recipeapp.data.repository.AuthRepository
import mona.mohamed.recipeapp.data.repository.AuthRepositoryImp

class RecipeApplication : Application() {

    companion object {
        private const val PREFS_NAME = "recipe_app_prefs"
        private const val KEY_DARK_MODE = "dark_mode"

    }
    lateinit var authRepository: AuthRepository

    override fun onCreate() {
        super.onCreate()
        authRepository = AuthRepositoryImp(this)
        // Apply dark mode on app startup
        applyDarkModeFromPreferences()
    }

    private fun applyDarkModeFromPreferences() {
        val sharedPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val isDarkMode = sharedPref.getBoolean(KEY_DARK_MODE, false)

        val mode = if (isDarkMode) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}