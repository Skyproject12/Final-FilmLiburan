package com.example.filmliburan.Data.Source.Local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.filmliburan.Data.Model.Movie;
import com.example.filmliburan.Data.Model.TvShow;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;


import static com.example.filmliburan.Data.Source.Local.DatabaseContract.MovieColumn.TABLE_MOVIE;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.TvshowColumn.TABLE_TVSHOW;

public class FavoriteHelper {
    private static final String DATABASE_MOVIE= TABLE_MOVIE;
    private static final String DATABASE_TVSHOW= TABLE_TVSHOW;
    private static DatabaseHelper databaseHelper;
    private static FavoriteHelper INSTANCE;
    private static SQLiteDatabase database;
    private FavoriteHelper (Context context){
        databaseHelper= new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstace(Context context){
        if(INSTANCE == null){
            synchronized (SQLiteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE= new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }
    public void open() throws SQLException{
        database= databaseHelper.getWritableDatabase();
    }
    public void close(){
        databaseHelper.close();
        if(database.isOpen()){
            database.close();
        }
    }

    public int deleteMovieById(String id){
        return database.delete(DATABASE_MOVIE, _ID+ " = ?", new String[]{id});

    }

    public long insertMovie(ContentValues values) {
        return database.insert(DATABASE_MOVIE, null, values);

    }

    public int updateMovie(String id, ContentValues values){
        return  database.update(DATABASE_MOVIE, values, _ID+ " = ? ", new String[]{id});

    }

    public Cursor queryAllMovie(){
        return database.query(DATABASE_MOVIE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC ");
    }

    public Cursor queryByIdMovie(String id){
        return database.query(DATABASE_MOVIE, null
                , _ID+ " = ? "
                , new String []{id}
                , null
                , null
                , null);
    }

    public int deleteTvshowById(String id){
        return database.delete(DATABASE_TVSHOW, _ID+ " = ?", new String[]{id});
    }

    public long insertTvshow(ContentValues values){
        return database.insert(DATABASE_TVSHOW, null, values);
    }

    public int updateTvshow(String id, ContentValues values){
        return database.update(DATABASE_TVSHOW, values,_ID+" = ?", new String []{id});
    }

    public Cursor queryAllTvshow(){
        return database.query(DATABASE_TVSHOW,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC ");
    }

    public Cursor queryByIdTvshow(String id){
        return database.query(DATABASE_TVSHOW, null
                , _ID + " = ?"
                , new String []{id}
                , null
                , null
                ,null);
    }


}
