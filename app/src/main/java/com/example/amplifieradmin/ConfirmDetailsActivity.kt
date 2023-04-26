package com.example.amplifieradmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.amplifieradmin.data.model.ConfirmedListRespData
import com.example.amplifieradmin.data.model.RecommendBusinessData
import com.example.amplifieradmin.databinding.ActivityBusinessInfoBinding
import com.example.amplifieradmin.databinding.ActivityConfirmDetailsBinding

class ConfirmDetailsActivity : AppCompatActivity() {
    private var _binding: ActivityConfirmDetailsBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityConfirmDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI();
        setupClicks()

    }

    private fun setupClicks() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupUI() {
        val dataDetails: ConfirmedListRespData?=intent.getParcelableExtra<ConfirmedListRespData?>("info")
        setData(dataDetails)

    }

    private fun setData(confirmedListRespData: ConfirmedListRespData?) {
        binding.bussinessTv.text=confirmedListRespData?.s_business
        binding.categoryTv.text=confirmedListRespData?.businesscategory
        binding.listingTv.text=confirmedListRespData?.listing_type
        binding.phoneNumber.text=confirmedListRespData?.s_phone_code+confirmedListRespData?.s_phone
        binding.locatedTv.text=confirmedListRespData?.locatedin
        binding.streetTv.text=confirmedListRespData?.s_address
        binding.areanameTv.text=confirmedListRespData?.s_address2
        binding.stateTv.text=confirmedListRespData?.s_address1
        binding.cityTv.text=confirmedListRespData?.city
        binding.pincodeTv.text=confirmedListRespData?.s_address3

    }
}