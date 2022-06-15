package com.example.movielist

import Database.GlobalVar
import Model.Teach
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movielist.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    private lateinit var viewBind: ActivityLoginBinding
    private var position = -1
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBind.root)

        supportActionBar?.hide()
        firebaseAuth = FirebaseAuth.getInstance()
        GetIntent()
        Listener()
    }

    private fun GetIntent() {
        position = intent.getIntExtra("position", -1)
        if (position != -1) {
            val guru = GlobalVar.listDataTeach[position]
            Display(guru)
        }
    }

    private fun Display(guru: Teach) {
        viewBind.EmailLoginLayout.editText?.setText(guru.name)
        viewBind.PasswordLoginLayout.editText?.setText(guru.password)
    }

    private fun Listener() {
        viewBind.LoginButton.setOnClickListener {
            login()
        }
        viewBind.RedirectRegisterButton.setOnClickListener {
            val myIntent = Intent(this, RegisterActivity::class.java)
            startActivity(myIntent)
        }
    }

    private fun login() {
        if (position == -1) {
            var username = viewBind.EmailLoginLayout.editText?.text.toString().trim()
            var password = viewBind.PasswordLoginLayout.editText?.text.toString().trim()
            firebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val myIntent = Intent(this, MainHomeActivity::class.java)
                        startActivity(myIntent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }


}