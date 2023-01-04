package com.example.amplifieradmin.ui.main.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amplifieradmin.data.model.AcceptAdsData
import com.example.amplifieradmin.data.model.BusinessListData
import com.example.amplifieradmin.databinding.AcceptadsBinding
import com.example.amplifieradmin.databinding.BusinesslistBinding

class BusinessListAdapter(
    private var businessListData: List<BusinessListData>,
    private val context: Context,
    ) : RecyclerView.Adapter<BusinessListAdapter.DataViewHolder>(){
    class DataViewHolder(itemView:BusinesslistBinding):
    RecyclerView.ViewHolder(itemView.root) {
        private var binding = itemView
        fun bind(
            businessListData: BusinessListData,
            context: Context,
        ) {
            binding.tvText.text=businessListData.s_business
            binding.addressTv.text=businessListData.s_address
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BusinessListAdapter.DataViewHolder(
            BusinesslistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    override fun getItemCount(): Int =
        businessListData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
    fun filterList(adminUsersData: MutableList<BusinessListData>) {
        this.businessListData = businessListData
        notifyDataSetChanged()
    }

    fun addData(list: List<BusinessListData>) {
        this.businessListData = list
    }
    override fun onBindViewHolder(holder: BusinessListAdapter.DataViewHolder, position: Int) {
        holder.bind(
            businessListData[position],
            context,
        )
    }
    }