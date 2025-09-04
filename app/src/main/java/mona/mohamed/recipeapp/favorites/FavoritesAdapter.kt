package mona.mohamed.recipeapp.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mona.mohamed.recipeapp.data.FavoriteEntity
import mona.mohamed.recipeapp.databinding.ItemFavoriteBinding

class FavoritesAdapter(
    private val onDeleteClick: (FavoriteEntity) -> Unit
) : ListAdapter<FavoriteEntity, FavoritesAdapter.FavoritesViewHolder>(DiffCallback()) {

    class FavoritesViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FavoriteEntity, onDeleteClick: (FavoriteEntity) -> Unit) {
            binding.mealName.text = item.mealName
            binding.deleteButton.setOnClickListener { onDeleteClick(item) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<FavoriteEntity>() {
        override fun areItemsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(getItem(position), onDeleteClick)
    }
}
