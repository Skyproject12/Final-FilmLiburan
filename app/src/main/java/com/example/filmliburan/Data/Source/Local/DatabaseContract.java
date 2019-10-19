package com.example.filmliburan.Data.Source.Local;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.example.filmliburan";
    public static final String SCHEME = "content";

    private DatabaseContract() {
    }

    public static final class MovieColumn implements BaseColumns {
        public static String TABLE_MOVIE = "movie";
        public static String JUDUL = "judul";
        public static String DESKRIPSI = "deskripsi";
        public static String GAMBARPOPULE = "gambar_populer";
        public static String POPULER = "populer";
        public static String ORIGINALLANGUAGE = "original_language";
        public static String GENRE = "genre";
        public static String RELEASEDATE = "release_date";
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();

    }

    public static final class TvshowColumn implements BaseColumns {
        public static String TABLE_TVSHOW = "tvshow";
        public static String JUDUL = "judul";
        public static String DESKRIPSI = "deskripsi";
        public static String GAMBARPOPULE = "gambar_populer";
        public static String POPULER = "populer";
        public static String ORIGINALLANGUAGE = "original_language";
        public static String GENRE = "genre";
        public static String RELEASEDATE = "release_date";
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_TVSHOW)
                .build();

    }
}