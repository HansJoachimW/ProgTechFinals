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
    private lateinit var tempMov:Movie
    var tempUri:String =""
    private var position = -1
    private val GetResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            val uri = it.data?.data
            binding.inputPoster.setImageURI(uri)
            if(uri!=null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    baseContext.getContentResolver().takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }
                tempUri = uri.toString()
            }

            //GlobalVar.listDataMovie[position].imageUri = uri.toString()
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
            binding.AddMovieView.setText("Edit Movie")
            binding.SubmitAddBut.setText("Save")
            val tempMov = GlobalVar.listDataMovie[position]
            Display(tempMov)
        }
    }

    private fun Display(tempMov:Movie?){
        binding.TitleInLayout.editText?.setText((tempMov?.title))
        binding.RatingInputLayout.editText?.setText((tempMov?.rating))
        binding.GenreInputLayout.editText?.setText((tempMov?.genre))
        binding.ProdInputLayout.editText?.setText((tempMov?.prodComp))
        binding.SynopsisInputLayout.editText?.setText((tempMov?.synopsis))
        binding.inputPoster.setImageURI(Uri.parse(tempMov?.imageUri))

    }

    private fun Listener(){
        binding.SubmitAddBut.setOnClickListener{
            var title = binding.TitleInLayout.editText?.text.toString().trim()
            var rating = binding.RatingInputLayout.editText?.text.toString().trim()
            var genre = binding.GenreInputLayout.editText?.text.toString().trim()
            var prodcom = binding.ProdInputLayout.editText?.text.toString().trim()
            var synopsis = binding.SynopsisInputLayout.editText?.text.toString().trim()

            tempMov = Movie(title,rating,genre,prodcom,synopsis)
            checker()
        }

        binding.BackButAddMov.setOnClickListener{
            val myIntent = Intent(this,HomeActivity::class.java)
            startActivity(myIntent)
        }

        binding.inputPoster.setOnClickListener{
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type="image/*"
            GetResult.launch(myIntent)
        }
    }


    private fun checker(){
        var isCompleted:Boolean = true
        if(tempMov.title!!.isEmpty()){
            binding.TitleInLayout.error = "Title harus diisi"
            isCompleted = false
        }else{
            binding.TitleInLayout.error = ""
        }

        if(tempMov.rating!!.isEmpty()){
            binding.RatingInputLayout.error = "Rating harus diisi 1-10"
            isCompleted=false
        }else if(tempMov.rating!!.contains(".*[A-Z].*".toRegex())){
            binding.RatingInputLayout.error = "Rating tidak boleh ada huruf"
            isCompleted=false
        }else if(tempMov.rating!!.contains(".*[a-z].*".toRegex())){
            binding.RatingInputLayout.error = "Rating tidak boleh ada huruf"
            isCompleted=false
        }else if(tempMov.rating!!.contains(".*[0-9].*".toRegex())){
            if(tempMov.rating!!.toInt()>10) {
                binding.RatingInputLayout.error = "Rating harus 1-10"
                isCompleted = false
            }
            else {
                binding.RatingInputLayout.error = ""
            }
        }
        else{
            binding.RatingInputLayout.error = "Rating tidak boleh ada simbol"
            isCompleted=false
        }

        if(tempMov.genre!!.isEmpty()){
            binding.GenreInputLayout.error = "Genre harus diisi"
            isCompleted=false
        }else{
            binding.GenreInputLayout.error = ""
        }

        if(tempMov.prodComp!!.isEmpty()){
            binding.ProdInputLayout.error = "Production Company harus diisi"
            isCompleted=false
        }else{
            binding.ProdInputLayout.error = ""
        }

        if(tempMov.synopsis!!.isEmpty()){
            binding.SynopsisInputLayout.error = "Synopsis harus diisi"
            isCompleted=false
        }else{
            binding.SynopsisInputLayout.error = ""
        }

        if(isCompleted){
            if(position==-1){
                tempMov.imageUri= tempUri
                GlobalVar.listDataMovie.add(tempMov)
                Toast.makeText(this, "Movie Successfully Added", Toast.LENGTH_SHORT).show()
                val myIntent = Intent(this,HomeActivity::class.java)
                startActivity(myIntent)
            }
            else{
                if(tempUri==GlobalVar.listDataMovie[position].imageUri) {
                    tempMov.imageUri = GlobalVar.listDataMovie[position].imageUri
                }
                else if(tempUri==""){
                    tempMov.imageUri = GlobalVar.listDataMovie[position].imageUri
                }
                else{
                    tempMov.imageUri = tempUri
                }
                GlobalVar.listDataMovie[position]=tempMov
                Toast.makeText(this, "Movie Successfully Edited", Toast.LENGTH_SHORT).show()
                val myIntent = Intent(this,HomeActivity::class.java)
                startActivity(myIntent)
            }
            finish()
        }
    }

}