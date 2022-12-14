package com.example.donatr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.donatr.adapter.FirestoreAdapter
import com.example.donatr.data.User
import com.example.donatr.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            registerUser()
        }

        binding.btnLogin.setOnClickListener {
            loginUser()
        }
    }


    fun registerUser() {
        if (isFormValid()) {
            val authInstance = FirebaseAuth.getInstance()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            authInstance.createUserWithEmailAndPassword(
                email, password
            ).addOnSuccessListener {

                val newUser = User(
                    it.user!!.uid,
                    email,
                )

                FirestoreAdapter(this).addToCollection(
                    FirestoreAdapter.COLLECTION_USERS,
                    newUser
                )

                Toast.makeText(
                    this,
                    getString(R.string.succesfulRegistration),
                    Toast.LENGTH_LONG
                ).show()
            }.addOnFailureListener{
                Toast.makeText(
                    this,
                    getString(R.string.Error) + "${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    fun loginUser() {
        if (isFormValid()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            ).addOnSuccessListener {
                Toast.makeText(
                    this,
                    getString(R.string.loginOK),
                    Toast.LENGTH_LONG
                ).show()

                startActivity(Intent(this, MainActivity::class.java))
            }.addOnFailureListener{
                Toast.makeText(
                    this,
                    getString(R.string.Error) + "${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    fun isFormValid(): Boolean {
        return when {
            binding.etEmail.text.isEmpty() -> {
                binding.etEmail.error = getString(R.string.nonemptyFieldErrormsg)
                false
            }
            binding.etPassword.text.isEmpty() -> {
                binding.etPassword.error = getString(R.string.nonemptyPasswordErrormsg)
                false
            }
            else -> true
        }
    }

}