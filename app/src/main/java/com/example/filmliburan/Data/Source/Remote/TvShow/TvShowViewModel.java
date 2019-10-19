package com.example.filmliburan.Data.Source.Remote.TvShow;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.filmliburan.Data.Model.TvShow;
import com.example.filmliburan.Util.Static;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvShowViewModel extends ViewModel {
    public MutableLiveData<ArrayList<TvShow>> listShow= new MutableLiveData<>();
    public void setTvshow(){
        final ArrayList<TvShow> listItem= new ArrayList<>();
        String KEY_NAME="412327d8b23a411e90711834b24fe08e";
        AsyncHttpClient client= new AsyncHttpClient();
        String Url= "https://api.themoviedb.org/3/discover/tv?api_key=" + KEY_NAME + "&language=en-US";

        client.get(Url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result= new String(responseBody);
                JSONObject responseJson= null;
                try {
                    responseJson = new JSONObject(result);
                    JSONArray list = responseJson.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tvshow = list.getJSONObject(i);
                        TvShow show = new TvShow(tvshow);
                        listItem.add(show);
                        Log.d("Success", show.getJudul());
                    }
                    listShow.postValue(listItem);
                }
                catch(JSONException e){
                    e.printStackTrace();
                    Log.d("Exceptions", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Failure", error.getMessage());
            }
        });
    }
    public void searchJoker(String joker){
        final ArrayList<TvShow> listJoker= new ArrayList<>();
        String KEY_NAME="412327d8b23a411e90711834b24fe08e";
        AsyncHttpClient client= new AsyncHttpClient();
        String Url="https://api.themoviedb.org/3/search/tv?api_key="+ KEY_NAME + "&language=en-US&query=" + joker;
        client.get(Url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result= new String(responseBody);
                JSONObject responseJson= null;
                try {
                    responseJson= new JSONObject(result);
                    JSONArray list= responseJson.getJSONArray("results");
                    for (int i = 0; i <list.length() ; i++) {
                        JSONObject tvshow= list.getJSONObject(i);
                        TvShow show= new TvShow(tvshow);
                        listJoker.add(show);
                    }
                    listShow.postValue(listJoker);
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

    public LiveData<ArrayList<TvShow>> getTvshow() {
        return listShow;
    }
}
