package mona.mohamed.recipeapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mona.mohamed.recipeapp.data.repository.AuthRepository

class AboutViewModelFactory(
    private val context: Context,
    private val authRepository: AuthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AboutViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AboutViewModel(context, authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}