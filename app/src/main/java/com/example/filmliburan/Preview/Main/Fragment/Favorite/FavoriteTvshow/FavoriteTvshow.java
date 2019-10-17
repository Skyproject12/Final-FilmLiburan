package com.example.filmliburan.Preview.Main.Fragment.Favorite.FavoriteTvshow;


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

import com.example.filmliburan.Data.Model.TvShow;
import com.example.filmliburan.Data.Source.Local.DatabaseContract;
import com.example.filmliburan.Data.Source.Local.LoadTvShowCallback;
import com.example.filmliburan.Data.Source.Local.MappingHelper;
import com.example.filmliburan.Preview.Detail.DetailTvShowActivity;
import com.example.filmliburan.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.filmliburan.Data.Source.Local.DatabaseContract.TvshowColumn.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvshow extends Fragment implements LoadTvShowCallback {

    View view;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    FavoriteTvshowAdapter favoriteTvmovieAdapter;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    TextView textKosong;


    public FavoriteTvshow() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favorite_tvshow, container, false);
        recyclerView = view.findViewById(R.id.recycler_favorite_tvshow);
        progressBar = view.findViewById(R.id.progressTvshow);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        textKosong = view.findViewById(R.id.text_kosong);
        favoriteTvmovieAdapter = new FavoriteTvshowAdapter(getActivity());
        recyclerView.setAdapter(favoriteTvmovieAdapter);
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver myObserver = new DataObserver(handler, getContext());
        getActivity().getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);
        if (savedInstanceState == null) {
            new LoadfavoriteAsnyc(getContext(), this).execute();
        } else {
            ArrayList<TvShow> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                favoriteTvmovieAdapter.setListTvShow(list);
            }
        }
        IntentToDetail();
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            outState.putParcelableArrayList(EXTRA_STATE, favoriteTvmovieAdapter.getListMovie());
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
    public void postExecute(ArrayList<TvShow> tvShows) {
        progressBar.setVisibility(View.INVISIBLE);
        if (tvShows.size() > 0) {
            textKosong.setVisibility(View.GONE);
            favoriteTvmovieAdapter.setListTvShow(tvShows);
        } else {
            textKosong.setVisibility(View.VISIBLE);
            favoriteTvmovieAdapter.setListTvShow(new ArrayList<TvShow>());
        }
    }

    private static class LoadfavoriteAsnyc extends AsyncTask<Void, Void, ArrayList<TvShow>> {

        private final WeakReference<LoadTvShowCallback> weakCallback;
        private final WeakReference<Context> weakContext;

        private LoadfavoriteAsnyc(Context context, LoadTvShowCallback callback) {
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
        protected ArrayList<TvShow> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor dataCursor = context.getContentResolver().query(CONTENT_URI, null, null, null);
            return MappingHelper.mapCursorTvshowToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<TvShow> tvShows) {
            super.onPostExecute(tvShows);
            weakCallback.get().postExecute(tvShows);
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
        favoriteTvmovieAdapter.setOnItemCallback(new FavoriteTvshowAdapter.OnItemClickCallback() {
            @Override
            public void onItmCliked(TvShow tvShow) {

                Intent intent = new Intent(getActivity(), DetailTvShowActivity.class);
                intent.putExtra("intent", tvShow);
                Uri currentTvshow = ContentUris.withAppendedId(DatabaseContract.TvshowColumn.CONTENT_URI, tvShow.getId());
                intent.setData(currentTvshow);
                startActivity(intent);
            }
        });
    }

}
