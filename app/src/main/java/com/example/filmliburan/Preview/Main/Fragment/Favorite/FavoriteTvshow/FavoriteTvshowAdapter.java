package com.example.filmliburan.Preview.Main.Fragment.Favorite.FavoriteTvshow;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmliburan.Data.Model.Movie;
import com.example.filmliburan.Data.Model.TvShow;
import com.example.filmliburan.Preview.Main.Fragment.Favorite.FavoriteMovie.FavoriteMovieAdapter;
import com.example.filmliburan.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteTvshowAdapter extends RecyclerView.Adapter<FavoriteTvshowAdapter.FavoriteHolder> {
    ArrayList<TvShow> list= new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;
    private Activity activity;
    public FavoriteTvshowAdapter(Activity activity){
        this.activity= activity;
    }
    public ArrayList<TvShow> getListMovie(){
        return list;
    }
    public void setListTvShow(ArrayList<TvShow> listTvShow){
        if(listTvShow.size()>0){
            this.list.clear();
        }
        this.list.addAll(listTvShow);
        notifyDataSetChanged();
    }
    public void addItem(TvShow tvShow){
        list.add(tvShow);
        notifyItemInserted(list.size()-1);
    }
    public void updateItem(int position, TvShow tvShow){
        this.list.set(position, tvShow);
        notifyItemChanged(position, tvShow);
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
    public void onBindViewHolder(@NonNull final FavoriteHolder holder, int position) {
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
        void onItmCliked(TvShow tvShow);
    }
}
