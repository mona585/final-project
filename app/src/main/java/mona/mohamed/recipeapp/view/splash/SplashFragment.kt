package mona.mohamed.recipeapp.view.splash

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import mona.mohamed.recipeapp.databinding.FragmentSplashBinding
import mona.mohamed.recipeapp.R
import mona.mohamed.recipeapp.model.AuthRepositoryImp
import mona.mohamed.recipeapp.viewmodel.AuthViewModel
import mona.mohamed.recipeapp.viewmodel.AuthViewModelFactory

import mona.mohamed.recipeapp.navigateToRecipeActivity
class SplashFragment : Fragment() {
    private val viewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory(AuthRepositoryImp(requireContext()))
    }
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

//        val logo = binding.logoImage
//        val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
//        logo.startAnimation(fadeIn)

//        view.postDelayed({
//
//        }, 5000)

        val logo = view.findViewById<ImageView>(R.id.logoImage)
        val splashAnimation = view.findViewById<LottieAnimationView>(R.id.splashAnimation)

        // 1. Fade in logo
        logo.animate()
            .alpha(1f)
            .setDuration(800) // fade in duration
            .withEndAction {
                // 2. Keep logo visible for 1.2s
                logo.postDelayed({
                    // 3. Fade out logo
                    logo.animate()
                        .alpha(0f)
                        .setDuration(600)
                        .withEndAction {
                            // 4. Fade in and play Lottie
                            splashAnimation.animate()
                                .alpha(1f)
                                .setDuration(600)
                                .withEndAction {
                                    splashAnimation.playAnimation()

                                    // 5. Go to MainActivity when animation ends
                                    splashAnimation.addAnimatorListener(object : Animator.AnimatorListener {
                                        override fun onAnimationStart(animation: Animator) {}
                                        override fun onAnimationCancel(animation: Animator) {}
                                        override fun onAnimationRepeat(animation: Animator) {}

                                        override fun onAnimationEnd(animation: Animator) {
//                                            viewModel.updateIsLoggedIn()
                                            if (viewModel.isUserLoggedIn()) {
                                                navigateToRecipeActivity()
                                            } else {
                                                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                                            }
                                        }
                                    })
                                }
                        }
                }, 1200) // delay before fading out logo
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}