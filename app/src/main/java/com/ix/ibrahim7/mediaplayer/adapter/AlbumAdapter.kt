package com.ix.ibrahim7.mediaplayer.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ix.ibrahim7.mediaplayer.R
import com.ix.ibrahim7.mediaplayer.databinding.ItemAlbumBinding
import com.ix.ibrahim7.mediaplayer.databinding.ItemAlbumLinerBinding
import com.ix.ibrahim7.mediaplayer.model.AlbumModel
import kotlinx.android.synthetic.main.item_album.view.*
import kotlinx.android.synthetic.main.item_album_liner.view.*


class AlbumAdapter(
    var activity:Activity, var data: MutableList<AlbumModel>, val itemclick: onClick
) :
        RecyclerView.Adapter<AlbumAdapter.MyViewHolder>() {


    class MyViewHolder(val item: ItemAlbumLinerBinding) : RecyclerView.ViewHolder(item.root) {


        fun bind(n: AlbumModel) {
            item.album = n
            item.executePendingBindings()
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView_layout: ItemAlbumLinerBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_album_liner, parent, false)
        return MyViewHolder(
            itemView_layout
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }




    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = data[position]
        holder.bind(currentItem)

        holder.itemView.apply {
            Log.e("eee name", data[position].artistName)

            tv_album_name_liner.text=currentItem.name
            if (data[position].coverArt != null) {
                    Glide.with(activity).asBitmap().load(currentItem.coverArt!!).error(R.drawable.ic_album_cover)
                        .into(holder.itemView.tv_album_image)

            }else{
                tv_album_image.setImageResource(R.drawable.ic_album_cover)
              //  Log.e("Eeee image", data[position].path!!)
            }


        }

        holder.item.allCard.setOnClickListener {
            itemclick.onClickItem(holder.adapterPosition,1)
        }

    }

    interface onClick {
        fun onClickItem(position: Int, type: Int)
    }







}
