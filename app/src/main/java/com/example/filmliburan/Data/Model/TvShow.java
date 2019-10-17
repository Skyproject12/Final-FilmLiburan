package com.example.filmliburan.Data.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TvShow implements Parcelable {
    int id;
    String judul;
    String gambar;
    double populer;
    String deskripsi;
    String originalLanguage;
    int genre;
    String releaseDate;

    public TvShow(int id, String judul, String gambar, double populer, String deskripsi, String originalLanguage, int genre, String releaseDate) {
        this.id = id;
        this.judul = judul;
        this.gambar = gambar;
        this.populer = populer;
        this.deskripsi = deskripsi;
        this.originalLanguage = originalLanguage;
        this.genre = genre;
        this.releaseDate = releaseDate;
    }

    public TvShow() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public double getPopuler() {
        return populer;
    }

    public void setPopuler(double populer) {
        this.populer = populer;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }



    public TvShow(JSONObject object) throws JSONException {
        String judulShow= object.getString("name");
        String gambarShow= object.getString("poster_path");
        String gambar="https://image.tmdb.org/t/p/w185/"+  gambarShow  ;
        double populer= object.getDouble("popularity");
        String deskripsi= object.getString("overview");
        String originalLanguage= object.getString("original_language");
        JSONArray array= object.getJSONArray("genre_ids");
        int id= object.getInt("id");
        for(int i=0; i<array.length(); i++){
            int genre= array.getInt(i);
            this.genre= genre;
        }
        String releaseDate= object.getString("first_air_date");

        this.judul= judulShow;
        this.gambar= gambar;
        this.populer= populer;
        this.deskripsi= deskripsi;
        this.originalLanguage= originalLanguage;
        this.releaseDate= releaseDate;
        this.id=id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.judul);
        dest.writeString(this.gambar);
        dest.writeDouble(this.populer);
        dest.writeString(this.deskripsi);
        dest.writeString(this.originalLanguage);
        dest.writeInt(this.genre);
        dest.writeString(this.releaseDate);
    }

    protected TvShow(Parcel in) {
        this.id = in.readInt();
        this.judul = in.readString();
        this.gambar = in.readString();
        this.populer = in.readDouble();
        this.deskripsi = in.readString();
        this.originalLanguage = in.readString();
        this.genre = in.readInt();
        this.releaseDate = in.readString();
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
}
