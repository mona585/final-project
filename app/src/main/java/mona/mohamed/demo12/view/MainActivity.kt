package mona.mohamed.demo12.view

import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import mona.mohamed.demo12.R
import mona.mohamed.demo12.model.AuthRepositoryImp
import mona.mohamed.demo12.viewmodel.AuthViewModel
import mona.mohamed.demo12.viewmodel.AuthViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: AuthViewModel // viewModel object declaration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Views assigning
        val inputName: EditText = findViewById(R.id.editTextText)
        val inputEmail: EditText = findViewById(R.id.editTextTextEmailAddress)
        val inputPassword: EditText = findViewById(R.id.editTextTextPassword)

        // viewModelFactory and viewModel assignment
        val viewModelFactory = AuthViewModelFactory(AuthRepositoryImp(this))
        viewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]


    }
}