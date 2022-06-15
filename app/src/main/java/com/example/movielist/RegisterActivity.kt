package com.example.movielist

import Database.GlobalVar
import Model.Teach
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.movielist.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var viewBind: ActivityRegisterBinding
    private lateinit var guru: Teach
    private var position = -1
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityRegisterBinding.inflate(layoutInflater)
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
        viewBind.UsernameRegisterLayout.editText?.setText(guru.name)
        viewBind.EmailRegisterLayout.editText?.setText(guru.email)
        viewBind.PasswordRegisterLayout.editText?.setText(guru.password)
        viewBind.RePasswordRegisterLayout.editText?.setText(guru.repassword)
    }

    private fun Listener() {
        viewBind.RegisterButton.setOnClickListener {
            var username = viewBind.UsernameRegisterLayout.editText?.text.toString().trim()
            var email = viewBind.EmailRegisterLayout.editText?.text.toString().trim()
            var password = viewBind.PasswordRegisterLayout.editText?.text.toString().trim()
            var repassword = viewBind.RePasswordRegisterLayout.editText?.text.toString().trim()

            guru = Teach(username, email, password, repassword)

            checker()
        }

        viewBind.RedirectLoginButton.setOnClickListener {
            val myIntent = Intent(this, LoginActivity::class.java)
            startActivity(myIntent)
        }
    }

    private fun checker() {
        var isCompleted: Boolean = true

        //nama
        if (guru.name!!.isEmpty()) {
            viewBind.UsernameRegisterLayout.error = "Tolong isi kolom nama"
            isCompleted = false
        } else {
            viewBind.UsernameRegisterLayout.error = ""
        }

        //Email
        if (guru.email!!.isEmpty()) {
            viewBind.EmailRegisterLayout.error = "Tolong isi kolom email"
            isCompleted = false
        } else {
            // Berguna untuk cek apakah input merupakan email
            if (!Patterns.EMAIL_ADDRESS.matcher(guru.email).matches()) {
                viewBind.EmailRegisterLayout.error = "Tolong masukan alamat email yang benar"
                isCompleted = false
            } else {
                viewBind.EmailRegisterLayout.error = ""
            }
        }

        // Password
        if (guru.password!!.isEmpty()) {
            viewBind.PasswordRegisterLayout.error = "Tolong isi kolom password"
            isCompleted = false
        } else {
            if (guru.password!!.length < 8) {
                viewBind.PasswordRegisterLayout.error = "Jumlah password min 8 karakter"
                isCompleted = false
            } else if (!guru.password!!.matches(".*[a-z].*".toRegex())) {
                viewBind.PasswordRegisterLayout.error = "Password tidak memiliki huruf kecil"
                isCompleted = false
            } else if (!guru.password!!.matches(".*[A-Z].*".toRegex())) {
                viewBind.PasswordRegisterLayout.error = "Password tidak memiliki huruf kapital"
                isCompleted = false
            } else if (!guru.password!!.equals(guru.repassword)) {
                viewBind.RePasswordRegisterLayout.error = "Password berbeda"
                isCompleted = false
            } else if (guru.repassword!!.isEmpty()) {
                viewBind.RePasswordRegisterLayout.error = "Tolong ulangi password"
                isCompleted = false
            } else {
                viewBind.PasswordRegisterLayout.error = ""
                viewBind.RePasswordRegisterLayout.error = ""
            }
        }

        if (isCompleted) {
            if (position == -1) {
                var finalEmail = viewBind.EmailRegisterLayout.editText?.text.toString().trim()
                var finalPassword = viewBind.PasswordRegisterLayout.editText?.text.toString().trim()
                firebaseAuth.createUserWithEmailAndPassword(finalEmail, finalPassword)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val myIntent = Intent(this, LoginActivity::class.java)
                            startActivity(myIntent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}