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
import com.ix.ibrahim7.mediaplayer.databinding.ItemArtistBinding
import com.ix.ibrahim7.mediaplayer.model.AlbumFile
import com.ix.ibrahim7.mediaplayer.model.ArtistModel
import com.ix.ibrahim7.mediaplayer.util.Constant.getAlbumArt
import kotlinx.android.synthetic.main.item_artist.view.*


class ArtistAdapter(
    var activity:Activity,var data: MutableList<ArtistModel>, val itemclick: onClick
) :
        RecyclerView.Adapter<ArtistAdapter.MyViewHolder>() {


    class MyViewHolder(val item: ItemArtistBinding) : RecyclerView.ViewHolder(item.root) {


        fun bind(n: ArtistModel) {
            item.album = n
            item.executePendingBindings()
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView_layout: ItemArtistBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_artist, parent, false)
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

            allCard.setOnClickListener {
                itemclick.onClickItem(holder.adapterPosition,1)
            }

            tv_album_count.text ="Number of song ${currentItem.songCount}"
            Glide.with(activity).asBitmap().load(data[position].artistImage!!).error(R.drawable.ic_album_cover).into(holder.itemView.tv_album_image)


        }

    }

    interface onClick {
        fun onClickItem(position: Int, type: Int)
    }







}
