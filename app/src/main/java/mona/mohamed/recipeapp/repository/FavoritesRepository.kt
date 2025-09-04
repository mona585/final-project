package mona.mohamed.recipeapp.repository

import mona.mohamed.recipeapp.data.FavoriteDao
import mona.mohamed.recipeapp.data.FavoriteEntity

class FavoritesRepository(private val dao: FavoriteDao) {
    fun getFavorites(userId: String) = dao.getFavorites(userId)
    suspend fun addFavorite(fav: FavoriteEntity) = dao.insertFavorite(fav)
    suspend fun removeFavorite(fav: FavoriteEntity) = dao.deleteFavorite(fav)
    suspend fun isFavorite(mealId: String, userId: String) = dao.isFavorite(mealId, userId)
}