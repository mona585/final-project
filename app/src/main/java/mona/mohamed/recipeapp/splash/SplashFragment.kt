package mona.mohamed.recipeapp.splash

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import mona.mohamed.recipeapp.databinding.FragmentSplashBinding
import mona.mohamed.recipeapp.R

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logo = binding.logoImage
        val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        logo.startAnimation(fadeIn)

        view.postDelayed({
            val isLoggedIn = requireContext()
                .getSharedPreferences("prefs", Context.MODE_PRIVATE)
                .getBoolean("isLoggedIn", false)

            if (isLoggedIn) {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }, 2000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
