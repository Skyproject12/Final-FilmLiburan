package com.example.filmliburan.Preview.Main.Fragment.Favorite.FavoriteMovie;


import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmliburan.Data.Model.Movie;
import com.example.filmliburan.Data.Source.Local.DatabaseContract;
import com.example.filmliburan.Data.Source.Local.LoadfavoriteCallback;
import com.example.filmliburan.Data.Source.Local.MappingHelper;
import com.example.filmliburan.Preview.Detail.DetailMovieActivity;
import com.example.filmliburan.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.filmliburan.Data.Source.Local.DatabaseContract.MovieColumn.CONTENT_URI;


public class FavoriteMovie extends Fragment implements LoadfavoriteCallback {

    private RecyclerView recyclerView;
    private FavoriteMovieAdapter favoriteMovieAdapter;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private ProgressBar progressBar;
    TextView textKosong;
    View view;


    public FavoriteMovie() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        recyclerView = view.findViewById(R.id.recycler_favorite_movie);
        progressBar = view.findViewById(R.id.progressMovie);
        textKosong = view.findViewById(R.id.text_kosong);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        favoriteMovieAdapter = new FavoriteMovieAdapter(getActivity());
        recyclerView.setAdapter(favoriteMovieAdapter);
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver myObserver = new DataObserver(handler, getContext());
        getActivity().getContentResolver().registerContentObserver(DatabaseContract.MovieColumn.CONTENT_URI, true, myObserver);
        if (savedInstanceState == null) {
            new LoadfavoriteAsnyc(getContext(), this).execute();
        } else {
            ArrayList<Movie> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                favoriteMovieAdapter.setListMovie(list);
            }
        }
        IntentToDetail();
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            outState.putParcelableArrayList(EXTRA_STATE, favoriteMovieAdapter.getListMovie());
        }
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<Movie> movie) {
        progressBar.setVisibility(View.INVISIBLE);
        if (movie.size() > 0) {
            textKosong.setVisibility(View.GONE);
            favoriteMovieAdapter.setListMovie(movie);
        } else {
            textKosong.setVisibility(View.VISIBLE);
            favoriteMovieAdapter.setListMovie(new ArrayList<Movie>());
        }
    }

    private static class LoadfavoriteAsnyc extends AsyncTask<Void, Void, ArrayList<Movie>> {

        private final WeakReference<LoadfavoriteCallback> weakCallback;
        private final WeakReference<Context> weakContext;

        private LoadfavoriteAsnyc(Context context, LoadfavoriteCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor dataCursor = context.getContentResolver().query(DatabaseContract.MovieColumn.CONTENT_URI, null, null, null);
            return MappingHelper.mapCursorMovieToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movie) {
            super.onPostExecute(movie);
            weakCallback.get().postExecute(movie);
        }
    }

    public static class DataObserver extends ContentObserver {

        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    }


    private void IntentToDetail() {
        favoriteMovieAdapter.setOnItemCallback(new FavoriteMovieAdapter.OnItemClickCallback() {
            @Override
            public void onItmCliked(Movie movie) {
                Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
                intent.putExtra("film", movie);
                Uri currentMovie = ContentUris.withAppendedId(CONTENT_URI, movie.getId());
                intent.setData(currentMovie);
                startActivity(intent);
            }
        });
    }


}
