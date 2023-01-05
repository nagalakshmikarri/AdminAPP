package com.example.amplifieradmin.ui.main.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amplifieradmin.data.model.GetCategoryRespData
import com.example.amplifieradmin.databinding.CategoryBinding

class CategoryAdapter(
    private var getCategoryData: List<GetCategoryRespData>,
    private val context: Context,
    ): RecyclerView.Adapter<CategoryAdapter.DataViewHolder>() {
    class DataViewHolder(itemView: CategoryBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private var binding = itemView
        fun bind(
            getCategoryData: GetCategoryRespData,
            context: Context,
        ) {
            binding.botqueTv.text=getCategoryData.name
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CategoryAdapter.DataViewHolder(
            CategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    override fun getItemCount(): Int =
        getCategoryData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onBindViewHolder(holder: CategoryAdapter.DataViewHolder, position: Int) {
        holder.bind(
            getCategoryData[position],
            context,
        )

    }
    fun filterList(adminUsersData: MutableList<GetCategoryRespData>) {
        this.getCategoryData = getCategoryData
        notifyDataSetChanged()
    }

    fun addData(list: List<GetCategoryRespData>) {
        this.getCategoryData = list
    }

}