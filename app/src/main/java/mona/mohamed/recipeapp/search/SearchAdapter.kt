package mona.mohamed.recipeapp.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mona.mohamed.recipeapp.databinding.ItemSearchResultBinding

class SearchAdapter(
    private val onItemClick: (Meal) -> Unit
) : ListAdapter<Meal, SearchAdapter.SearchViewHolder>(DiffCallback()) {

    class SearchViewHolder(private val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal, onItemClick: (Meal) -> Unit) {
            binding.mealName.text = meal.name
            binding.root.setOnClickListener { onItemClick(meal) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Meal, newItem: Meal) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchResultBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }
}
