package com.example.movielist

import Database.GlobalVar
import Model.Student
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.example.movielist.databinding.ActivityCreateStudentBinding

class CreateStudentActivity : AppCompatActivity() {
    private lateinit var tempStudent: Student
    var tempUri: String = ""
    private var position = -1
    private val GetResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data
                binding.inputProfile.setImageURI(uri)
                if (uri != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        baseContext.getContentResolver().takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        )
                    }
                    tempUri = uri.toString()
                }
            }
        }
    private lateinit var binding: ActivityCreateStudentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        GetIntent()
        Listener()

    }


    private fun GetIntent() {
        position = intent.getIntExtra("position", -1)
        if (position != -1) {
            binding.AddStudentView.setText("Edit Movie")
            binding.addStudentButton.setText("Save")
            val tempStudent = GlobalVar.listDataStudent[position]
            Display(tempStudent)
        }
    }

    private fun Display(tempStudent: Student?) {
        binding.NameAddStudent.editText?.setText((tempStudent?.name))
        binding.AgeAddStudent.editText?.setText((tempStudent?.age))
        binding.MajorAddStudent.editText?.setText((tempStudent?.major))
        binding.UnivLayout.editText?.setText((tempStudent?.university))
        binding.AboutLayout.editText?.setText((tempStudent?.about))
        binding.inputProfile.setImageURI(Uri.parse(tempStudent?.imageUri))

    }

    private fun Listener() {
        binding.addStudentButton.setOnClickListener {
            var name = binding.NameAddStudent.editText?.text.toString().trim()
            var age = binding.AgeAddStudent.editText?.text.toString().trim()
            var major = binding.MajorAddStudent.editText?.text.toString().trim()
            var university = binding.UnivLayout.editText?.text.toString().trim()
            var about = binding.AboutLayout.editText?.text.toString().trim()

            tempStudent = Student(name, age, major, university, about)
            checker()
        }

        binding.BackButtonAddStudent.setOnClickListener {
            val myIntent = Intent(this, StudentHome::class.java)
            startActivity(myIntent)
        }

        binding.inputProfile.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }
    }


    private fun checker() {
        var isCompleted: Boolean = true
        if (tempStudent.name!!.isEmpty()) {
            binding.NameAddStudent.error = "Nama mahasiswa harus diisi"
            isCompleted = false
        } else {
            binding.NameAddStudent.error = ""
        }

        if (tempStudent.age!!.isEmpty()) {
            binding.AgeAddStudent.error = "Umur mahasiswa harus diisi 1-100"
            isCompleted = false
        } else if (tempStudent.age!!.contains(".*[A-Z].*".toRegex())) {
            binding.AgeAddStudent.error = "Umur mahasiswa tidak boleh ada huruf"
            isCompleted = false
        } else if (tempStudent.age!!.contains(".*[a-z].*".toRegex())) {
            binding.AgeAddStudent.error = "Umur mahasiswa tidak boleh ada huruf"
            isCompleted = false
        } else if (tempStudent.age!!.contains(".*[0-9].*".toRegex())) {
            if (tempStudent.age!!.toInt() > 100) {
                binding.AgeAddStudent.error = "Umur mahasiswa harus 1-100"
                isCompleted = false
            } else {
                binding.AgeAddStudent.error = ""
            }
        } else {
            binding.AgeAddStudent.error = "Umur mahasiswa tidak boleh ada simbol"
            isCompleted = false
        }

        if (tempStudent.major!!.isEmpty()) {
            binding.MajorAddStudent.error = "Major harus diisi"
            isCompleted = false
        } else {
            binding.MajorAddStudent.error = ""
        }

        if (tempStudent.university!!.isEmpty()) {
            binding.UnivLayout.error = "Nama universitas harus diisi"
            isCompleted = false
        } else {
            binding.UnivLayout.error = ""
        }

        if (tempStudent.about!!.isEmpty()) {
            binding.AboutLayout.error = "Deskripsi mahasiswa harus diisi"
            isCompleted = false
        } else {
            binding.AboutLayout.error = ""
        }

        if (isCompleted) {
            if (position == -1) {
                tempStudent.imageUri = tempUri
                GlobalVar.listDataStudent.add(tempStudent)
                Toast.makeText(this, "Student Successfully Added", Toast.LENGTH_SHORT).show()
                val myIntent = Intent(this, StudentHome::class.java)
                startActivity(myIntent)
            } else {
                if (tempUri == GlobalVar.listDataStudent[position].imageUri) {
                    tempStudent.imageUri = GlobalVar.listDataStudent[position].imageUri
                } else if (tempUri == "") {
                    tempStudent.imageUri = GlobalVar.listDataStudent[position].imageUri
                } else {
                    tempStudent.imageUri = tempUri
                }
                GlobalVar.listDataStudent[position] = tempStudent
                Toast.makeText(this, "Class Successfully Edited", Toast.LENGTH_SHORT).show()
                val myIntent = Intent(this, StudentHome::class.java)
                startActivity(myIntent)
            }
            finish()
        }
    }

}