package com.example.filmliburan.Preview.Main.Fragment.Movies;


import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import android.widget.Toast;


import com.example.filmliburan.Data.Model.Movie;
import com.example.filmliburan.Data.Source.Local.DatabaseContract;
import com.example.filmliburan.Data.Source.Remote.Movies.MovieViewModel;
import com.example.filmliburan.Preview.Detail.DetailMovieActivity;
import com.example.filmliburan.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {


    private MovieAdapter adapter;
    Toolbar toolbar;
    private ProgressBar progressBar;
    private MovieViewModel movieViewModel;
    View view;
    MenuItem searchItem;
    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_movies, container, false);
        progressBar= view.findViewById(R.id.progressBar);
        toolbar= view.findViewById(R.id.toolbar_movies);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Movie");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        RecyclerView recyclerView= view.findViewById(R.id.recycler_movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter= new MovieAdapter();
        recyclerView.setAdapter(adapter);
        movieViewModel = ViewModelProviders.of(getActivity()).get(MovieViewModel.class);
//        if(adapter!=null){
//            getProgress(false);
//        }

        // mengeset nilai movie
        movieViewModel.setMovie();
        getProgress(true);
        toolbar.setVisibility(View.INVISIBLE);
        // mengambil nilai movie lalu di set ke adapter
        movieViewModel.getMovie().observe(getActivity(), getMovieList);
        IntentToDetail();
        setHasOptionsMenu(true);
        return view;
    }

    public Observer<ArrayList<Movie>> getMovieList= new Observer<ArrayList<Movie>>(){
        @Override
        public void onChanged(ArrayList<Movie> movieslist){
            if(movieslist!=null){
                adapter.setItems(movieslist);
                if(adapter!=null) {
                    getProgress(false);
                    toolbar.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    public void getProgress(Boolean progress){
        if(progress){
            progressBar.setVisibility(View.VISIBLE);
        }
        else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void IntentToDetail(){
        adapter.setOnItemCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItmCliked(Movie movie) {
                String status="movie";
                Intent intent= new Intent(getActivity(), DetailMovieActivity.class);
                intent.putExtra("film",movie);
                Uri currentMovie= ContentUris.withAppendedId(DatabaseContract.MovieColumn.CONTENT_URI, movie.getId());
                intent.setData(currentMovie);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        ((AppCompatActivity)getActivity()).getMenuInflater().inflate(R.menu.menu_navigation, menu);
        searchItem= menu.findItem(R.id.action_search);
        SearchView searchView= (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
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

}
