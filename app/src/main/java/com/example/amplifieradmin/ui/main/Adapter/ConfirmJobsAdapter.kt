package com.example.amplifieradmin.ui.main.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.amplifieradmin.R
import com.example.amplifieradmin.data.model.ConfirmedJobsRespData
import com.example.amplifieradmin.databinding.ConfirmjoibslayoutBinding

class ConfirmJobsAdapter(
    private var confirmedJobsRespData: List<ConfirmedJobsRespData>,
    private val context: Context,
    private var onBlockClick:OnBlockClick
): RecyclerView.Adapter<ConfirmJobsAdapter.DataViewHolder>(){

    class DataViewHolder(itemView: ConfirmjoibslayoutBinding):
        RecyclerView.ViewHolder(itemView.root){
        private var binding = itemView
        fun bind(
            confirmedJobsRespData: ConfirmedJobsRespData,
            context: Context,
            onBlockClick: OnBlockClick
        ) {

            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(50))
            Glide.with(context).load(confirmedJobsRespData.user_img)
                //  .apply(requestOptions)
                .placeholder(R.drawable.user).into(binding.userpic)


            binding.userNameTv.text=confirmedJobsRespData.name
            binding.type.text="Posted by ${confirmedJobsRespData.type}"
            binding.tvTitle.text=confirmedJobsRespData.title
            binding.tvAddress.text=confirmedJobsRespData.address

            binding.blockBtn.setOnClickListener {
                onBlockClick.onBlockClick(confirmedJobsRespData.id)
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        ConfirmJobsAdapter. DataViewHolder(
            ConfirmjoibslayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    override fun getItemCount(): Int =
        confirmedJobsRespData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onBindViewHolder(holder:ConfirmJobsAdapter.DataViewHolder,position: Int) {
        holder.bind(
            confirmedJobsRespData[position],
            context,
            onBlockClick
        )

    }
    fun filterList(adminUsersData: MutableList<ConfirmedJobsRespData>) {
        this.confirmedJobsRespData=confirmedJobsRespData
        notifyDataSetChanged()
    }
    fun addData(list: List<ConfirmedJobsRespData>) {
        this.confirmedJobsRespData = list
    }

    interface OnBlockClick{
        fun onBlockClick(id:String)
    }

}