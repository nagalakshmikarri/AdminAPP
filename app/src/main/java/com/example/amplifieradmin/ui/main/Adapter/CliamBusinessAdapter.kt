package com.example.amplifieradmin.ui.main.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amplifieradmin.AdsListStatusActivity
import com.example.amplifieradmin.AdvertaisementsActivity
import com.example.amplifieradmin.data.model.AdsPendingData
import com.example.amplifieradmin.data.model.CliamBusinessData
import com.example.amplifieradmin.databinding.CliambusinessBinding
import com.example.amplifieradmin.databinding.PendingBinding

class CliamBusinessAdapter(
    private var cliamBusinessData: List<CliamBusinessData>,
    private val context: Context,
    ):RecyclerView.Adapter<CliamBusinessAdapter.DataViewHolder>(){
    class DataViewHolder(itemView:CliambusinessBinding):
    RecyclerView.ViewHolder(itemView.root){
        private var binding = itemView
        fun bind(
            cliamBusinessData:CliamBusinessData,
            context: Context,
        ){
            binding.tvBusiness.text=cliamBusinessData.s_business
            binding.tvAddress.text=cliamBusinessData.s_address
            binding.linearLayoutTv.setOnClickListener {
                //  Toast.makeText(context ,adminUsersData.admin_id, Toast.LENGTH_SHORT).show()
                val intent = Intent(context, AdvertaisementsActivity::class.java)
                intent.putExtra("subadmin_id", cliamBusinessData.admin_id)
                context.startActivity(intent);
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        CliamBusinessAdapter. DataViewHolder(
            CliambusinessBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    override fun getItemCount(): Int =
        cliamBusinessData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder:CliamBusinessAdapter.DataViewHolder,position: Int) {
        holder.bind(
            cliamBusinessData[position],
            context,
        )

    }

    fun filterList(adminUsersData: MutableList<CliamBusinessData>) {
        this.cliamBusinessData=cliamBusinessData
        notifyDataSetChanged()
    }
    fun addData(list: List<CliamBusinessData>) {
        this.cliamBusinessData = list
    }
}
