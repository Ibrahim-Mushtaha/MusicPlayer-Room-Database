package com.ix.ibrahim7.mediaplayer.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by deadsec on 9/4/17.
 */

public class AlbumModel implements Parcelable {
    public final ArrayList<SongModel> songs;
    public int id;

    public AlbumModel(ArrayList<SongModel> songs) {
        this.songs = songs;
    }

    protected AlbumModel(Parcel in) {
        songs = in.createTypedArrayList(SongModel.CREATOR);
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(songs);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AlbumModel> CREATOR = new Creator<AlbumModel>() {
        @Override
        public AlbumModel createFromParcel(Parcel in) {
            return new AlbumModel(in);
        }

        @Override
        public AlbumModel[] newArray(int size) {
            return new AlbumModel[size];
        }
    };

    public void setId(int i) {
        this.id=i;
    }
    public ArrayList<SongModel> getAlbumSongs() {
        return songs;
    }

    public String getName() {
        if(songs.size()>0) {
            return songs.get(0).getAlbumName();
        }else {
            return " ";
        }
    }
    public int getNoOfSong() {
        return songs.size();
    }

    public String getCoverArt() {
        if(songs.size()>0) {
            return songs.get(0).getAlbumArt();
        }else {
            return " ";
        }
    }

    public int getArtistId() {
        if(songs.size()>0) {
            return songs.get(0).getArtistId();
        }else {
            return 0;
        }
    }
    public String getArtistName() {
     if(songs.size()>0) {
            return songs.get(0).getArtistName();
        }else {
            return "";
        }
    }
}
