package com.example.amplifieradmin.ui.main.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amplifieradmin.AdvertaisementsActivity
import com.example.amplifieradmin.data.model.CliamBusinessData
import com.example.amplifieradmin.data.model.CliamDetailRespData
import com.example.amplifieradmin.databinding.CliambusinessBinding
import com.example.amplifieradmin.databinding.CliamuserslayoutBinding

class CliamDetailAdaper(
    private var cliamDetailRespData: List<CliamDetailRespData>,
    private val context: Context,
): RecyclerView.Adapter<CliamDetailAdaper.DataViewHolder>() {
    class DataViewHolder(itemView: CliamuserslayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private var binding = itemView
        fun bind(
            cliamDetailRespData: CliamDetailRespData,
            context: Context,
        ) {
            binding.tvName.text = cliamDetailRespData.s_username
            binding.tvPhonenumber.text=cliamDetailRespData.s_phone
/*
            binding.linearLayoutTv.setOnClickListener {
                //  Toast.makeText(context ,adminUsersData.admin_id, Toast.LENGTH_SHORT).show()
                val intent = Intent(context, AdvertaisementsActivity::class.java)
                intent.putExtra("subadmin_id", cliamBusinessData.admin_id)
                context.startActivity(intent);
            }
*/

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CliamDetailAdaper.DataViewHolder(
            CliamuserslayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )

    override fun getItemCount(): Int =
        cliamDetailRespData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder:CliamDetailAdaper.DataViewHolder,position: Int) {
        holder.bind(
            cliamDetailRespData[position],
            context,
        )

    }

    fun filterList(adminUsersData: MutableList<CliamDetailRespData>) {
        this.cliamDetailRespData=cliamDetailRespData
        notifyDataSetChanged()
    }
    fun addData(list: List<CliamDetailRespData>) {
        this.cliamDetailRespData = list
    }
}