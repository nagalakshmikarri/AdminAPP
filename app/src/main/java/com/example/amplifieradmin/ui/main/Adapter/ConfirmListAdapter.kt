package com.example.amplifieradmin.ui.main.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amplifieradmin.data.model.ConfirmedListRespData
import com.example.amplifieradmin.databinding.ConfirmListLayoutBinding

class ConfirmListAdapter(
    private var confirmedListRespData: List<ConfirmedListRespData>,
    private val context: Context,
    private var onItemClick:OnItemClick,
    private var onPhoneClick:OnPhoneClick,
): RecyclerView.Adapter<ConfirmListAdapter.DataViewHolder>(){

    class DataViewHolder(itemView: ConfirmListLayoutBinding):
        RecyclerView.ViewHolder(itemView.root){
        private var binding = itemView
        fun bind(
            confirmedListRespData: ConfirmedListRespData,
            context: Context,
            onItemClick: OnItemClick,
            onPhoneClick: OnPhoneClick
        ) {
            binding.username.text=confirmedListRespData.s_business
            binding.address.text =
                if (!confirmedListRespData.s_address.isNullOrEmpty())
                    confirmedListRespData.s_address + ", " else " " +
                        if (!confirmedListRespData.s_address1.isNullOrEmpty())
                            confirmedListRespData.s_address1 + ", " else " " +
                                if (!confirmedListRespData.s_address2.isNullOrEmpty())
                                    confirmedListRespData.s_address2 + ", " else " " +
                                        if (!confirmedListRespData.s_address3.isNullOrEmpty())
                                            confirmedListRespData.s_address3 + ", " else ""
            binding.phoneNumber.text=confirmedListRespData.s_phone_code+confirmedListRespData.s_phone
            binding.category.text=confirmedListRespData.businesscategory

            binding.blockBtn.setOnClickListener {
                onItemClick.onItemClick(confirmedListRespData.s_id)
            }


            binding.phoneNumber.setOnClickListener {
                onPhoneClick.onPhoneClick(confirmedListRespData.s_phone)
            }


        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        ConfirmListAdapter. DataViewHolder(
            ConfirmListLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    override fun getItemCount(): Int =
        confirmedListRespData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onBindViewHolder(holder:ConfirmListAdapter.DataViewHolder,position: Int) {
        holder.bind(
            confirmedListRespData[position],
            context,
            onItemClick,
            onPhoneClick
        )

    }

    fun filterList(adminUsersData: MutableList<ConfirmedListRespData>) {
        this.confirmedListRespData=confirmedListRespData
        notifyDataSetChanged()
    }
    fun addData(list: List<ConfirmedListRespData>) {
        this.confirmedListRespData = list
    }
    interface OnItemClick{
        fun onItemClick(s_id:String)
    }
    interface OnPhoneClick{
        fun onPhoneClick(phone:String)
    }

}