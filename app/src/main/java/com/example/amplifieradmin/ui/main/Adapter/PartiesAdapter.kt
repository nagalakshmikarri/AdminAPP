package com.example.amplifieradmin.ui.main.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amplifieradmin.data.model.SubTypeInviteListRespData
import com.example.amplifieradmin.databinding.RecommandedServicesLayoutBinding

class PartiesAdapter(
    private var subTypeInviteListRespData: List<SubTypeInviteListRespData>,
    private val context: Context,
) : RecyclerView.Adapter<PartiesAdapter.DataViewHolder>()  {
    class DataViewHolder(itemView: RecommandedServicesLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private var binding = itemView
        fun bind(
            subTypeInviteListRespData: SubTypeInviteListRespData,
            context: Context
        ) {
            binding.tvType.text=subTypeInviteListRespData.type

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PartiesAdapter.DataViewHolder(
            RecommandedServicesLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )

    override fun getItemCount(): Int =
        subTypeInviteListRespData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(
        holder: PartiesAdapter.DataViewHolder,
        position: Int
    ) {
        holder.bind(
            subTypeInviteListRespData[position],
            context,
        )

    }

    fun filterList(adminUsersData: MutableList<SubTypeInviteListRespData>) {
        this.subTypeInviteListRespData = subTypeInviteListRespData
        notifyDataSetChanged()
    }

    fun addData(list: List<SubTypeInviteListRespData>) {
        this.subTypeInviteListRespData = list
    }

}