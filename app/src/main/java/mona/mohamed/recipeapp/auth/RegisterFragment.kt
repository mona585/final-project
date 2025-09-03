package mona.mohamed.recipeapp.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import mona.mohamed.recipeapp.databinding.FragmentRegisterBinding
import mona.mohamed.recipeapp.R

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            val email = binding.emailRegisterEditText.text.toString().trim()
            val password = binding.passwordRegisterEditText.text.toString().trim()

            when {
                email.isEmpty() -> {
                    binding.emailRegisterEditText.error = "Email required"
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    binding.emailRegisterEditText.error = "Invalid email"
                }
                password.isEmpty() -> {
                    binding.passwordRegisterEditText.error = "Password required"
                }
                password.length < 6 -> {
                    binding.passwordRegisterEditText.error = "Password must be at least 6 characters"
                }
                else -> {
                    requireContext().getSharedPreferences("prefs", android.content.Context.MODE_PRIVATE)
                        .edit()
                        .putBoolean("isLoggedIn", true)
                        .apply()

                    findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
