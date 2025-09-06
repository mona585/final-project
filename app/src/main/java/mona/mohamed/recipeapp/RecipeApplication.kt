package mona.mohamed.recipeapp

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class RecipeApplication : Application() {

    companion object {
        private const val PREFS_NAME = "recipe_app_prefs"
        private const val KEY_DARK_MODE = "dark_mode"
    }

    override fun onCreate() {
        super.onCreate()

        // Apply dark mode on app startup
        applyDarkModeFromPreferences()
    }

    private fun applyDarkModeFromPreferences() {
        val sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isDarkMode = sharedPref.getBoolean(KEY_DARK_MODE, false)

        val mode = if (isDarkMode) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}