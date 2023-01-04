package com.example.amplifieradmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.amplifieradmin.databinding.ActivityAdvertaisementsBinding
import com.example.amplifieradmin.databinding.ActivityCliamBusinessBinding

class AdvertaisementsActivity : AppCompatActivity() {
    private var _binding: ActivityAdvertaisementsBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAdvertaisementsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClicks()
    }

    private fun setupClicks() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.createads.setOnClickListener {
            val intent = Intent(this@AdvertaisementsActivity, CreateAdActivity::class.java)
            startActivity(intent)
        }

    }
}