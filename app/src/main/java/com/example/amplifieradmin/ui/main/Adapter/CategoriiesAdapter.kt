package com.example.amplifieradmin.ui.main.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amplifieradmin.data.model.GetCategoriesRespData
import com.example.amplifieradmin.data.model.ListInviteTypeRespData
import com.example.amplifieradmin.databinding.CategoriesLayoutBinding
import kotlinx.android.synthetic.main.businessscategory.view.*

class CategoriiesAdapter(
    private var getCategoriesRespData: List<GetCategoriesRespData>,
    private val context: Context,
) : RecyclerView.Adapter<CategoriiesAdapter.DataViewHolder>() {
    public var selectedPosition = -1
    private val selectedList: ArrayList<String> = java.util.ArrayList<String>()

    class DataViewHolder(itemView: CategoriesLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private var binding = itemView
        fun bind(
            getCategoriesRespData: GetCategoriesRespData,
            context: Context
        ) {
            binding.artcenter.text=getCategoriesRespData.name
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CategoriiesAdapter.DataViewHolder(
            CategoriesLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )

    override fun getItemCount(): Int =
        getCategoriesRespData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onBindViewHolder(
        holder: CategoriiesAdapter.DataViewHolder,
        position: Int
    ) {

        holder.bind(
            getCategoriesRespData[position],
            context,
        )
        val serviceList = getCategoriesRespData[position]

        holder.itemView.check_box.isChecked = selectedPosition == position

        if (selectedPosition == -1) {
            holder.itemView.check_box.isChecked = false
        } else {
            holder.itemView.check_box.isChecked = selectedPosition == position
        }

/*
        if (serviceList.selected == 1) {
            holder.itemView.check_box.isChecked = true
            selectedList.add(serviceList.id)

        }
*/
/*
        holder.itemView.business_rl.setOnClickListener(View.OnClickListener {
            holder.itemView.check_box.isChecked = true
            if (selectedPosition != position) {
                notifyItemChanged(selectedPosition)
                selectedPosition = position
            }
        })
*/
       holder.itemView.business_rl.setOnClickListener(View.OnClickListener {
            holder.itemView.check_box.isChecked = !holder.itemView.check_box.isChecked
            val chkStatus: Boolean = holder.itemView.check_box.isChecked()
            if (chkStatus) {
/*                notifyItemChanged(selectedPosition)
                selectedPosition = position*/
                selectedList.add(serviceList.id)


            } else {
/*                notifyItemChanged(selectedPosition)
                selectedPosition = position*/
              //  selectedList.remove(serviceList.id)

            }
            Log.e("TAG", "onBindViewHolder: " + selectedPosition)
        })


    }
    fun filterList(adminUsersData: MutableList<GetCategoriesRespData>) {
        this.getCategoriesRespData = getCategoriesRespData
        notifyDataSetChanged()
    }

    fun addData(list: List<GetCategoriesRespData>) {
        this.getCategoriesRespData = list
    }

}