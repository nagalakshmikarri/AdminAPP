package com.example.amplifieradmin.ui.main.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amplifieradmin.data.model.PriorityListRspData
import com.example.amplifieradmin.databinding.PriorityListLayoutBinding
import java.text.SimpleDateFormat
import java.util.*

class PriorityListAdapter(
    private var priorityListRspData: List<PriorityListRspData>,
    private val context: Context,
) : RecyclerView.Adapter<PriorityListAdapter.DataViewHolder>() {
    class DataViewHolder(itemView: PriorityListLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private var binding = itemView
        fun bind(
            priorityListRspData: PriorityListRspData,
            context: Context
        ) {
            binding.businessTv.text=priorityListRspData.s_business
            binding.addressTv.text=priorityListRspData.s_address
            binding.typeTv.text="Type: ${priorityListRspData.duration}"
            binding.amountTv.text="Amount: ${priorityListRspData.amount}"
            val inputPattern = "yyyy-MM-dd"
            val outputPattern = "MMM dd"
            val inputFormat = SimpleDateFormat(inputPattern, Locale.US)
            val outputFormat = SimpleDateFormat(outputPattern, Locale.US)

            val sDate = inputFormat.parse(priorityListRspData.start_date)
            val eDate = inputFormat.parse(priorityListRspData.end_date)

            binding.stDate.text = outputFormat.format(sDate)+" \u00b7 " +outputFormat.format(eDate)


        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        PriorityListAdapter. DataViewHolder(
            PriorityListLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    override fun getItemCount(): Int =
        priorityListRspData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onBindViewHolder(holder:PriorityListAdapter.DataViewHolder,position: Int) {
        holder.bind(
            priorityListRspData[position],
            context,
        )

    }
    fun filterList(adminUsersData: MutableList<PriorityListRspData>) {
        this.priorityListRspData=priorityListRspData
        notifyDataSetChanged()
    }
    fun addData(list: List<PriorityListRspData>) {
        this.priorityListRspData = list
    }

}