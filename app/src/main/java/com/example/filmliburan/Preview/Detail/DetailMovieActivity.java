package com.example.filmliburan.Preview.Detail;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filmliburan.Data.Model.Movie;
import com.example.filmliburan.Data.Model.TvShow;
import com.example.filmliburan.Data.Source.Local.DatabaseContract;
import com.example.filmliburan.Data.Source.Local.FavoriteHelper;
import com.example.filmliburan.Preview.Main.MainActivity;
import com.example.filmliburan.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.MovieColumn.CONTENT_URI;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.MovieColumn.DESKRIPSI;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.MovieColumn.GAMBARPOPULE;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.MovieColumn.GENRE;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.MovieColumn.JUDUL;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.MovieColumn.ORIGINALLANGUAGE;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.MovieColumn.POPULER;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.MovieColumn.RELEASEDATE;

public class DetailMovieActivity extends AppCompatActivity {

    TextView textJudul;
    TextView textDeskripsi;
    ImageView gambarFilm;
    Movie movie;
    TextView populer;
    TextView originalLeangue;
    TextView genre;
    TextView releaseDate;
    Menu menu;
    FavoriteHelper favoriteHelper;
    ArrayList<Movie> listMovie;
    Uri currentUri;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        favoriteHelper= FavoriteHelper.getInstace(this.getApplicationContext());
        favoriteHelper.open();
        textJudul= findViewById(R.id.text_judul);
        textDeskripsi= findViewById(R.id.text_deskripsi);
        gambarFilm= findViewById(R.id.gambar_film);
        movie = getIntent().getParcelableExtra("film");
        listMovie= new ArrayList<>();
        textJudul.setText(movie.getJudul());
        textDeskripsi.setText(movie.getDeskripsi());
        Picasso.get().load(movie.getGambar()).into(gambarFilm);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        populer= findViewById(R.id.text_populer);
        originalLeangue= findViewById(R.id.text_originalLanguage);
        genre= findViewById(R.id.text_genre);
        releaseDate= findViewById(R.id.text_releasedate);
        populer.setText(String.valueOf(movie.getPopuler()));
        originalLeangue.setText(movie.getOriginalLanguege());
        genre.setText(String.valueOf(movie.getGenre()));
        releaseDate.setText(movie.getReleaseDate());
        Intent intent= getIntent();
        currentUri=intent.getData();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu=menu;
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        MenuItem menuItem= menu.findItem(R.id.favorite_detail);
        if(tampilFilm(movie.getId())){
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_star));
        }else if(!tampilFilm(movie.getId())){
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_starko));
        }
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        MenuItem menuItem= menu.findItem(R.id.favorite_detail);
        switch (item.getItemId()){
            case R.id.favorite_detail:
                tampilFilm(movie.getId());
                if(tampilFilm(movie.getId())){
                    menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_starko));
                    getContentResolver().delete(currentUri,null,null);
                }else if(!tampilFilm(movie.getId())){
                    menuItem.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_star));
                    ContentValues values= new ContentValues();
                    values.put(_ID,movie.getId());
                    values.put(JUDUL,movie.getJudul());
                    values.put(DESKRIPSI, movie.getDeskripsi());
                    values.put(GAMBARPOPULE, movie.getGambar());
                    values.put(POPULER, movie.getPopuler());
                    values.put(ORIGINALLANGUAGE, movie.getOriginalLanguege());
                    values.put(GENRE, movie.getGenre());
                    values.put(RELEASEDATE, movie.getReleaseDate());
                    Toast.makeText(this, "comtent"+CONTENT_URI, Toast.LENGTH_SHORT).show();
                    Uri masuk= getContentResolver().insert(CONTENT_URI, values);
                }
                break;
            case R.id.home:
                Intent intent= new  Intent(DetailMovieActivity.this, MainActivity.class);
                startActivity(intent);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private boolean tampilFilm(int position){
        String [] projection= {
                _ID,
                JUDUL,
                DESKRIPSI,
                GAMBARPOPULE,
                POPULER,
                ORIGINALLANGUAGE,
                GENRE,
                RELEASEDATE,
        };
        String selection= _ID + " = ?";
        String selectionArgs [] = new String [] {
                String.valueOf(position)
        };
        Cursor cursor= getContentResolver().query(
                currentUri,
                projection,
                selection,
                selectionArgs,
                null,
                null
        );
        if(cursor!=null){
            cursor.moveToFirst();
        }
        boolean Faforite;
        listMovie= new ArrayList<>();
        for (int i = 0; i <cursor.getCount() ; i++) {
            int id= cursor.getInt(cursor.getColumnIndexOrThrow(_ID)) ;
            String judul= cursor.getString(cursor.getColumnIndexOrThrow(JUDUL));
            String deskripsi= cursor.getString(cursor.getColumnIndexOrThrow(DESKRIPSI));
            String gambarpopule= cursor.getString(cursor.getColumnIndexOrThrow(GAMBARPOPULE));
            double populer= cursor.getDouble(cursor.getColumnIndexOrThrow(POPULER));
            String originallangue= cursor.getString(cursor.getColumnIndexOrThrow(ORIGINALLANGUAGE));
            int genre= cursor.getInt(cursor.getColumnIndexOrThrow(GENRE));
            String releasedate= cursor.getString(cursor.getColumnIndexOrThrow(RELEASEDATE));
            listMovie.add(new Movie(id, judul, deskripsi, gambarpopule, populer, originallangue, genre, releasedate));
            cursor.moveToNext();
        }
        Faforite= cursor.getCount() >0;
        cursor.close();
        return Faforite;
    }
}
