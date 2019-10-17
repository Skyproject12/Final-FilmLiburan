package com.example.filmliburan.Preview.Main.Fragment.Movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmliburan.Data.Model.Movie;
import com.example.filmliburan.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> implements Filterable {
    public ArrayList<Movie> list= new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;
    private List<Movie> listFull;
    public void setItems(ArrayList<Movie> item){
        list.clear();
        list.addAll(item);
        listFull= new ArrayList<>(item);
        notifyDataSetChanged();
    }
    public void setOnItemCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback= onItemClickCallback;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movies_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.bind(list.get(i));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItmCliked(list.get(viewHolder.getAdapterPosition()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Movie> filterList= new ArrayList<>();
            if(constraint==null || constraint.length()==0){
                filterList.addAll(listFull);
            }
            else{
                String filterPattern= constraint.toString().toLowerCase().trim();
                for (Movie item:listFull){
                    if(item.getJudul().toLowerCase().contains(filterPattern)){
                        filterList.add(item);
                    }
                }
            }
            FilterResults result= new FilterResults();
            result.values= filterList;
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textJudul;
        TextView textDesskripsi;
        ImageView gambarMovie;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textJudul= itemView.findViewById(R.id.text_judul);
            textDesskripsi= itemView.findViewById(R.id.text_deskripsi);
            gambarMovie= itemView.findViewById(R.id.gambar_movie);
        }
        public void bind(Movie movie){
            textJudul.setText(movie.getJudul());
            textDesskripsi.setText(movie.getDeskripsi());
            Picasso.get().load(movie.getGambar()).into(gambarMovie);
        }
    }
    public interface OnItemClickCallback{
        void onItmCliked(Movie movie);
    }
}
