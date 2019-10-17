package com.example.filmliburan.Data.Source.Remote.Movies;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.filmliburan.Data.Model.Movie;
import com.example.filmliburan.Util.Static;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieViewModel extends ViewModel {
    public MutableLiveData<ArrayList<Movie>> listMovie= new MutableLiveData<>() ;
    public ArrayList<Movie> listItem= new ArrayList<>();

    public void setMovie(){
        AsyncHttpClient client= new AsyncHttpClient();
        Static listSame= new Static();
        String Url= "https://api.themoviedb.org/3/discover/movie?api_key="+ listSame.API_KEY  + "&language=en-US";
        client.get(Url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result= new String(responseBody);
                try {
                    JSONObject responseJson= new JSONObject(result);
                    JSONArray list= responseJson.getJSONArray("results");
                    for(int i=0; i<list.length(); i++){
                        JSONObject movie= list.getJSONObject(i);
                        Movie moviefilm= new Movie(movie);
                        listItem.add(moviefilm);
                        Log.d("ViewModel", moviefilm.getDeskripsi());
                    }
                    listMovie.postValue(listItem);
                } catch (JSONException e) {
                    Log.d("Exception", e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Failure", error.getMessage());
            }
        });

    }

    public LiveData<ArrayList<Movie>> getMovie() {
        return listMovie;
    }
}
