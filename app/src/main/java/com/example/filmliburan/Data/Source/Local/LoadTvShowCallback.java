package com.example.filmliburan.Data.Source.Local;

import com.example.filmliburan.Data.Model.Movie;
import com.example.filmliburan.Data.Model.TvShow;

import java.util.ArrayList;

public interface LoadTvShowCallback {
    void preExecute();
    void postExecute(ArrayList<TvShow> tvShows);
}
