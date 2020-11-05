package com.ix.ibrahim7.mediaplayer.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlbumFile(
    val album:String,
    val artist:String,
    val artist_id:String,
    val path:String?
):Parcelable