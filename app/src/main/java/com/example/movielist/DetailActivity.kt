package com.example.movielist

import Database.GlobalVar.Companion.listDataMovie
import Model.Movie
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.movielist.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private var position = -1
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        GetIntent()
        listener()

    }

    private fun listener() {
        binding.BackButMovDet.setOnClickListener {
            val myIntent = Intent(this, HomeActivity::class.java)
            startActivity(myIntent)
        }
        binding.DeleteBut.setOnClickListener {
            listDataMovie.removeAt(position)
            Toast.makeText(this, "Delete Success", Toast.LENGTH_SHORT).show()
            val myIntent = Intent(this, HomeActivity::class.java)
            startActivity(myIntent)
        }
        binding.EditBut.setOnClickListener {
            val myIntent = Intent(this, CreateActivity::class.java).apply {
                putExtra("position", position)
            }
            startActivity(myIntent)
        }
    }

    private fun GetIntent() {
        position = intent.getIntExtra("position", -1)
        val movie = listDataMovie[position]
        Display(movie)
    }

    private fun Display(movie: Movie) {
        binding.ClassTitleTextView.text = movie.classname
        binding.AmountTextView.text = movie.amount
        binding.MajorTextView.text = movie.major
        binding.TeacherTextView.text = movie.teacher
        binding.DescriptionTextView.text = movie.description
        var temp = Uri.parse(movie.imageUri)

        if (movie.imageUri!!.isNotEmpty()) {
            binding.previewPoster.setImageURI(temp)
        }
    }

    override fun onResume() {
        super.onResume()
        val movie = listDataMovie[position]
        Display(movie)
    }

}
