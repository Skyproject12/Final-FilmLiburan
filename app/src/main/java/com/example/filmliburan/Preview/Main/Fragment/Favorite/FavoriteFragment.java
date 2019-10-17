package com.example.filmliburan.Preview.Main.Fragment.Favorite;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.filmliburan.Preview.Main.Fragment.Favorite.FavoriteMovie.FavoriteMovie;
import com.example.filmliburan.Preview.Main.Fragment.Favorite.FavoriteTvshow.FavoriteTvshow;
import com.example.filmliburan.R;
import com.google.android.material.tabs.TabLayout;

public class FavoriteFragment extends Fragment {

    View view;
    Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_favorite, container, false);
        toolbar= view.findViewById(R.id.toolbar_favorite);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setTitle("Favorite");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));

        profilViewPager();
        return view;
    }
    private void profilViewPager() {
        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FavoriteMovie()); // index 2
        adapter.addFragment(new FavoriteTvshow()); //index 1
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.profil_viewPager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabsProfil);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(getString(R.string.movies));
        tabLayout.getTabAt(1).setText(getString(R.string.tvshow));
    }
}
