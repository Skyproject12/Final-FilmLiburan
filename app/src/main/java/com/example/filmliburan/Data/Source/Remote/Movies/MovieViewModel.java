package com.example.filmliburan.Data.Source.Remote.Movies;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
    String API_KEY="412327d8b23a411e90711834b24fe08e";

    public void setMovie(){
        AsyncHttpClient client= new AsyncHttpClient();
        final ArrayList<Movie> listItem= new ArrayList<>();
        String Url= "https://api.themoviedb.org/3/discover/movie?api_key="+ API_KEY  + "&language=en-US";
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

    public void searchHeader(String search){
        final ArrayList<Movie> listSearch= new ArrayList<>();
        AsyncHttpClient client= new AsyncHttpClient();
        String Url="https://api.themoviedb.org/3/search/movie?api_key="+ API_KEY + "&language=en-US&query=" + search;
        client.get(Url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result= new String(responseBody);
                try {
                    JSONObject responseObject= new JSONObject(result);
                    JSONArray list= responseObject.getJSONArray("results");
                    for (int i = 0; i <list.length() ; i++) {
                        JSONObject movie= list.getJSONObject(i);
                        Movie moviefilm= new Movie(movie);
                        listSearch.add(moviefilm);
                    }
                    listMovie.postValue(listSearch);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public LiveData<ArrayList<Movie>> getMovie() {
        return listMovie;
    }
}
