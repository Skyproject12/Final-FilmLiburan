package com.example.filmliburan.Preview.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.example.filmliburan.Preview.Main.Fragment.Favorite.FavoriteFragment;
import com.example.filmliburan.Preview.Main.Fragment.Movies.MoviesFragment;
import com.example.filmliburan.Preview.Main.Fragment.TvShow.TvShowFragment;
import com.example.filmliburan.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private MoviesFragment movieFragment;
    private TvShowFragment tvShowFragment;
    private FavoriteFragment favoriteFragment;
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment= new MoviesFragment();
    public static final String KEY_FRAGMENT = "fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.movies:
                        fragment= new MoviesFragment();
                        break;
                    case R.id.tv_show:
                        fragment= new TvShowFragment();
                        break;
                    case R.id.favorite:
                        fragment= new FavoriteFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
                return true;
            }
        });
        if (savedInstanceState == null) getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
        else {
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, KEY_FRAGMENT);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
        }
    }

    private void initial() {
        movieFragment= new MoviesFragment();
        tvShowFragment= new TvShowFragment();
        favoriteFragment= new FavoriteFragment();
        bottomNavigationView= findViewById(R.id.menu_buttom_main);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT, fragment);
        super.onSaveInstanceState(outState);
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
