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
    private lateinit var guru: Teach
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
            var username = viewBind.EmailLoginLayout.editText?.text.toString().trim()
            var password = viewBind.PasswordLoginLayout.editText?.text.toString().trim()

            checker(username, password)
        }
        viewBind.RedirectRegisterButton.setOnClickListener {
            val myIntent = Intent(this, RegisterActivity::class.java)
            startActivity(myIntent)
        }
    }

    private fun checker(username: String, password: String) {
        var isCompleted: Boolean = true

        //nama
        if (guru.name!!.isEmpty()) {
            viewBind.EmailLoginLayout.error = "Tolong isi kolom nama"
            isCompleted = false
        } else if (!guru.name!!.equals(username)) {
            viewBind.EmailLoginLayout.error = ""
        } else {
            viewBind.EmailLoginLayout.error = ""
        }

        // Password
        if (guru.password!!.isEmpty()) {
            viewBind.PasswordLoginLayout.error = "Tolong isi kolom password"
            isCompleted = false
        } else {
            if (guru.password!!.length < 8) {
                viewBind.PasswordLoginLayout.error = "Jumlah password min 8 karakter"
                isCompleted = false
            } else if (!guru.password!!.matches(".*[a-z].*".toRegex())) {
                viewBind.PasswordLoginLayout.error = "Password tidak memiliki huruf kecil"
                isCompleted = false
            } else if (!guru.password!!.matches(".*[A-Z].*".toRegex())) {
                viewBind.PasswordLoginLayout.error = "Password tidak memiliki huruf kapital"
                isCompleted = false
            } else if (!guru.password!!.equals(guru.repassword)) {
                viewBind.PasswordLoginLayout.error = "Password berbeda"
                isCompleted = false
            } else {
                viewBind.PasswordLoginLayout.error = ""
            }
        }

        if (isCompleted) {
            if (position == -1) {
                var finalEmail = viewBind.EmailLoginLayout.editText?.text.toString().trim()
                var finalPassword = viewBind.PasswordLoginLayout.editText?.text.toString().trim()
                firebaseAuth.signInWithEmailAndPassword(finalEmail, finalPassword)
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

    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser != null) {
            val myIntent = Intent(this, MainHomeActivity::class.java)
            startActivity(myIntent)
        }
    }
}