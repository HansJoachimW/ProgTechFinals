package com.example.movielist

import Adapter.ListMovieRVAdapter
import Database.GlobalVar
import Interface.CardListener
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movielist.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity(), CardListener {
    private lateinit var binding : ActivityHomeBinding
    private val adapter = ListMovieRVAdapter(GlobalVar.listDataMovie, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupRecyclerView()
        listener()
        hidden()


}
    private fun hidden(){
        if(GlobalVar.listDataMovie.isNotEmpty()){
            binding.addClassTag.isInvisible = true
        }
        else if(GlobalVar.listDataMovie.isNotEmpty())
        {
            binding.addClassTag.isVisible = true
        }
    }
    private fun listener(){
        binding.addClassList.setOnClickListener {
            val myIntent = Intent(this, CreateActivity::class.java)
            startActivity(myIntent)
        }
        binding.BackButtonClassHome.setOnClickListener {
            val myIntent = Intent(this, MainHomeActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    private fun setupRecyclerView(){
        val layoutManager = GridLayoutManager(baseContext,2)
        binding.listClassRV.layoutManager= layoutManager //Set layout
        binding.listClassRV.adapter=adapter //Set adapter
    }


    override fun onCardClick(position: Int) {
        val myIntent = Intent(this, DetailActivity::class.java).apply{
            putExtra("position" , position)
        }

        startActivity(myIntent)
    }


}