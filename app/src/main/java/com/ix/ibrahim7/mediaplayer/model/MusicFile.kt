package com.ix.ibrahim7.mediaplayer.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MusicFile(
val path:String,
val title:String,
val artist:String,
val album:String,
val duration: String
):Parcelable