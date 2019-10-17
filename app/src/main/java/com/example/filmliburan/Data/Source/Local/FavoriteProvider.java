package com.example.filmliburan.Data.Source.Local;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import static com.example.filmliburan.Data.Source.Local.DatabaseContract.AUTHORITY;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.MovieColumn.TABLE_MOVIE;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.TvshowColumn.TABLE_TVSHOW;

public class FavoriteProvider extends ContentProvider {
    public FavoriteProvider() {
    }

    private static final int MOVIE=1;
    private static final int MOVIE_ID=2;
    private static final int Tvshow=3;
    private static final int Tvshow_ID=4;
    private FavoriteHelper favoriteHelper;

    private static UriMatcher sUriMatcher= new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(AUTHORITY,TABLE_MOVIE, MOVIE);
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE+ "/#", MOVIE_ID);
        sUriMatcher.addURI(AUTHORITY, TABLE_TVSHOW, Tvshow);
        sUriMatcher.addURI(AUTHORITY, TABLE_TVSHOW + "/#", Tvshow_ID);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)){
            case MOVIE_ID:
                deleted= favoriteHelper.deleteMovieById(uri.getLastPathSegment());
                getContext().getContentResolver().notifyChange(DatabaseContract.MovieColumn.CONTENT_URI, null);
                break;
            case Tvshow_ID:
                deleted= favoriteHelper.deleteTvshowById(uri.getLastPathSegment());
                getContext().getContentResolver().notifyChange(DatabaseContract.TvshowColumn.CONTENT_URI, null);
                default:
                    deleted=0;
                    break;
        }
        return deleted;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long added;
        switch (sUriMatcher.match(uri)){
            case MOVIE:
                added= favoriteHelper.insertMovie(values);
                getContext().getContentResolver().notifyChange(DatabaseContract.MovieColumn.CONTENT_URI, null);
                return Uri.parse(DatabaseContract.MovieColumn.CONTENT_URI+"/"+added);
            case Tvshow:
                added= favoriteHelper.insertTvshow(values);
                getContext().getContentResolver().notifyChange(DatabaseContract.TvshowColumn.CONTENT_URI, null);
                return Uri.parse(DatabaseContract.TvshowColumn.CONTENT_URI+"/"+added);
            default:
                throw  new IllegalArgumentException("Insertion not support for "+uri);
        }

    }

    @Override
    public boolean onCreate() {
        favoriteHelper= FavoriteHelper.getInstace(getContext());
        favoriteHelper.open();
        return true;

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor cursor ;
        switch(sUriMatcher.match(uri)){
            case MOVIE:
                cursor= favoriteHelper.queryAllMovie();
                break;
            case MOVIE_ID:
                cursor= favoriteHelper.queryByIdMovie(uri.getLastPathSegment());
                break;
            case Tvshow:
                cursor= favoriteHelper.queryAllTvshow();
                break;
            case Tvshow_ID:
                cursor= favoriteHelper.queryByIdTvshow(uri.getLastPathSegment());
                break;
            default:
                cursor=null;
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int updated;
        switch (sUriMatcher.match(uri)){
            case MOVIE_ID:
                updated= favoriteHelper.updateMovie(uri.getLastPathSegment(), values);
                getContext().getContentResolver().notifyChange(DatabaseContract.MovieColumn.CONTENT_URI,null);
                break;
            case Tvshow_ID:
                updated= favoriteHelper.updateTvshow(uri.getLastPathSegment(), values);
                getContext().getContentResolver().notifyChange(DatabaseContract.MovieColumn.CONTENT_URI,null);
                break;
                default:
                    updated=0;
                    break;
        }
        return updated;
    }
}
