package com.example.movielist

import Adapter.ListStudentRVAdapter
import Database.GlobalVar
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movielist.databinding.ActivityStudentHomeBinding

class StudentHome : AppCompatActivity() {
    private lateinit var binding : ActivityStudentHomeBinding
    private val adapter = ListStudentRVAdapter(GlobalVar.listDataStudent, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupRecyclerView()
        listener()
        hidden()


    }
    private fun hidden(){
        if(GlobalVar.listDataMovie.isNotEmpty()){
            binding.middletag.isInvisible = true
        }
        else if(GlobalVar.listDataMovie.isNotEmpty())
        {
            binding.middletag.isVisible = true
        }
    }
    private fun listener(){
        binding.addFAB.setOnClickListener {
            val myIntent = Intent(this, CreateActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    private fun setupRecyclerView(){
        val layoutManager = GridLayoutManager(baseContext,2)
        binding.listMovRV.layoutManager= layoutManager //Set layout
        binding.listMovRV.adapter=adapter //Set adapter
    }


    override fun onCardClick(position: Int) {
        val myIntent = Intent(this, DetailActivity::class.java).apply{
            putExtra("position" , position)
        }

        startActivity(myIntent)
    }
}