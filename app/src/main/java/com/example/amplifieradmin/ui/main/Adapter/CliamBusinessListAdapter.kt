package com.example.amplifieradmin.ui.main.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amplifieradmin.data.model.CliamBusinessListRespData
import com.example.amplifieradmin.databinding.CliambusinessBinding

class CliamBusinessListAdapter(
    private var cliamBusinessListRespData: List<CliamBusinessListRespData>,
    private val context: Context,
    private var onItemClick: OnItemClick
): RecyclerView.Adapter<CliamBusinessListAdapter.DataViewHolder>(){
    class DataViewHolder(itemView: CliambusinessBinding):
        RecyclerView.ViewHolder(itemView.root){
        private var binding = itemView
        fun bind(
            cliamBusinessListRespData: CliamBusinessListRespData,
            context: Context,
            onItemClick: OnItemClick,
        ){
            binding.tvBusiness.text=cliamBusinessListRespData.s_business
            binding.tvPhone.text=cliamBusinessListRespData.s_phone
/*
            binding.linearLayoutTv.setOnClickListener {
                //  Toast.makeText(context ,adminUsersData.admin_id, Toast.LENGTH_SHORT).show()
                val intent = Intent(context, AdvertaisementsActivity::class.java)
                intent.putExtra("subadmin_id", cliamBusinessListRespData.admin_id)
                context.startActivity(intent);
            }
*/

            binding.tvBusiness.setOnClickListener {
                onItemClick.onItemClick(cliamBusinessListRespData.s_business,cliamBusinessListRespData.s_id)

            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        CliamBusinessListAdapter. DataViewHolder(
            CliambusinessBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    override fun getItemCount(): Int =
        cliamBusinessListRespData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder:CliamBusinessListAdapter.DataViewHolder,position: Int) {
        holder.bind(
            cliamBusinessListRespData[position],
            context,
            onItemClick,
        )

    }

    fun filterList(adminUsersData: MutableList<CliamBusinessListRespData>) {
        this.cliamBusinessListRespData=cliamBusinessListRespData
        notifyDataSetChanged()
    }
    fun addData(list: List<CliamBusinessListRespData>) {
        this.cliamBusinessListRespData = list
    }


    interface OnItemClick {
        fun onItemClick(s_business: String,sId:String)
    }

}