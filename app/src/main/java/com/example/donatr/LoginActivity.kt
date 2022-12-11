package com.example.donatr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.donatr.adapter.FirestoreAdapter
import com.example.donatr.data.User
import com.example.donatr.databinding.ActivityLoginBinding
import com.example.donatr.summary.SummaryActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                    "Registration OK",
                    Toast.LENGTH_LONG
                ).show()
            }.addOnFailureListener{
                Toast.makeText(
                    this,
                    "Error: ${it.message}",
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
                    "Login OK",
                    Toast.LENGTH_LONG
                ).show()

//                startActivity(Intent(this, MainActivity::class.java))
                startActivity(Intent(this, SummaryActivity::class.java))
            }.addOnFailureListener{
                Toast.makeText(
                    this,
                    "Error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun isFormValid(): Boolean {
        return when {
            binding.etEmail.text.isEmpty() -> {
                binding.etEmail.error = "This field can not be empty"
                false
            }
            binding.etPassword.text.isEmpty() -> {
                binding.etPassword.error = "The password can not be empty"
                false
            }
            else -> true
        }
    }

}