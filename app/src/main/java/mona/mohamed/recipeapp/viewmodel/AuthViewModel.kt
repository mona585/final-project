package mona.mohamed.recipeapp.viewmodel

import androidx.lifecycle.ViewModel
import mona.mohamed.recipeapp.model.AuthRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository): ViewModel() {
    fun updateIsLoggedIn() {
        val user = authRepository.getCurrentUser()
        if (user != null) {
            authRepository.setLoggedIn(true)
        } else {
            authRepository.setLoggedIn(false)
        }
    }

    fun isUserLoggedIn() = authRepository.isLoggedIn()

    suspend fun isVerified(): Boolean {
        val user = authRepository.getCurrentUser()
        authRepository.reloadUser(user!!)
        return authRepository.isEmailVerified(user)
    }

    suspend fun sendVerification(): Boolean {
        val user = authRepository.getCurrentUser()
        return authRepository.sendEmailVerification(user!!)
    }

    suspend fun register(email: String, password: String): Boolean {
        val result = authRepository.signUp(email, password)
        authRepository.setLoggedIn(result)
        return result
    }

    suspend fun login(email: String, password: String): Boolean {
        val result = authRepository.signIn(email, password)
        authRepository.setLoggedIn(result)
        return result
    }

    fun logout() {
        authRepository.signOut()
        authRepository.setLoggedIn(false)
    }

    fun setUserName(name: String) {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            authRepository.setCurrentUserName(user!!, name)
        }
    }

    suspend fun getUserName(): String {
        val user = authRepository.getCurrentUser()
        authRepository.reloadUser(user!!)
        return authRepository.getCurrentUserName(user)!!
    }

    suspend fun getUserId(): String {
        val user = authRepository.getCurrentUser()
        authRepository.reloadUser(user!!)
        return authRepository.getCurrentUserId(user)!!
    }
}