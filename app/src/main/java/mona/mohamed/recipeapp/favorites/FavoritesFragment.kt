package mona.mohamed.recipeapp.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import mona.mohamed.recipeapp.data.AppDatabase
import mona.mohamed.recipeapp.databinding.FragmentFavoritesBinding
import mona.mohamed.recipeapp.repository.FavoritesRepository
import mona.mohamed.recipeapp.viewmodel.FavoritesViewModel
import mona.mohamed.recipeapp.viewmodel.FavoritesViewModelFactory

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var adapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dao = AppDatabase.getDatabase(requireContext()).favoriteDao()
        val repository = FavoritesRepository(dao)
        val factory = FavoritesViewModelFactory(repository)
        favoritesViewModel = ViewModelProvider(this, factory)[FavoritesViewModel::class.java]

        adapter = FavoritesAdapter { favorite ->
            showDeleteDialog(favorite)
        }

        binding.favoritesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.favoritesRecyclerView.adapter = adapter

        favoritesViewModel.getFavorites("dummyUserId").observe(viewLifecycleOwner) { favorites ->
            if (favorites.isEmpty()) {
                binding.emptyStateText.visibility = View.VISIBLE
                binding.favoritesRecyclerView.visibility = View.GONE
            } else {
                binding.emptyStateText.visibility = View.GONE
                binding.favoritesRecyclerView.visibility = View.VISIBLE
                adapter.submitList(favorites)
            }
        }
    }

    private fun showDeleteDialog(favorite: mona.mohamed.recipeapp.data.FavoriteEntity) {
        AlertDialog.Builder(requireContext())
            .setTitle("Remove Favorite")
            .setMessage("Are you sure you want to remove this meal from your favorites?")
            .setPositiveButton("Delete") { _, _ ->
                favoritesViewModel.removeFavorite(favorite)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
