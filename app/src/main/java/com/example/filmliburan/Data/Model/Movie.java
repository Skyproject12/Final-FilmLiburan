package com.example.filmliburan.Data.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Movie implements Parcelable {
    int id;
    String judul;
    String deskripsi;
    String gambar;
    double populer;
    String originalLanguege;
    int genre;
    String releaseDate;

    public Movie(int id, String judul, String deskripsi, String gambar, double populer, String originalLanguege, int genre, String releaseDate) {
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.gambar = gambar;
        this.populer = populer;
        this.originalLanguege = originalLanguege;
        this.genre = genre;
        this.releaseDate = releaseDate;
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

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
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

    public String getOriginalLanguege() {
        return originalLanguege;
    }

    public void setOriginalLanguege(String originalLanguege) {
        this.originalLanguege = originalLanguege;
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

    public Movie() {
    }

    public Movie(JSONObject object) throws JSONException {
        String posterPath= object.getString("poster_path");
        String judul= object.getString("title");
        String deskription= object.getString("overview");
        String poster= "https://image.tmdb.org/t/p/w185/" + posterPath ;
        double populer= object.getDouble("popularity");
        String originalLanguage= object.getString("original_language");
        JSONArray jsonArray= object.getJSONArray("genre_ids");
        int genre;
        for(int i=0; i<jsonArray.length(); i++){
            genre=jsonArray.getInt(i);
            this.genre=genre;
        }
        String releaseDate= object.getString("release_date");
        int id= object.getInt("id");

        this.gambar=poster;
        this.judul= judul;
        this.deskripsi= deskription;
        this.populer=populer;
        this.originalLanguege= originalLanguage;
        this.releaseDate=  releaseDate;
        this.id= id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.judul);
        dest.writeString(this.deskripsi);
        dest.writeString(this.gambar);
        dest.writeDouble(this.populer);
        dest.writeString(this.originalLanguege);
        dest.writeInt(this.genre);
        dest.writeString(this.releaseDate);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.judul = in.readString();
        this.deskripsi = in.readString();
        this.gambar = in.readString();
        this.populer = in.readDouble();
        this.originalLanguege = in.readString();
        this.genre = in.readInt();
        this.releaseDate = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
