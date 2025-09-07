package mona.mohamed.recipeapp.data.repository

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import androidx.core.content.edit
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

class AuthRepositoryImp(private val context: Context): AuthRepository {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    private val auth = FirebaseAuth.getInstance()

    override fun getCurrentUser() = auth.currentUser

    override suspend fun reloadUser(user: FirebaseUser): Boolean {
        return try {
            user.reload().await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun signIn(email: String, password: String): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun signOut() = auth.signOut()

    override suspend fun signUp(email: String, password: String): Boolean {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun setCurrentUserName(user: FirebaseUser, name: String) {
        val nameUpdate = userProfileChangeRequest { displayName = name }
        user.updateProfile(nameUpdate)
    }

    override fun getCurrentUserName(user: FirebaseUser) = user.displayName
    override fun getCurrentUserEmail(user: FirebaseUser) = user.email

    override fun getCurrentUserId(user: FirebaseUser) = user.uid

    override fun isEmailVerified(user: FirebaseUser) = user.isEmailVerified

    override suspend fun sendEmailVerification(user: FirebaseUser): Boolean {
        return try {
            user.sendEmailVerification().await()
            true
        } catch (e: Exception) {
            if (e is FirebaseTooManyRequestsException) {
                Toast.makeText(context, "There's a recent email been sent", Toast.LENGTH_LONG).show()
            }
            false
        }
    }

    override fun setLoggedIn(isLoggedIn: Boolean) = prefs.edit { putBoolean("is_logged_in", isLoggedIn) }

    override fun isLoggedIn() = prefs.getBoolean("is_logged_in", false)
}