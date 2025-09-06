package mona.mohamed.recipeapp

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import mona.mohamed.recipeapp.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Add this temporarily in RecipeActivity.onCreate() before anything else
        getSharedPreferences("recipe_app_prefs", Context.MODE_PRIVATE)
            .edit()
            .remove("dark_mode")
            .apply()
        // Inflate the layout
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get references to the bottom navigation and nav controller
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Connect the bottom navigation with the navigation controller
        navView.setupWithNavController(navController)
    }
}