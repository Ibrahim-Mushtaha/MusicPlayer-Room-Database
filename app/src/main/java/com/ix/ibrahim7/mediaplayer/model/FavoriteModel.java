package com.ix.ibrahim7.mediaplayer.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import kotlinx.android.parcel.Parcelize;


@Entity(tableName = "favorite_table")
@Parcelize
public class FavoriteModel implements Parcelable {
    @PrimaryKey
    private int id;
    private int trackNumber;
    private int year;
    private int albumId;
    private int artistId;
    private long duration;
    private long dateModified;
    private long dateAdded;
    private long bookmark;
    private String title;
    private String artistName;
    private String composer;
    private String albumName;
    private String albumArt;
    private String data;


    protected FavoriteModel(Parcel in) {
        id = in.readInt();
        trackNumber = in.readInt();
        year = in.readInt();
        albumId = in.readInt();
        artistId = in.readInt();
        duration = in.readLong();
        dateModified = in.readLong();
        dateAdded = in.readLong();
        bookmark = in.readLong();
        title = in.readString();
        artistName = in.readString();
        composer = in.readString();
        albumName = in.readString();
        albumArt = in.readString();
        data = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(trackNumber);
        dest.writeInt(year);
        dest.writeInt(albumId);
        dest.writeInt(artistId);
        dest.writeLong(duration);
        dest.writeLong(dateModified);
        dest.writeLong(dateAdded);
        dest.writeLong(bookmark);
        dest.writeString(title);
        dest.writeString(artistName);
        dest.writeString(composer);
        dest.writeString(albumName);
        dest.writeString(albumArt);
        dest.writeString(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FavoriteModel> CREATOR = new Creator<FavoriteModel>() {
        @Override
        public FavoriteModel createFromParcel(Parcel in) {
            return new FavoriteModel(in);
        }

        @Override
        public FavoriteModel[] newArray(int size) {
            return new FavoriteModel[size];
        }
    };

    public static FavoriteModel EMPTY() {
        return new FavoriteModel(0,"","","","","","",0,0,0,0,0,0,0,0);
    }

    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }

    public FavoriteModel(int id, String title, String artistName,
                         String composer, String albumName, String albumArt,
                         String data, int trackNumber, int year, long duration,
                         long dateModified, long dateAdded, int albumId, int artistId,
                         long bookmark) {
        this.id = id;
        this.title = title;
        this.artistName = artistName;
        this.composer = composer;
        this.albumName = albumName;
        this.albumArt = albumArt;
        this.data = data;
        this.trackNumber = trackNumber;
        this.year = year;
        this.duration = duration;
        this.dateModified = dateModified;
        this.dateAdded = dateAdded;
        this.albumId = albumId;
        this.artistId = artistId;
        this.bookmark = bookmark;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getComposer() {
        return composer;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getAlbumArt() {
        return albumArt;
    }

    public String getData() {
        return data;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public int getYear() {
        return year;
    }

    public long getDuration() {
        return duration;
    }

    public long getDateModified() {
        return dateModified;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public int getAlbumId() {
        return albumId;
    }

    public int getArtistId() {
        return artistId;
    }

    public long getBookmark() {
        return bookmark;
    }
}