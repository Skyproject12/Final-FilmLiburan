package com.example.filmliburan.Preview.Detail;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.filmliburan.Data.Model.Movie;
import com.example.filmliburan.Data.Model.TvShow;
import com.example.filmliburan.Data.Source.Local.FavoriteHelper;
import com.example.filmliburan.Preview.Main.MainActivity;
import com.example.filmliburan.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.TvshowColumn.CONTENT_URI;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.TvshowColumn.DESKRIPSI;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.TvshowColumn.GAMBARPOPULE;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.TvshowColumn.GENRE;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.TvshowColumn.JUDUL;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.TvshowColumn.ORIGINALLANGUAGE;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.TvshowColumn.POPULER;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.TvshowColumn.RELEASEDATE;

public class DetailTvShowActivity extends AppCompatActivity {

    TextView textJudul;
    TextView textPopuler;
    ImageView gambarPopuler;
    TvShow tvShow;
    TextView textDeskripsi;
    TextView textOriginalLanguage;
    TextView textGenre;
    TextView textReleaseDate;
    Menu menu;
    Uri currentUri;
    ArrayList<TvShow> listTvshow;
    FavoriteHelper favoriteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_tv_show);
        favoriteHelper= FavoriteHelper.getInstace(getApplicationContext());
        favoriteHelper.open();
        textJudul= findViewById(R.id.text_judul);
        textPopuler= findViewById(R.id.text_populer);
        gambarPopuler= findViewById(R.id.gambar_populer);
        tvShow= getIntent().getParcelableExtra("intent");
        textJudul.setText(tvShow.getJudul());
        textPopuler.setText(String.valueOf(tvShow.getPopuler()));
        Picasso.get().load(tvShow.getGambar()).into(gambarPopuler);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        textDeskripsi= findViewById(R.id.text_deskripsi);
        textOriginalLanguage= findViewById(R.id.text_language);
        textGenre= findViewById(R.id.text_genre);
        textReleaseDate= findViewById(R.id.text_date);
        textGenre.setText(String.valueOf(tvShow.getGenre()));
        textDeskripsi.setText(tvShow.getDeskripsi());
        textOriginalLanguage.setText(tvShow.getOriginalLanguage());
        textReleaseDate.setText(tvShow.getReleaseDate());
        Intent intent= getIntent();
        currentUri=intent.getData();

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu=menu;
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        MenuItem menuItem= menu.findItem(R.id.favorite_detail);
        if(getFilm(tvShow.getId())){
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_star));
        }else if(!getFilm(tvShow.getId())){
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_starko));
        }
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        MenuItem menuItem = menu.findItem(R.id.favorite_detail);
        switch (item.getItemId()) {
            case R.id.favorite_detail:
                getFilm(tvShow.getId());
                if (getFilm(tvShow.getId())) {
                    Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
                    menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_starko));
                    getContentResolver().delete(currentUri,null,null);
                } else if (!getFilm(tvShow.getId())) {
                    Toast.makeText(this, "masuk", Toast.LENGTH_SHORT).show();
                    menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_star));
                    ContentValues values= new ContentValues();
                    values.put(_ID ,tvShow.getId());
                    values.put(JUDUL,tvShow.getJudul());
                    values.put(GAMBARPOPULE, tvShow.getGambar());
                    values.put(POPULER, tvShow.getPopuler());
                    values.put(DESKRIPSI, tvShow.getDeskripsi());
                    values.put(ORIGINALLANGUAGE, tvShow.getOriginalLanguage());
                    values.put(GENRE, tvShow.getGenre());
                    values.put(RELEASEDATE, tvShow.getReleaseDate());
                    Toast.makeText(this, "comtent"+CONTENT_URI, Toast.LENGTH_SHORT).show();
                    Uri masuk= getContentResolver().insert(CONTENT_URI, values);
                }
                break;
            case R.id.home:
                Intent intent = new Intent(DetailTvShowActivity.this, MainActivity.class);
                startActivity(intent);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public boolean getFilm(int position){
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
        listTvshow= new ArrayList<>();
        for (int i = 0; i <cursor.getCount() ; i++) {
            int id= cursor.getInt(cursor.getColumnIndexOrThrow(_ID)) ;
            String judul= cursor.getString(cursor.getColumnIndexOrThrow(JUDUL));
            String deskripsi= cursor.getString(cursor.getColumnIndexOrThrow(DESKRIPSI));
            String gambarpopule= cursor.getString(cursor.getColumnIndexOrThrow(GAMBARPOPULE));
            double populer= cursor.getDouble(cursor.getColumnIndexOrThrow(POPULER));
            String originallangue= cursor.getString(cursor.getColumnIndexOrThrow(ORIGINALLANGUAGE));
            int genre= cursor.getInt(cursor.getColumnIndexOrThrow(GENRE));
            String releasedate= cursor.getString(cursor.getColumnIndexOrThrow(RELEASEDATE));
            listTvshow.add(new TvShow(id, judul, gambarpopule, populer,deskripsi, originallangue, genre, releasedate));
            cursor.moveToNext();
        }
        Faforite= cursor.getCount() >0;
        Toast.makeText(this, "count"+cursor.getCount(), Toast.LENGTH_SHORT).show();
        cursor.close();
        return Faforite;
    }
}
