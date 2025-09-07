package mona.mohamed.recipeapp.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import mona.mohamed.recipeapp.R
import mona.mohamed.recipeapp.RecipeApplication
import mona.mohamed.recipeapp.data.local.RecipeDatabase
import mona.mohamed.recipeapp.data.models.Meal
import mona.mohamed.recipeapp.repository.FavoriteRepository
import mona.mohamed.recipeapp.databinding.FragmentMealDetailBinding
import mona.mohamed.recipeapp.view.mealDetail.MealDetailViewModel
import mona.mohamed.recipeapp.view.mealDetail.MealDetailViewModelFactory

class MealDetailFragment : Fragment() {

    private var _binding: FragmentMealDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MealDetailViewModel
    private val args: MealDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize dependencies
        val database = RecipeDatabase.getInstance(requireContext())
        val authRepository = (requireActivity().application as RecipeApplication).authRepository
        val favoriteRepository = FavoriteRepository(database.favoriteMealDao(), authRepository)

        // Initialize ViewModel with factory
        val factory = MealDetailViewModelFactory(favoriteRepository)
        viewModel = ViewModelProvider(this, factory).get(MealDetailViewModel::class.java)

        setupToolbar()
        setupWebView()
        observeData()
        setupFavoriteButton()

        // Load meal details
        viewModel.loadMealDetails(args.mealId)
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupWebView() {
        binding.webViewVideo.apply {
            webChromeClient = WebChromeClient()
            settings.apply {
                javaScriptEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
            }
        }
    }

    private fun observeData() {
        viewModel.meal.observe(viewLifecycleOwner) { meal ->
            meal?.let { displayMealDetails(it) }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            updateFavoriteIcon(isFavorite)
        }
    }

    private fun setupFavoriteButton() {
        binding.fabFavorite.setOnClickListener {
            viewModel.toggleFavorite()
        }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        binding.fabFavorite.setImageResource(
            if (isFavorite) R.drawable.ic_favorite
            else R.drawable.ic_favorite_border
        )
    }

    private fun displayMealDetails(meal: Meal) {
        binding.apply {
            // Set meal image
            Glide.with(requireContext())
                .load(meal.strMealThumb)
                .placeholder(R.drawable.placeholder)
                .into(ivMealImage)

            // Set meal name
            tvMealName.text = meal.strMeal
            toolbar.title = meal.strMeal

            // Set category and area
            meal.strCategory?.let { chipCategory.text = it }
            meal.strArea?.let { chipArea.text = it }

            // Set ingredients
            displayIngredients(meal.getIngredientsList())

            // Set instructions
            tvInstructions.text = meal.strInstructions

            // Set up YouTube video
            meal.strYoutube?.let { youtubeUrl ->
                setupYouTubeVideo(youtubeUrl)
            }
        }
    }

    private fun displayIngredients(ingredients: List<Pair<String, String>>) {
        binding.ingredientsContainer.removeAllViews()

        ingredients.forEach { (ingredient, measure) ->
            val ingredientView = LayoutInflater.from(context)
                .inflate(android.R.layout.simple_list_item_1, binding.ingredientsContainer, false)

            val textView = ingredientView.findViewById<TextView>(android.R.id.text1)
            textView.text = "• $measure $ingredient"
            textView.setTextColor(resources.getColor(R.color.secondary_text))

            binding.ingredientsContainer.addView(ingredientView)
        }
    }

    private fun setupYouTubeVideo(youtubeUrl: String) {
        val videoId = extractYouTubeVideoId(youtubeUrl)

        if (videoId != null) {
            binding.cardVideo.visibility = View.VISIBLE

            val html = """
                <html>
                <body style="margin:0;padding:0;">
                    <iframe 
                        width="100%" 
                        height="100%" 
                        src="https://www.youtube.com/embed/$videoId" 
                        frameborder="0" 
                        allowfullscreen>
                    </iframe>
                </body>
                </html>
            """.trimIndent()

            binding.webViewVideo.loadData(html, "text/html", "utf-8")
        }
    }

    private fun extractYouTubeVideoId(youtubeUrl: String): String? {
        val patterns = listOf(
            "v=([^&]+)",
            "youtu.be/([^?]+)",
            "embed/([^?]+)"
        )

        for (pattern in patterns) {
            val regex = Regex(pattern)
            val matchResult = regex.find(youtubeUrl)
            if (matchResult != null) {
                return matchResult.groupValues[1]
            }
        }
        return null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}