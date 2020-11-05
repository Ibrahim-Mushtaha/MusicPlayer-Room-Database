package com.ix.ibrahim7.mediaplayer.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ix.ibrahim7.mediaplayer.R
import com.ix.ibrahim7.mediaplayer.databinding.ItemSongBinding
import com.ix.ibrahim7.mediaplayer.model.SongModel
import kotlinx.android.synthetic.main.item_song.view.*


class SongAdapter(
    var activity:Activity, var data: MutableList<SongModel>, val itemclick: onClick
) :
        RecyclerView.Adapter<SongAdapter.MyViewHolder>() {


    class MyViewHolder(val item: ItemSongBinding) : RecyclerView.ViewHolder(item.root) {


        fun bind(n: SongModel) {
            item.song = n
            item.executePendingBindings()
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView_layout: ItemSongBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_song, parent, false)
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

        holder.item.allCard.setOnClickListener {
            itemclick.onClickItem(holder.adapterPosition,1)
        }
        Glide.with(activity).asBitmap().load(currentItem.albumArt).error(R.drawable.ic_album_cover).into(holder.itemView.tv_song_image)


    }

    interface onClick {
        fun onClickItem(position: Int, type: Int)
    }







}
