package com.example.amplifieradmin.ui.main.Adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.amplifieradmin.data.model.AcceptAdsData
import com.example.amplifieradmin.data.model.RejectAdsData
import com.example.amplifieradmin.databinding.AcceptadsBinding
import com.example.amplifieradmin.databinding.AlertDialogBinding
import com.example.amplifieradmin.databinding.RejectedBinding
import kotlinx.android.synthetic.main.pending.view.*

class RejectedAdsAdapter
    (
    private var rejectAdsData: List<RejectAdsData>,
    private val context: Context,
    private var onAcceptClick: RejectedAdsAdapter.OnAcceptClick,
) : RecyclerView.Adapter<RejectedAdsAdapter.DataViewHolder>(){
    class DataViewHolder(itemView: RejectedBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private var binding = itemView
        fun bind(
            rejectAdsData:RejectAdsData,
            context: Context,
        ) {
            Glide.with(context).load(rejectAdsData.s_user_img).into(binding.userpic)
            binding.userNameTv.text = rejectAdsData.s_business
            binding.title.text = rejectAdsData.title
            binding.description.text = rejectAdsData.descript
            binding.tvWatchnow.text = rejectAdsData.link_name
            Glide.with(context).load(rejectAdsData.img).into(binding.bannerImage)

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RejectedAdsAdapter.DataViewHolder(
            RejectedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    override fun getItemCount(): Int =
        rejectAdsData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
    fun filterList(adminUsersData: MutableList<RejectAdsData>) {
        this.rejectAdsData= rejectAdsData
        notifyDataSetChanged()
    }
    fun addData(list: List<RejectAdsData>) {
        this.rejectAdsData = list
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(
            rejectAdsData[position],
            context,
        )
        holder.itemView.accepted_btn.setOnClickListener {
            val dialog = Dialog(context)
            val inflaterBlock =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val bindingDialog = AlertDialogBinding.inflate(inflaterBlock)

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(bindingDialog.root)

            bindingDialog.tvSubTitle.text = "Are you sure do you want to Accept Post?"
            bindingDialog.btnCancel.setOnClickListener { dialog.dismiss() }
            bindingDialog.cancelIv.setOnClickListener { dialog.dismiss() }


            bindingDialog.btnYes.setOnClickListener {
                onAcceptClick.onAcceptClick(rejectAdsData[position].id, "1")
                dialog.dismiss()
            }

            dialog.show()


        }


    }
    interface OnAcceptClick{
        fun onAcceptClick(acceptdId: String,accept: String)
    }

}