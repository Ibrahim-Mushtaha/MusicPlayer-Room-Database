package com.ix.ibrahim7.mediaplayer.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ix.ibrahim7.mediaplayer.R
import com.ix.ibrahim7.mediaplayer.databinding.ItemSongBinding
import com.ix.ibrahim7.mediaplayer.model.SongModel
import kotlinx.android.synthetic.main.item_song.view.*


class SongAdapter(
    var activity:Activity, var mdata: MutableList<SongModel>, val itemclick: onClick,val type: Int
) :
        RecyclerView.Adapter<SongAdapter.MyViewHolder>() , Filterable {

    var data: MutableList<SongModel>? = mdata

    lateinit var filteredList:ArrayList<SongModel>

    private val _SongSearchModelLiveData = MutableLiveData<Int>()

    val SongSearchModelliveData: LiveData<Int>
        get() = _SongSearchModelLiveData

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
        return data!!.size
    }




    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = data!![position]
        holder.bind(currentItem)

        holder.item.apply {
            allCard.setOnClickListener {
                itemclick.onClickItem(holder.adapterPosition, 1)
            }

            if (type == 0) {
               btnMore.visibility = View.GONE
            } else {
              btnMore.visibility = View.VISIBLE
            }

            btnMore.setOnClickListener {
                val popupMenu: PopupMenu = PopupMenu(activity,it)
                popupMenu.menuInflater.inflate(R.menu.popup_menu,popupMenu.menu)
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    when(item.itemId) {
                        R.id.favorite ->{
                            itemclick.onClickItem(holder.adapterPosition, 2)
                        }

                    }
                    true
                })
                popupMenu.show()
            }

            Glide.with(activity).asBitmap().load(currentItem.albumArt)
                .error(R.drawable.ic_album_cover).into(holder.itemView.tv_song_image)

        }

    }

    interface onClick {
        fun onClickItem(position: Int, type: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                data = results!!.values as ArrayList<SongModel>
                notifyDataSetChanged()
            }

            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val charString: String = constraint.toString()
                if (charString.isEmpty())
                    data = mdata
                else {
                    filteredList = ArrayList<SongModel>()
                    for (i in 0 until data!!.size) {
                        if (data!![i].title.toLowerCase()
                                .contains(charString.toLowerCase())
                        ) {
                            filteredList.add(data!![i])
                        }
                    }
                    if (filteredList.size == 0){
                        _SongSearchModelLiveData.postValue(0)
                    }else{
                        _SongSearchModelLiveData.postValue(1)
                    }
                    data = filteredList


                }
                val filteredResult = FilterResults()
                filteredResult.values = data
                return filteredResult
            }


        }
    }


}
