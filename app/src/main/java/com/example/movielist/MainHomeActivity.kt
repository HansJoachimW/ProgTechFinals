package com.example.movielist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movielist.databinding.ActivityLoginBinding
import com.example.movielist.databinding.ActivityMainHomeBinding

class MainHomeActivity : AppCompatActivity() {
    private lateinit var viewBind: ActivityMainHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityMainHomeBinding.inflate(layoutInflater)
        setContentView(viewBind.root)

        supportActionBar?.hide()
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
            val myIntent = Intent(this, RegisterActivity::class.java)
            startActivity(myIntent)
        }
    }
}