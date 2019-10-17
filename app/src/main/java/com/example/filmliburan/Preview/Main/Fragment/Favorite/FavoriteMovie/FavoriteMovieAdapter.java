package com.example.filmliburan.Preview.Main.Fragment.Favorite.FavoriteMovie;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmliburan.Data.Model.Movie;
import com.example.filmliburan.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteHolder> {
    ArrayList<Movie> list= new ArrayList<>();
    private Activity activity;
    private OnItemClickCallback onItemClickCallback;


    public FavoriteMovieAdapter(Activity activity) {
        this.activity= activity;
    }

    public ArrayList<Movie> getListMovie(){
        return list;
    }
    public void setListMovie(ArrayList<Movie> listMovie){
        if(listMovie.size()>0){
            this.list.clear();
        }
        this.list.addAll(listMovie);
        notifyDataSetChanged();
    }
    public void addItem(Movie movie){
        list.add(movie);
        notifyItemInserted(list.size()-1);
    }
    public void updateItem(int position, Movie movie){
        this.list.set(position, movie);
        notifyItemChanged(position, movie);
    }
    public void removeItem(int position){
        this.list.remove(position);
        notifyItemRangeChanged(position, list.size());
        notifyItemRemoved(position);
    }
    public void setOnItemCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback= onItemClickCallback;
    }
    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_item, parent, false);
        return new FavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteHolder holder, final int position) {
        holder.textJudul.setText(getListMovie().get(position).getJudul());
        holder.textDekripsi.setText(getListMovie().get(position).getDeskripsi());
        Picasso.get().load(getListMovie().get(position).getGambar()).into(holder.imageMovie);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItmCliked(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FavoriteHolder extends RecyclerView.ViewHolder {
        TextView textJudul;
        TextView textDekripsi;
        ImageView imageMovie;
        public FavoriteHolder(@NonNull View itemView) {
            super(itemView);
            textJudul= itemView.findViewById(R.id.text_judul);
            textDekripsi= itemView.findViewById(R.id.text_deskripsi);
            imageMovie= itemView.findViewById(R.id.gambar_movie);
        }
    }
    public interface OnItemClickCallback{
        void onItmCliked(Movie movie);
    }
}
