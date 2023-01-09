package com.example.amplifieradmin.ui.main.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.amplifieradmin.BusinessCategoryActivity
import com.example.amplifieradmin.R
import com.example.amplifieradmin.data.model.AllBusinessListData
import com.example.amplifieradmin.data.model.RecommendBusinessData
import com.example.amplifieradmin.databinding.AllbusinesslistBinding
import com.example.amplifieradmin.databinding.RecommendbusinessBinding

class AllBusinessListAdapter(
    private var allBusinessData: List<AllBusinessListData>,
    private val context: Context,
) : RecyclerView.Adapter<AllBusinessListAdapter.DataViewHolder>(){
    class DataViewHolder(itemView: AllbusinesslistBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private var binding = itemView
        fun bind(
            allBusinessData: AllBusinessListData,
            context: Context,
        ) {
            binding.tvBusiness.text = allBusinessData.s_business
            binding.tvCategory.text=allBusinessData.businesscategory
            binding.tvCategory.setTextColor(ContextCompat.getColor(context, R.color.purple_500));
            binding.businessCategoryTv.setOnClickListener {
                val intent = Intent(context, BusinessCategoryActivity::class.java)
                intent.putExtra("subadmin_id", allBusinessData.admin_id)
                intent.putExtra("s_id",allBusinessData.s_id)
                context.startActivity(intent);

            }
            binding.tvAddress.text = if (!allBusinessData.s_address.isNullOrEmpty())
                allBusinessData.s_address + ", " else " " +
                    if (!allBusinessData.s_address1.isNullOrEmpty())
                        allBusinessData.s_address1 + ", " else " " +
                            if (!allBusinessData.s_address2.isNullOrEmpty())
                                allBusinessData.s_address2 + ", " else " " +
                                    if (!allBusinessData.s_address3.isNullOrEmpty())
                                        allBusinessData.s_address3 + ", " else "" +
                                            if (!allBusinessData.city.isNullOrEmpty())
                                                allBusinessData.city + ", " else ""

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AllBusinessListAdapter.DataViewHolder(
            AllbusinesslistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    override fun getItemCount(): Int =
        allBusinessData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onBindViewHolder(holder: AllBusinessListAdapter.DataViewHolder, position: Int) {
        holder.bind(
            allBusinessData[position],
            context,
        )

    }

    fun filterList(adminUsersData: MutableList<AllBusinessListData>) {
        this.allBusinessData = allBusinessData
        notifyDataSetChanged()
    }

    fun addData(list: List<AllBusinessListData>) {
        this.allBusinessData = list
    }


}