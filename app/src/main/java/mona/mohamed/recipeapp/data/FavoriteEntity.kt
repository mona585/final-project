package mona.mohamed.recipeapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val id: String,  // mealId
    val mealName: String,
    val userId: String           // Firebase UID
)
