package com.example.amplifieradmin.ui.main.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amplifieradmin.data.model.AllJobsRespData
import com.example.amplifieradmin.data.model.BlockedListRespData
import com.example.amplifieradmin.databinding.BlockListLayoutBinding

class BlockedListAdapter(
    private var blockedListRespData: List<BlockedListRespData>,
    private val context: Context,
    private var onItemClick:OnItemClick,
    private var onPhoneClick:OnPhoneClick,
    private var onBlockClick:OnBlockClick
): RecyclerView.Adapter<BlockedListAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: BlockListLayoutBinding):
        RecyclerView.ViewHolder(itemView.root){
        private var binding = itemView
        fun bind(
            blockedListRespData: BlockedListRespData,
            context: Context,
            onItemClick: OnItemClick,
            onPhoneClick: OnPhoneClick,
            onBlockClick: OnBlockClick
        ) {
            binding.username.text=blockedListRespData.s_business
            binding.address.text =
                if (!blockedListRespData.s_address.isNullOrEmpty()) {
                    blockedListRespData.s_address + ", "
                } else {
                    " "
                } + if (!blockedListRespData.s_address1.isNullOrEmpty()) {
                    blockedListRespData.s_address1 + ", "
                } else {
                    " "
                } + if (!blockedListRespData.s_address2.isNullOrEmpty()) {
                    blockedListRespData.s_address2 + ", "
                } else {
                    " "
                } + if (!blockedListRespData.s_address3.isNullOrEmpty()) {
                    blockedListRespData.s_address3 + ", "
                } else {
                    ""
                }
            binding.phoneNumber.text=blockedListRespData.s_phone_code+blockedListRespData.s_phone
            binding.category.text=blockedListRespData.businesscategory

            binding.confirmBtn.setOnClickListener {
                onItemClick.onItemClick(blockedListRespData.s_id)
            }

            binding.phoneNumber.setOnClickListener {
                onPhoneClick.onPhoneClick(blockedListRespData.s_phone)
            }

            binding.rlBlock.setOnClickListener {
                onBlockClick.onBlockClick(blockedListRespData)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        BlockedListAdapter. DataViewHolder(
            BlockListLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    override fun getItemCount(): Int =
        blockedListRespData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onBindViewHolder(holder:BlockedListAdapter.DataViewHolder,position: Int) {
        holder.bind(
            blockedListRespData[position],
            context,
            onItemClick,
            onPhoneClick,
            onBlockClick
        )

    }

    fun filterList(adminUsersData: MutableList<BlockedListRespData>) {
        this.blockedListRespData=blockedListRespData
        notifyDataSetChanged()
    }
    fun addData(list: List<BlockedListRespData>) {
        this.blockedListRespData = list
    }
    interface OnItemClick{
        fun onItemClick(s_id:String)
    }
    interface OnPhoneClick{
        fun onPhoneClick(phone:String)
    }
    interface OnBlockClick{
        fun onBlockClick(item:BlockedListRespData)
    }

}