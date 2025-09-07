package mona.mohamed.recipeapp.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.launch
import mona.mohamed.recipeapp.view.RecipeApplication
import mona.mohamed.recipeapp.data.local.FavoriteMeal
import mona.mohamed.recipeapp.data.local.RecipeDatabase
import mona.mohamed.recipeapp.data.repository.FavoriteRepository
import mona.mohamed.recipeapp.databinding.FragmentFavoritesBinding
import mona.mohamed.recipeapp.viewmodel.FavoritesViewModel
import mona.mohamed.recipeapp.viewmodel.FavoritesViewModelFactory

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var adapter: FavoritesAdapter
    private lateinit var favoriteRepository: FavoriteRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDependencies()
        setupRecyclerView()
        observeFavorites()
    }

    private fun setupDependencies() {
        // Initialize dependencies
        val database = RecipeDatabase.getInstance(requireContext())
        val authRepository = (requireActivity().application as RecipeApplication).authRepository
        favoriteRepository = FavoriteRepository(database.favoriteMealDao(), authRepository)

        // Initialize ViewModel
        val factory = FavoritesViewModelFactory(favoriteRepository)
        viewModel = ViewModelProvider(this, factory)[FavoritesViewModel::class.java]
    }

    private fun setupRecyclerView() {
        adapter = FavoritesAdapter(
            onMealClick = { favoriteMeal ->
                navigateToMealDetail(favoriteMeal.mealId)
            },
            onDeleteClick = { favoriteMeal ->
                showDeleteDialog(favoriteMeal)
            }
        )

        binding.favoritesRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@FavoritesFragment.adapter
        }
    }

    private fun observeFavorites() {
        viewModel.favorites.observe(viewLifecycleOwner) { favorites ->
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

    private fun navigateToMealDetail(mealId: String) {
        // Navigate to meal detail using Navigation Component
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToMealDetailFragment(mealId)
        findNavController().navigate(action)
    }

    private fun showDeleteDialog(favorite: FavoriteMeal) {
        AlertDialog.Builder(requireContext())
            .setTitle("Remove Favorite")
            .setMessage("Are you sure you want to remove ${favorite.mealName} from your favorites?")
            .setPositiveButton("Remove") { _, _ ->
                lifecycleScope.launch {
                    favoriteRepository.removeFromFavorites(favorite.mealId)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}