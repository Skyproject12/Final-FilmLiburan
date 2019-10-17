package com.example.filmliburan.Data.Source.Local;

import android.database.Cursor;

import com.example.filmliburan.Data.Model.Movie;

import java.util.ArrayList;

public interface LoadfavoriteCallback {
    void preExecute();
    void postExecute(ArrayList<Movie> movie);
}
