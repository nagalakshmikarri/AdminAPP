package com.example.amplifieradmin.ui.main.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amplifieradmin.data.model.ApprovedBusinessListResp
import com.example.amplifieradmin.data.model.CliamBusinessListRespData
import com.example.amplifieradmin.databinding.CliambusinessBinding

class ApproveBusinessListAdapter(
    private var cliamBusinessListRespData: List<ApprovedBusinessListResp.Data>,
    private val context: Context,
    private var onItemClick: OnItemClick
): RecyclerView.Adapter<ApproveBusinessListAdapter.DataViewHolder>(){
    class DataViewHolder(itemView: CliambusinessBinding):
        RecyclerView.ViewHolder(itemView.root){
        private var binding = itemView
        fun bind(
            cliamBusinessListRespData: ApprovedBusinessListResp.Data,
            context: Context,
            onItemClick: OnItemClick,
        ){
            binding.tvBusiness.text=cliamBusinessListRespData.sBusiness
            binding.tvEmail.text=cliamBusinessListRespData.sEmail
            binding.tvCategory.text=cliamBusinessListRespData.businesscategory
            binding.tvAddress.text =
                if (!cliamBusinessListRespData.sAddress.isNullOrEmpty()) {
                    cliamBusinessListRespData.sAddress + ", "
                } else {
                    " "
                } + if (!cliamBusinessListRespData.sAddress1.isNullOrEmpty()) {
                    cliamBusinessListRespData.sAddress1 + ", "
                } else {
                    " "
                } + if (!cliamBusinessListRespData.sAddress2.isNullOrEmpty()) {
                    cliamBusinessListRespData.sAddress2 + ", "
                } else {
                    " "
                } + if (!cliamBusinessListRespData.sAddress3.isNullOrEmpty()) {
                    cliamBusinessListRespData.sAddress3 + ", "
                } else {
                    ""
                }

            binding.tvPhone.text=cliamBusinessListRespData.sPhoneCode+cliamBusinessListRespData.sPhone
/*
            binding.linearLayoutTv.setOnClickListener {
                //  Toast.makeText(context ,adminUsersData.admin_id, Toast.LENGTH_SHORT).show()
                val intent = Intent(context, AdvertaisementsActivity::class.java)
                intent.putExtra("subadmin_id", cliamBusinessListRespData.admin_id)
                context.startActivity(intent);
            }
*/

         /*   binding.linearLayoutTv.setOnClickListener {
                onItemClick.onItemClick(cliamBusinessListRespData.s_business,cliamBusinessListRespData.s_id,cliamBusinessListRespData)

            }*/


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        ApproveBusinessListAdapter. DataViewHolder(
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

    override fun onBindViewHolder(holder:ApproveBusinessListAdapter.DataViewHolder, position: Int) {
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
    fun addData(list: List<ApprovedBusinessListResp.Data>) {
        this.cliamBusinessListRespData = list
    }


    interface OnItemClick {
        fun onItemClick(
            s_business: String,
            sId: String,
            cliamBusinessListRespData: CliamBusinessListRespData
        )
    }

}