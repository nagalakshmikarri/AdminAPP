package com.example.amplifieradmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.amplifieradmin.databinding.ActivityAdvertaisementsBinding
import com.example.amplifieradmin.databinding.ActivityCreateAdBinding

class CreateAdActivity : AppCompatActivity() {
    private var _binding: ActivityCreateAdBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreateAdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClicks()
    }

    private fun setupClicks() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

    }
}