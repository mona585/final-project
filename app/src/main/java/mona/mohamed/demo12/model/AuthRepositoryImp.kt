package mona.mohamed.demo12.model

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import androidx.core.content.edit
import com.google.firebase.auth.FirebaseUser

class AuthRepositoryImp(private val context: Context): AuthRepository {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    private val auth = FirebaseAuth.getInstance()

    override suspend fun getCurrentUser() = auth.currentUser

    override suspend fun signIn(email: String, password: String): Boolean {
        var result = false
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                result = task.isSuccessful
            }
        return result
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun signUp(email: String, password: String): Boolean {
        var result = false
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                result = task.isSuccessful
            }
        return result
    }

    override suspend fun setUserName(user: FirebaseUser, name: String): Boolean {
        var result = false
        val nameUpdate = userProfileChangeRequest { displayName = name }
        user.updateProfile(nameUpdate)
            .addOnCompleteListener { task ->
                result = task.isSuccessful
            }
        return result
    }

    override suspend fun getCurrentUserName(user: FirebaseUser): String? {
        return user.displayName
    }

    override suspend fun getCurrentUserId(user: FirebaseUser): String? {
        return user.uid
    }

    override suspend fun setLoggedIn(isLoggedIn: Boolean) {
        prefs.edit { putBoolean("is_logged_in", isLoggedIn) }
    }

    override suspend fun isLoggedIn(): Boolean {
        return prefs.getBoolean("is_logged_in", false)
    }
}