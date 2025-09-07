package mona.mohamed.recipeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mona.mohamed.recipeapp.data.repository.AuthRepository

class AuthViewModelFactory(val authRepository: AuthRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java))
            return AuthViewModel(authRepository) as T
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}