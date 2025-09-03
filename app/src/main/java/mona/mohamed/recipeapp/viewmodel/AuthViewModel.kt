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
        }
        return result
    }

    fun isVerified(): Boolean {
        var result = false
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            result = authRepository.isEmailVerified(user!!)
        }
        return result
    }

    fun sendVerification(): Boolean {
        var result = false
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            result = authRepository.sendEmailVerification(user!!)
        }
        return result
    }

    fun register(email: String, password: String): Boolean {
        var result = false
        viewModelScope.launch {
            result = authRepository.signUp(email, password)
            authRepository.setLoggedIn(result)
        }
        return result
    }

    fun login(email: String, password: String): Boolean {
        var result = false
        viewModelScope.launch{
            result = authRepository.signIn(email, password)
            authRepository.setLoggedIn(result)
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