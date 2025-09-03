package mona.mohamed.recipeapp.viewmodel

import androidx.lifecycle.ViewModel
import mona.mohamed.recipeapp.model.AuthRepository
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

    fun isUserLoggedIn(): Boolean {
        var result = false
        viewModelScope.launch {
            result = authRepository.isLoggedIn()
//            if () {
//                // There's a user logged in already [navigate to homeFragment]
//            } else {
//                // No user is logged in at the moment [navigate to loginFragment]
//            }
        }
        return result
    }

    fun register(name: String, email: String, password: String): Boolean {
        var result = false
        viewModelScope.launch {
            result = authRepository.signUp(email, password)
//            if (result) {
//                // *STILL UNDER DEVELOPMENT* send validation link to user email and [Navigate to validationFragment]
//            } else {
//                // Registration problem [Show error message]
//            }
        }
        return result
    }

    fun login(email: String, password: String): Boolean {
        var result = false
        viewModelScope.launch{
            result = authRepository.signIn(email, password)
//            if (result) {
//                authRepository.setLoggedIn(true)
//                // Login successful [Navigate to homeFragment]
//            } else {
//                // Login failed [Show error message]
//            }
        }
        return result
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.signOut()
            authRepository.setLoggedIn(false)
        }
    }

    fun setUserName(name: String) {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            authRepository.setCurrentUserName(user!!, name)
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

    fun getUserId(): String {
        var userid = ""
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            userid = authRepository.getCurrentUserId(user!!)!!
        }
        return userid
    }
}