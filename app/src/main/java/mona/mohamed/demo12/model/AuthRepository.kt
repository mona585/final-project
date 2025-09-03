package mona.mohamed.demo12.model

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun getCurrentUser(): FirebaseUser?

    suspend fun signIn(email: String, password: String): Boolean

    suspend fun signOut()

    suspend fun signUp(email: String, password: String): Boolean

    suspend fun setUserName(user: FirebaseUser, name: String): Boolean

    suspend fun getCurrentUserName(user: FirebaseUser): String?

    suspend fun getCurrentUserId(user: FirebaseUser): String?

    suspend fun setLoggedIn(isLoggedIn: Boolean)

    suspend fun isLoggedIn(): Boolean
}