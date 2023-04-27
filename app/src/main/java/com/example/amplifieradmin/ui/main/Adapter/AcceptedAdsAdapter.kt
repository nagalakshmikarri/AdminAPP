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
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.amplifieradmin.R
import com.example.amplifieradmin.data.model.AcceptAdsData
import com.example.amplifieradmin.data.model.AdsPendingData
import com.example.amplifieradmin.databinding.AcceptadsBinding
import com.example.amplifieradmin.databinding.AlertDialogBinding
import com.example.amplifieradmin.databinding.PendingBinding
import kotlinx.android.synthetic.main.acceptads.view.*
import kotlinx.android.synthetic.main.pending.view.*
import java.text.SimpleDateFormat
import java.util.*

class AcceptedAdsAdapter(
    private var acceptAdsData: List<AcceptAdsData>,
    private val context: Context,
    private var onRejectClick: AcceptedAdsAdapter.OnRejectClick,
    ) : RecyclerView.Adapter<AcceptedAdsAdapter.DataViewHolder>() {
    class DataViewHolder(itemView: AcceptadsBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private var binding = itemView
        fun bind(
            acceptAdsData: AcceptAdsData,
            context: Context,
        ) {


            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(50))
            Glide.with(context).load(acceptAdsData.s_user_img)
                //  .apply(requestOptions)
                .placeholder(R.drawable.user).into(binding.userpic)

            binding.userNameTv.text = acceptAdsData.s_business
            binding.title.text = acceptAdsData.title
            binding.description.text = acceptAdsData.descript
            binding.tvWatchnow.text = acceptAdsData.link_name
            Glide.with(context).load(acceptAdsData.img).into(binding.bannerImage)


            val inputPattern = "yyyy-MM-dd"
            val outputPattern = "MMM dd, yyyy"
            val inputFormat = SimpleDateFormat(inputPattern, Locale.US)
            val outputFormat = SimpleDateFormat(outputPattern, Locale.US)

            val sDate = inputFormat.parse(acceptAdsData.sdate)
            val eDate = inputFormat.parse(acceptAdsData.edate)

            binding.startDateTv.text = outputFormat.format(sDate)+" \u00b7 " +outputFormat.format(eDate)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AcceptedAdsAdapter.DataViewHolder(
            AcceptadsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    override fun getItemCount(): Int =
        acceptAdsData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }


    fun filterList(adminUsersData: MutableList<AcceptAdsData>) {
        this.acceptAdsData = acceptAdsData
        notifyDataSetChanged()
    }

    fun addData(list: List<AcceptAdsData>) {
        this.acceptAdsData = list
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(
            acceptAdsData[position],
            context,
        )
        holder.itemView.btn_rejected.setOnClickListener {
            val dialog = Dialog(context)
            val inflaterBlock =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val bindingDialog = AlertDialogBinding.inflate(inflaterBlock)

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(bindingDialog.root)

            bindingDialog.tvSubTitle.text = "Are you sure do you want to Reject Post?"
            bindingDialog.btnCancel.setOnClickListener { dialog.dismiss() }
            bindingDialog.cancelIv.setOnClickListener { dialog.dismiss() }


            bindingDialog.btnYes.setOnClickListener {
                onRejectClick.onRejectClick(acceptAdsData[position].id,"2")
                dialog.dismiss()
            }

            dialog.show()


        }


    }
    interface OnRejectClick{
        fun onRejectClick(RejectedId:String,reject:String)
    }

}