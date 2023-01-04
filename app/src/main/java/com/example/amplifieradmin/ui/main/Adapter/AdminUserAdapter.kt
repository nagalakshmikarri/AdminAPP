package com.example.amplifieradmin.ui.main.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.amplifieradmin.AdsListStatusActivity
import com.example.amplifieradmin.R
import com.example.amplifieradmin.data.model.AdminUsersData
import com.example.amplifieradmin.databinding.AdminSupportersBinding

class   AdminUserAdapter(
    private var adminUsersData: List<AdminUsersData>,
    private val context: Context,
) :RecyclerView.Adapter<AdminUserAdapter.DataViewHolder>(){

    class DataViewHolder(itemView: AdminSupportersBinding) :
        RecyclerView.ViewHolder(itemView.root) {
            private var binding = itemView
        fun bind(
            adminUsersData: AdminUsersData,
            context: Context,
        ) {
            binding.tvAdminName.text = adminUsersData.admin_name
            binding.tvNumber.text = adminUsersData.admin_phone
            binding.tvAcceptCount.text = adminUsersData.acceptcount.toString()
            binding.tvRejectCount.text = adminUsersData.rejectcount.toString()
            binding.linearLayout.setOnClickListener {
                //  Toast.makeText(context ,adminUsersData.admin_id, Toast.LENGTH_SHORT).show()
                val intent = Intent(context, AdsListStatusActivity::class.java)
                intent.putExtra("subadmin_id", adminUsersData.admin_id)
                context.startActivity(intent);
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            AdminSupportersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    override fun getItemCount(): Int =
        adminUsersData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(
            adminUsersData[position],
            context,
        )

    }
    fun filterList(adminUsersData: MutableList<AdminUsersData>) {
        this.adminUsersData = adminUsersData
        notifyDataSetChanged()
    }
    fun addData(list: List<AdminUsersData>) {
        this.adminUsersData = list
    }
}