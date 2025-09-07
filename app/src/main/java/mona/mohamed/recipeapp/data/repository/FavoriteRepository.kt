package mona.mohamed.recipeapp.data.repository

import kotlinx.coroutines.flow.Flow
import mona.mohamed.recipeapp.data.local.FavoriteMeal
import mona.mohamed.recipeapp.data.local.FavoriteMealDao

class FavoriteRepository(
    private val favoriteMealDao: FavoriteMealDao,
    private val authRepository: AuthRepository
) {

    fun getCurrentUserId(): String? {
        val currentUser = authRepository.getCurrentUser()
        return currentUser?.let { authRepository.getCurrentUserId(it) }
    }

    fun getAllFavorites(): Flow<List<FavoriteMeal>>? {
        val userId = getCurrentUserId()
        return userId?.let { favoriteMealDao.getAllFavorites(it) }
    }

    suspend fun toggleFavorite(
        mealId: String,
        mealName: String,
        mealThumb: String,
        category: String? = null,
        area: String? = null
    ): Boolean {
        val userId = getCurrentUserId() ?: return false

        return if (favoriteMealDao.isFavorite(mealId, userId)) {
            favoriteMealDao.deleteFavorite(mealId, userId)
            false // Not favorite anymore
        } else {
            val favorite = FavoriteMeal(
                mealId = mealId,
                userId = userId,
                mealName = mealName,
                mealThumb = mealThumb,
                category = category,
                area = area
            )
            favoriteMealDao.insertFavorite(favorite)
            true // Now is favorite
        }
    }

    suspend fun isFavorite(mealId: String): Boolean {
        val userId = getCurrentUserId() ?: return false
        return favoriteMealDao.isFavorite(mealId, userId)
    }

    suspend fun removeFromFavorites(mealId: String) {
        val userId = getCurrentUserId() ?: return
        favoriteMealDao.deleteFavorite(mealId, userId)
    }
}