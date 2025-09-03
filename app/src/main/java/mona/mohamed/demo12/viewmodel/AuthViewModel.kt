package mona.mohamed.demo12.viewmodel

import androidx.lifecycle.ViewModel
import mona.mohamed.demo12.model.AuthRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository): ViewModel() {
    fun updateIsLoggedIn() {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            if (user != null) {
                authRepository.setLoggedIn(true)
            } else {
                authRepository.setLoggedIn(false)
            }
        }
    }

    fun splashScreen() {
        viewModelScope.launch {
            if (authRepository.isLoggedIn()) {
                // There's a user logged in already [navigate to homeFragment]
            } else {
                // No user is logged in at the moment [navigate to loginFragment]
            }
        }
    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.signUp(email, password)
            if (result) {
                // *STILL UNDER DEVELOPMENT* send validation link to user email and [Navigate to validationFragment]
            } else {
                // Registration problem [Show error message]
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch{
            val result = authRepository.signIn(email, password)
            if (result) {
                authRepository.setLoggedIn(true)
                // Login successful [Navigate to homeFragment]
            } else {
                // Login failed [Show error message]
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.signOut()
            authRepository.setLoggedIn(false)
        }
    }

    fun getUserName(): String {
        var username = ""
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            username = authRepository.getCurrentUserName(user!!)!!
        }
        return username
    }

    fun getId(): String {
        var userid = ""
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            userid = authRepository.getCurrentUserId(user!!)!!
        }
        return userid
    }
}