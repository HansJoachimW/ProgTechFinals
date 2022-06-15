package com.example.movielist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movielist.databinding.ActivityLoginBinding
import com.example.movielist.databinding.ActivityMainHomeBinding
import com.google.firebase.auth.FirebaseAuth

class MainHomeActivity : AppCompatActivity() {
    private lateinit var viewBind: ActivityMainHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityMainHomeBinding.inflate(layoutInflater)
        setContentView(viewBind.root)

        supportActionBar?.hide()
        firebaseAuth = FirebaseAuth.getInstance()
        Listener()
    }

    private fun Listener() {
        viewBind.StudentHomepageImage.setOnClickListener {
            val myIntent = Intent(this, StudentHome::class.java)
            startActivity(myIntent)
        }
        viewBind.ClassHomepageImage.setOnClickListener {
            val myIntent = Intent(this, HomeActivity::class.java)
            startActivity(myIntent)
        }
        viewBind.ProfileButtonHomepage.setOnClickListener {
            val myIntent = Intent(this, ProfileActivity::class.java)
            startActivity(myIntent)
        }
        viewBind.BackButtonMainHome.setOnClickListener {
            firebaseAuth.signOut()
            val myIntent = Intent(this, LoginActivity::class.java)
            startActivity(myIntent)
        }
    }
}