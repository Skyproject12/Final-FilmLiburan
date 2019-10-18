package com.example.filmliburan.Preview.Main.Fragment.Stack;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.example.filmliburan.Data.Model.Movie;
import com.example.filmliburan.Data.Source.Local.DatabaseContract;
import com.example.filmliburan.Data.Source.Local.FavoriteHelper;
import com.example.filmliburan.Data.Source.Local.MappingHelper;
import com.example.filmliburan.R;

import java.util.ArrayList;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<Movie> listMovie;
    private final Context mContext;
    FavoriteHelper favoriteHelper;

    public StackRemoteViewsFactory(Context mContext) {
        this.mContext = mContext;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        Cursor dataCursor = mContext.getContentResolver().query(DatabaseContract.MovieColumn.CONTENT_URI, null, null, null);
        listMovie = MappingHelper.mapCursorMovieToArrayList(dataCursor);
        favoriteHelper = new FavoriteHelper(mContext);
        favoriteHelper.open();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDataSetChanged() {
        listMovie = favoriteHelper.getAllFavoriteMovie();
//
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listMovie.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        try {
            Bitmap bitmap = Glide.with(mContext)
                    .asBitmap()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .load(listMovie.get(position).getGambar())
                    .submit(512, 512)
                    .get();
            rv.setImageViewBitmap(R.id.imageView, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bundle extras = new Bundle();
        extras.putInt(ImageBannerWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
