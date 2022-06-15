package com.example.movielist

import Adapter.ListStudentRVAdapter
import Database.GlobalVar
import Interface.CardListener
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movielist.databinding.ActivityStudentHomeBinding

class StudentHome : AppCompatActivity(), CardListener {
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
        if(GlobalVar.listDataStudent.isNotEmpty()){
            binding.StudentListTag.isInvisible = true
        }
        else if(GlobalVar.listDataStudent.isNotEmpty())
        {
            binding.StudentListTag.isVisible = true
        }
    }
    private fun listener(){
        binding.addStudentTag.setOnClickListener {
            val myIntent = Intent(this, CreateStudentActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    private fun setupRecyclerView(){
        val layoutManager = GridLayoutManager(baseContext,2)
        binding.listStudentRV.layoutManager= layoutManager //Set layout
        binding.listStudentRV.adapter=adapter //Set adapter
    }


    override fun onCardClick(position: Int) {
        val myIntent = Intent(this, DetailStudentActivity::class.java).apply{
            putExtra("position" , position)
        }

        startActivity(myIntent)
    }
}