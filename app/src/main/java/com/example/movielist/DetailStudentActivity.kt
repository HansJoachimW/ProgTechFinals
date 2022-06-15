package com.example.movielist

import Database.GlobalVar.Companion.listDataMovie
import Database.GlobalVar.Companion.listDataStudent
import Model.Student
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.movielist.databinding.ActivityDetailStudentBinding

class DetailStudentActivity : AppCompatActivity() {
    private var position = -1
    private lateinit var binding: ActivityDetailStudentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        GetIntent()
        listener()

    }

    private fun listener() {
        binding.BackButtonDetailStudent.setOnClickListener {
            val myIntent = Intent(this, StudentHome::class.java)
            startActivity(myIntent)
        }
        binding.deleteStudentDetail.setOnClickListener {
            listDataStudent.removeAt(position)
            Toast.makeText(this, "Delete Success", Toast.LENGTH_SHORT).show()
            val myIntent = Intent(this, StudentHome::class.java)
            startActivity(myIntent)
        }
        binding.editStudentDetail.setOnClickListener {
            val myIntent = Intent(this, CreateStudentActivity::class.java).apply {
                putExtra("position", position)
            }
            startActivity(myIntent)
        }
    }

    private fun GetIntent() {
        position = intent.getIntExtra("position", -1)
        val student = listDataStudent[position]
        Display(student)
    }

    private fun Display(student: Student) {
        binding.StudentNameView.text = student.name
        binding.inputAge.text = student.age
        binding.inputMajor.text = student.major
        binding.inputUniversity.text = student.university
        binding.inputInformation.text = student.about
        var temp = Uri.parse(student.imageUri)

        if (student.imageUri!!.isNotEmpty()) {
            binding.previewPoster.setImageURI(temp)
        }
    }

    override fun onResume() {
        super.onResume()
        val student = listDataStudent[position]
        Display(student)
    }

}
