package com.harsha.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.harsha.common.Constants.ZERO
import com.harsha.data.model.ResponseData
import com.harsha.ui.splashactivity.databinding.StoreListAdapterBinding

class StoreAdapter(storeData:ArrayList<ResponseData.Result>): RecyclerView.Adapter<StoreAdapter.ViewHolder>() {
    var storeData:ArrayList<ResponseData.Result>
    lateinit var mContext: Context
    init {
        this.storeData=storeData;
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext=parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
                var binding: StoreListAdapterBinding= StoreListAdapterBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if(storeData!=null) storeData.size else ZERO
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(storeData.get(position).icon!=null && !storeData.get(position).icon.equals("")){
            Glide.with(mContext).load(storeData.get(position).icon).into(holder.storeListAdapterBinding.ivIcon)
        }
        holder.storeListAdapterBinding.tvStoreName.text= if(storeData.get(position).name!=null) storeData.get(position).name else ""
        holder.storeListAdapterBinding.tvPrice.text= if(storeData.get(position).priceLevel!=null) storeData.get(position).priceLevel.toString() else ""
        holder.storeListAdapterBinding.tvCloseTime.text = if(storeData.get(position).businessStatus!=null) storeData.get(position).businessStatus else ""
        holder.storeListAdapterBinding.tvVinicity.text = if(storeData.get(position).vicinity!=null) storeData.get(position).vicinity else ""
        holder.storeListAdapterBinding.tvRating.text = if(storeData.get(position).rating!=null) storeData.get(position).rating.toString() else ""


    }

    class ViewHolder(storeListAdapterBinding: StoreListAdapterBinding):RecyclerView.ViewHolder(storeListAdapterBinding.root) {
        var storeListAdapterBinding:StoreListAdapterBinding
        init {
            this.storeListAdapterBinding=storeListAdapterBinding
        }

    }
}