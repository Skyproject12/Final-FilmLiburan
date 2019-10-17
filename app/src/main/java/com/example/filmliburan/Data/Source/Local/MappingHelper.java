package com.example.filmliburan.Data.Source.Local;

import android.database.Cursor;

import com.example.filmliburan.Data.Model.Movie;
import com.example.filmliburan.Data.Model.TvShow;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<Movie> mapCursorMovieToArrayList(Cursor movieCursor){
        ArrayList<Movie> movieList= new ArrayList<>();
        while (movieCursor.moveToNext()){
            int id= movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumn._ID));
            String judul= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumn.JUDUL));
            String deskripsi= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumn.DESKRIPSI));
            String gambarpopuler= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumn.GAMBARPOPULE));
            double populer= movieCursor.getDouble(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumn.POPULER));
            String originallanguage= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumn.ORIGINALLANGUAGE));
            int genre= movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumn.GENRE));
            String releasedate= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumn.RELEASEDATE));
            movieList.add(new Movie(id,judul,deskripsi, gambarpopuler, populer, originallanguage, genre, releasedate));

        }
        return movieList;
    }

    public static Movie mapCursorMovieToObject(Cursor movieCursor){
        movieCursor.moveToFirst();
        int id= movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumn._ID));
        String judul= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumn.JUDUL));
        String deskripsi= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumn.DESKRIPSI));
        String gambarpopuler= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumn.GAMBARPOPULE));
        double populer= movieCursor.getDouble(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumn.POPULER));
        String originallanguage= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumn.ORIGINALLANGUAGE));
        int genre= movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumn.GENRE));
        String releasedate= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumn.RELEASEDATE));

        return new Movie(id, judul, deskripsi, gambarpopuler, populer, originallanguage, genre, releasedate);

    }

    public static ArrayList<TvShow> mapCursorTvshowToArrayList(Cursor tvshowCursor){
        ArrayList<TvShow> movieList= new ArrayList<>();
        while (tvshowCursor.moveToNext()){
            int id= tvshowCursor.getInt(tvshowCursor.getColumnIndexOrThrow(DatabaseContract.TvshowColumn._ID));
            String judul= tvshowCursor.getString(tvshowCursor.getColumnIndexOrThrow(DatabaseContract.TvshowColumn.JUDUL));
            String deskripsi= tvshowCursor.getString(tvshowCursor.getColumnIndexOrThrow(DatabaseContract.TvshowColumn.DESKRIPSI));
            String gambarpopuler= tvshowCursor.getString(tvshowCursor.getColumnIndexOrThrow(DatabaseContract.TvshowColumn.GAMBARPOPULE));
            double populer= tvshowCursor.getDouble(tvshowCursor.getColumnIndexOrThrow(DatabaseContract.TvshowColumn.POPULER));
            String originallanguage= tvshowCursor.getString(tvshowCursor.getColumnIndexOrThrow(DatabaseContract.TvshowColumn.ORIGINALLANGUAGE));
            int genre= tvshowCursor.getInt(tvshowCursor.getColumnIndexOrThrow(DatabaseContract.TvshowColumn.GENRE));
            String releasedate= tvshowCursor.getString(tvshowCursor.getColumnIndexOrThrow(DatabaseContract.TvshowColumn.RELEASEDATE));
            movieList.add(new TvShow(id,judul, gambarpopuler, populer, deskripsi , originallanguage, genre, releasedate));

        }
        return movieList;
    }

    public static TvShow mapCursorTvshowToObject(Cursor tvshowCursor){
        tvshowCursor.moveToFirst();
        int id= tvshowCursor.getInt(tvshowCursor.getColumnIndexOrThrow(DatabaseContract.TvshowColumn._ID));
        String judul= tvshowCursor.getString(tvshowCursor.getColumnIndexOrThrow(DatabaseContract.TvshowColumn.JUDUL));
        String deskripsi= tvshowCursor.getString(tvshowCursor.getColumnIndexOrThrow(DatabaseContract.TvshowColumn.DESKRIPSI));
        String gambarpopuler= tvshowCursor.getString(tvshowCursor.getColumnIndexOrThrow(DatabaseContract.TvshowColumn.GAMBARPOPULE));
        double populer= tvshowCursor.getDouble(tvshowCursor.getColumnIndexOrThrow(DatabaseContract.TvshowColumn.POPULER));
        String originallanguage= tvshowCursor.getString(tvshowCursor.getColumnIndexOrThrow(DatabaseContract.TvshowColumn.ORIGINALLANGUAGE));
        int genre= tvshowCursor.getInt(tvshowCursor.getColumnIndexOrThrow(DatabaseContract.TvshowColumn.GENRE));
        String releasedate= tvshowCursor.getString(tvshowCursor.getColumnIndexOrThrow(DatabaseContract.TvshowColumn.RELEASEDATE));

        return new TvShow(id, judul, gambarpopuler, populer, deskripsi , originallanguage, genre, releasedate);

    }
}
