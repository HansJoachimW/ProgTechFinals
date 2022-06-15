package com.example.movielist

import Database.GlobalVar
import Model.Teach
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movielist.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    private var position = -1
    private lateinit var binding: ActivityProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        firebaseAuth = FirebaseAuth.getInstance()
        GetIntent()
        listener()
    }

    private fun listener() {
        binding.BackButtonUserProfile.setOnClickListener {
            val myIntent = Intent(this, MainHomeActivity::class.java)
            startActivity(myIntent)
        }
        binding.LogOutProfile.setOnClickListener {
            val myIntent = Intent(this, LoginActivity::class.java)
            startActivity(myIntent)
        }
    }

    private fun GetIntent() {
        position = intent.getIntExtra("position", -1)
        val guru = GlobalVar.listDataTeach[position]
        Display(guru)
    }

    private fun Display(guru: Teach) {
        binding.usernameProfile.text = guru.name
        binding.usernameProfileInput.text = guru.name
        binding.emailProfileInput.text = guru.email
        var temp = Uri.parse(guru.imageUri)

        if (guru.imageUri!!.isNotEmpty()) {
            binding.imageView6.setImageURI(temp)
        }
    }

    override fun onResume() {
        super.onResume()
        val guru = GlobalVar.listDataTeach[position]
        Display(guru)
    }
}