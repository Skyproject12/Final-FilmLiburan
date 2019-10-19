package com.example.filmliburan.Preview.Main.Fragment.TvShow;


import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.appcompat.widget.SearchView;

import com.example.filmliburan.Data.Model.TvShow;
import com.example.filmliburan.Data.Source.Local.DatabaseContract;
import com.example.filmliburan.Data.Source.Remote.TvShow.TvShowViewModel;
import com.example.filmliburan.Preview.Detail.DetailTvShowActivity;
import com.example.filmliburan.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    TvshowAdapter adapter;
    View view;
    ProgressBar progressBar;
    Toolbar toolbar;
    TvShowViewModel tvShowViewModel;
    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        adapter= new TvshowAdapter();
        RecyclerView recyclerView= view.findViewById(R.id.recycler_tvshow);
        toolbar= view.findViewById(R.id.toolbar_tvshow);
        progressBar= view.findViewById(R.id.progressBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("TvShow");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        tvShowViewModel= ViewModelProviders.of(getActivity()).get(TvShowViewModel.class);

        tvShowViewModel.setTvshow();
        tvShowViewModel.getTvshow().observe(getActivity(), getTvShowListr);
        IntentToDetailActivity();
        setHasOptionsMenu(true);
        return view;
    }

    public Observer<ArrayList<TvShow>> getTvShowListr= new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvShows) {
            getProgress(true);
            if(tvShows!=null) {
                adapter.setItem(tvShows);
                if (adapter != null) {
                    getProgress(false);
                }
            }
        }
    };

    public void getProgress(Boolean progress){
        if(progress){
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        ((AppCompatActivity)getActivity()).getMenuInflater().inflate(R.menu.menu_navigation,menu);
        MenuItem searchItem= menu.findItem(R.id.action_search);
        SearchView searchView= (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()>0) {
                    tvShowViewModel.searchJoker(newText);
                }
                else{
                    tvShowViewModel.setTvshow();
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.setting:
                Intent intent= new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void IntentToDetailActivity(){
        adapter.setOnItemClickCallback(new TvshowAdapter.OnItemClickCallback() {
            @Override
            public void onItmCliked(TvShow tvShow) {
                Intent intent= new Intent(getActivity(), DetailTvShowActivity.class);
                intent.putExtra("intent",tvShow);
                Uri currentTvshow= ContentUris.withAppendedId(DatabaseContract.TvshowColumn.CONTENT_URI, tvShow.getId());
                intent.setData(currentTvshow);
                startActivity(intent);
            }
        });
    }
}
