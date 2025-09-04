package mona.mohamed.recipeapp


import android.content.Intent
import androidx.fragment.app.Fragment
import mona.mohamed.recipeapp.RecipeActivity

fun Fragment.navigateToRecipeActivity() {
    val intent = Intent(requireActivity(), RecipeActivity::class.java)
    startActivity(intent)
    requireActivity().finish()
}