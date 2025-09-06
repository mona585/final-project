package mona.mohamed.recipeapp.model

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun getCurrentUser(): FirebaseUser?

    suspend fun reloadUser(user: FirebaseUser): Boolean

    suspend fun signIn(email: String, password: String): Boolean

    fun signOut()

    suspend fun signUp(email: String, password: String): Boolean

    suspend fun setCurrentUserName(user: FirebaseUser, name: String)

    fun getCurrentUserName(user: FirebaseUser): String?
    fun getCurrentUserEmail(user: FirebaseUser): String?

    fun getCurrentUserId(user: FirebaseUser): String?

    fun isEmailVerified(user: FirebaseUser): Boolean

    suspend fun sendEmailVerification(user: FirebaseUser): Boolean

    fun setLoggedIn(isLoggedIn: Boolean)

    fun isLoggedIn(): Boolean
}