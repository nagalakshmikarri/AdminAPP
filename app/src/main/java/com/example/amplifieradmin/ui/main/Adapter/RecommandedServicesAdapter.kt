package com.example.amplifieradmin.ui.main.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amplifieradmin.data.model.ListInviteTypeRespData
import com.example.amplifieradmin.databinding.RecommandedServicesLayoutBinding

class RecommandedServicesAdapter(
    private var listInviteTypeRespData: List<ListInviteTypeRespData>,
    private val context: Context,
    private var onItemClick:OnItemClick

) : RecyclerView.Adapter<RecommandedServicesAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: RecommandedServicesLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private var binding = itemView
        fun bind(
            listInviteTypeRespData: ListInviteTypeRespData,
            context: Context,
            onItemClick: OnItemClick
        ) {
            binding.tvType.text=listInviteTypeRespData.type

            binding.tvType.setOnClickListener {
                onItemClick.onItemClick(listInviteTypeRespData.type,listInviteTypeRespData.id)

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecommandedServicesAdapter.DataViewHolder(
            RecommandedServicesLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )

    override fun getItemCount(): Int =
        listInviteTypeRespData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(
        holder: RecommandedServicesAdapter.DataViewHolder,
        position: Int
    ) {
        holder.bind(
            listInviteTypeRespData[position],
            context,
            onItemClick,
            )

    }

    fun filterList(adminUsersData: MutableList<ListInviteTypeRespData>) {
        this.listInviteTypeRespData = listInviteTypeRespData
        notifyDataSetChanged()
    }

    fun addData(list: List<ListInviteTypeRespData>) {
        this.listInviteTypeRespData = list
    }
    interface OnItemClick {
        fun onItemClick(
         type:String,
         id:String
        )
    }

}