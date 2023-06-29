package com.example.amplifieradmin.ui.main.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amplifieradmin.data.model.AllCliaedBusinessRespData
import com.example.amplifieradmin.databinding.AllBusinessListLayoutBinding
import java.text.SimpleDateFormat
import java.util.*

class AllCliamBusinessListAdapter(
    private var allCliaedBusinessRespData: List<AllCliaedBusinessRespData>,
    private val context: Context,
) : RecyclerView.Adapter<AllCliamBusinessListAdapter.DataViewHolder>() {
    class DataViewHolder(itemView: AllBusinessListLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private var binding = itemView
        fun bind(
            allCliaedBusinessRespData: AllCliaedBusinessRespData,
            context: Context
        ) {
            binding.businessTv.text=allCliaedBusinessRespData.s_business
            binding.addressTv.text=allCliaedBusinessRespData.s_address
            binding.categoryTv.text="Category: ${allCliaedBusinessRespData.businesscategory}"
            binding.emailTv.text=allCliaedBusinessRespData.s_email
            binding.phoneNumber.text=allCliaedBusinessRespData?.s_phone_code+allCliaedBusinessRespData?.s_phone

            val inputPattern = "yyyy-MM-dd"
            val outputPattern = "MMM dd"
            val inputFormat = SimpleDateFormat(inputPattern, Locale.US)
            val outputFormat = SimpleDateFormat(outputPattern, Locale.US)

            val sDate = inputFormat.parse(allCliaedBusinessRespData.s_created)

            binding.stDate.text = outputFormat.format(sDate)


        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        AllCliamBusinessListAdapter. DataViewHolder(
            AllBusinessListLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    override fun getItemCount(): Int =
        allCliaedBusinessRespData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onBindViewHolder(holder:AllCliamBusinessListAdapter.DataViewHolder, position: Int) {
        holder.bind(
            allCliaedBusinessRespData[position],
            context,
        )

    }
    fun filterList(adminUsersData: MutableList<AllCliaedBusinessRespData>) {
        this.allCliaedBusinessRespData=adminUsersData
        notifyDataSetChanged()
    }
    fun addData(list: List<AllCliaedBusinessRespData>) {
        this.allCliaedBusinessRespData = list
    }

}