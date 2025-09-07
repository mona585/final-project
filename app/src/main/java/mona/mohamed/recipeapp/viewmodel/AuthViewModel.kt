package mona.mohamed.recipeapp.viewmodel

import androidx.lifecycle.ViewModel
import mona.mohamed.recipeapp.data.repository.AuthRepository

class AuthViewModel(private val authRepository: AuthRepository): ViewModel() {
//    fun updateIsLoggedIn() {
//        val user = authRepository.getCurrentUser()
//        if (user != null) {
//            authRepository.setLoggedIn(true)
//        } else {
//            authRepository.setLoggedIn(false)
//        }
//    }

    fun isUserLoggedIn() = authRepository.isLoggedIn()

    fun setStatus(status: Boolean) = authRepository.setLoggedIn(status)

    suspend fun isVerified(): Boolean {
        val user = authRepository.getCurrentUser()
        authRepository.reloadUser(user!!)
        return authRepository.isEmailVerified(user)
    }

    suspend fun sendVerification(): Boolean {
        val user = authRepository.getCurrentUser()
        return authRepository.sendEmailVerification(user!!)
    }

    suspend fun register(email: String, password: String) = authRepository.signUp(email, password)

    suspend fun login(email: String, password: String) = authRepository.signIn(email, password)

    fun logout() = authRepository.signOut()

    suspend fun setUserName(name: String) {
        val user = authRepository.getCurrentUser()
        authRepository.setCurrentUserName(user!!, name)
    }

    suspend fun getUserName(): String {
        val user = authRepository.getCurrentUser()
        authRepository.reloadUser(user!!)
        return authRepository.getCurrentUserName(user)!!
    }
    suspend fun getUserEmail(): String? {
        return try {
            val user = authRepository.getCurrentUser()
            if (user != null) {
                authRepository.reloadUser(user)
                authRepository.getCurrentUserEmail(user) // You'll need to implement this
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
    suspend fun getUserId(): String {
        val user = authRepository.getCurrentUser()
        authRepository.reloadUser(user!!)
        return authRepository.getCurrentUserId(user)!!
    }
}