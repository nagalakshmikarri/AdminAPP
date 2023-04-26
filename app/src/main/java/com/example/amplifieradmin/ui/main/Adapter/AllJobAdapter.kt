package com.example.amplifieradmin.ui.main.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.amplifieradmin.data.model.AllJobsRespData
import com.example.amplifieradmin.data.model.BlockedListRespData
import com.example.amplifieradmin.databinding.AlljobslayoutBinding

class AllJobAdapter(
    private var allJobsRespData: List<AllJobsRespData>,
    private val context: Context,
): RecyclerView.Adapter<AllJobAdapter.DataViewHolder>(){
    class DataViewHolder(itemView: AlljobslayoutBinding):
        RecyclerView.ViewHolder(itemView.root){
        private var binding = itemView
        fun bind(
            allJobsRespData: AllJobsRespData,
            context: Context
        ) {
            Glide.with(context).load(allJobsRespData.user_img).into(binding.userpic)
            binding.userNameTv.text=allJobsRespData.name
            binding.type.text="Posted by ${allJobsRespData.type}"
            binding.tvTitle.text=allJobsRespData.title
            binding.tvAddress.text=allJobsRespData.address

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        AllJobAdapter. DataViewHolder(
            AlljobslayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    override fun getItemCount(): Int =
        allJobsRespData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onBindViewHolder(holder:AllJobAdapter.DataViewHolder,position: Int) {
        holder.bind(
            allJobsRespData[position],
            context)

    }
    fun filterList(adminUsersData: MutableList<AllJobsRespData>) {
        this.allJobsRespData=allJobsRespData
        notifyDataSetChanged()
    }
    fun addData(list: List<AllJobsRespData>) {
        this.allJobsRespData = list
    }
}