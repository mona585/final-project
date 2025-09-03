package mona.mohamed.recipeapp.model

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun getCurrentUser(): FirebaseUser?

    suspend fun signIn(email: String, password: String): Boolean

    suspend fun signOut()

    suspend fun signUp(email: String, password: String): Boolean

    suspend fun setCurrentUserName(user: FirebaseUser, name: String)

    suspend fun getCurrentUserName(user: FirebaseUser): String?

    suspend fun getCurrentUserId(user: FirebaseUser): String?

    suspend fun isEmailVerified(user: FirebaseUser): Boolean

    suspend fun sendEmailVerification(user: FirebaseUser): Boolean

    suspend fun setLoggedIn(isLoggedIn: Boolean)

    suspend fun isLoggedIn(): Boolean
}