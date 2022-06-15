package com.example.movielist

import Database.GlobalVar
import Model.Movie
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.example.movielist.databinding.ActivityCreateBinding

class CreateActivity : AppCompatActivity() {
    private lateinit var tempClass:Movie
    var tempUri:String =""
    private var position = -1
    private val GetResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            val uri = it.data?.data
            binding.inputClassPhoto.setImageURI(uri)
            if(uri!=null) {
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
    private lateinit var binding : ActivityCreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        GetIntent()
        Listener()

    }


    private fun GetIntent(){
        position = intent.getIntExtra("position",-1)
        if(position!=-1){
            binding.AddClassView.setText("Edit Movie")
            binding.SubmitAddStudent.setText("Save")
            val tempMov = GlobalVar.listDataMovie[position]
            Display(tempMov)
        }
    }

    private fun Display(tempMov:Movie?){
        binding.ClassnameLayout.editText?.setText((tempMov?.classname))
        binding.AmountLayout.editText?.setText((tempMov?.amount))
        binding.MajorLayout.editText?.setText((tempMov?.major))
        binding.TeacherLayout.editText?.setText((tempMov?.teacher))
        binding.DescriptionLayout.editText?.setText((tempMov?.description))
        binding.inputClassPhoto.setImageURI(Uri.parse(tempMov?.imageUri))

    }

    private fun Listener(){
        binding.SubmitAddStudent.setOnClickListener{
            var classname = binding.ClassnameLayout.editText?.text.toString().trim()
            var amount = binding.AmountLayout.editText?.text.toString().trim()
            var major = binding.MajorLayout.editText?.text.toString().trim()
            var teacher = binding.TeacherLayout.editText?.text.toString().trim()
            var description = binding.DescriptionLayout.editText?.text.toString().trim()

            tempClass = Movie(classname,amount,major,teacher,description)
            checker()
        }

        binding.BackButAddMov.setOnClickListener{
            val myIntent = Intent(this, HomeActivity::class.java)
            startActivity(myIntent)
        }

        binding.inputClassPhoto.setOnClickListener{
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type="image/*"
            GetResult.launch(myIntent)
        }
    }


    private fun checker(){
        var isCompleted:Boolean = true
        if(tempClass.classname!!.isEmpty()){
            binding.ClassnameLayout.error = "Nama kelas harus diisi"
            isCompleted = false
        }else{
            binding.ClassnameLayout.error = ""
        }

        if(tempClass.amount!!.isEmpty()){
            binding.AmountLayout.error = "Jumlah mahasiswa harus diisi 1-100"
            isCompleted=false
        }else if(tempClass.amount!!.contains(".*[A-Z].*".toRegex())){
            binding.AmountLayout.error = "Jumlah mahasiswa tidak boleh ada huruf"
            isCompleted=false
        }else if(tempClass.amount!!.contains(".*[a-z].*".toRegex())){
            binding.AmountLayout.error = "Jumlah mahasiswa tidak boleh ada huruf"
            isCompleted=false
        }else if(tempClass.amount!!.contains(".*[0-9].*".toRegex())){
            if(tempClass.amount!!.toInt()>100) {
                binding.AmountLayout.error = "Jumlah mahasiswa harus 1-100"
                isCompleted = false
            }
            else {
                binding.AmountLayout.error = ""
            }
        }
        else{
            binding.AmountLayout.error = "Jumlah mahasiswa tidak boleh ada simbol"
            isCompleted=false
        }

        if(tempClass.major!!.isEmpty()){
            binding.MajorLayout.error = "Major harus diisi"
            isCompleted=false
        }else{
            binding.MajorLayout.error = ""
        }

        if(tempClass.teacher!!.isEmpty()){
            binding.TeacherLayout.error = "Nama guru harus diisi"
            isCompleted=false
        }else{
            binding.TeacherLayout.error = ""
        }

        if(tempClass.description!!.isEmpty()){
            binding.DescriptionLayout.error = "Deskripsi kelas harus diisi"
            isCompleted=false
        }else{
            binding.DescriptionLayout.error = ""
        }

        if(isCompleted){
            if(position==-1){
                tempClass.imageUri= tempUri
                GlobalVar.listDataMovie.add(tempClass)
                Toast.makeText(this, "Class Successfully Added", Toast.LENGTH_SHORT).show()
                val myIntent = Intent(this, HomeActivity::class.java)
                startActivity(myIntent)
            }
            else{
                if(tempUri==GlobalVar.listDataMovie[position].imageUri) {
                    tempClass.imageUri = GlobalVar.listDataMovie[position].imageUri
                }
                else if(tempUri==""){
                    tempClass.imageUri = GlobalVar.listDataMovie[position].imageUri
                }
                else{
                    tempClass.imageUri = tempUri
                }
                GlobalVar.listDataMovie[position]=tempClass
                Toast.makeText(this, "Class Successfully Edited", Toast.LENGTH_SHORT).show()
                val myIntent = Intent(this, HomeActivity::class.java)
                startActivity(myIntent)
            }
            finish()
        }
    }

}