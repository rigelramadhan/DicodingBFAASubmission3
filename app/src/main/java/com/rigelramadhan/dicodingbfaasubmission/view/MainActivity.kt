package com.rigelramadhan.dicodingbfaasubmission.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rigelramadhan.dicodingbfaasubmission.R
import com.rigelramadhan.dicodingbfaasubmission.adapter.UserAdapter
import com.rigelramadhan.dicodingbfaasubmission.databinding.ActivityMainBinding
import com.rigelramadhan.dicodingbfaasubmission.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)

        mainViewModel.usersList.observe(this) {
            binding.rvUsers.adapter = UserAdapter(it)
            binding.rvUsers.layoutManager = LinearLayoutManager(this)
        }
    }
}