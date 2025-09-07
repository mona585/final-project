package mona.mohamed.recipeapp.view


import android.content.Intent
import androidx.fragment.app.Fragment

fun Fragment.navigateToRecipeActivity() {
    val intent = Intent(requireActivity(), RecipeActivity::class.java)
    startActivity(intent)
    requireActivity().finish()
}