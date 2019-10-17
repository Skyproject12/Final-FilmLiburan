package com.example.filmliburan.Preview.Main.Fragment.TvShow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmliburan.Data.Model.TvShow;
import com.example.filmliburan.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TvshowAdapter extends RecyclerView.Adapter<TvshowAdapter.ViewHolder> implements Filterable {

    ArrayList<TvShow> list= new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;
    private List<TvShow> listFull;
    public void setItem(ArrayList<TvShow> show){
        list.clear();
        list.addAll(show);
        listFull= new ArrayList<>(show);
        notifyDataSetChanged();
    }
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback= onItemClickCallback;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tvshow_item, viewGroup, false);
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
            List<TvShow> filterList= new ArrayList<>();
            if(constraint==null || constraint.length()==0){
                filterList.addAll(listFull);
            }
            else {
                String filterPattern= constraint.toString().toLowerCase().trim();
                for(TvShow item:listFull){
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
        TextView textPopuler;
        ImageView gambarShow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textJudul= itemView.findViewById(R.id.text_judul);
            textPopuler= itemView.findViewById(R.id.text_populer);
            gambarShow= itemView.findViewById(R.id.gambar_populer);
        }
        public void bind(TvShow tvShow){
            textJudul.setText(tvShow.getJudul());
            textPopuler.setText(String.valueOf(tvShow.getPopuler()));

            Picasso.get().load(tvShow.getGambar()).into(gambarShow);
        }
    }
    public interface OnItemClickCallback{
        void onItmCliked(TvShow tvShow);
    }
}
