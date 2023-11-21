package com.harshit.popcornpick.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.harshit.popcornpick.EntityAndDb.Detail;
import com.harshit.popcornpick.EntityAndDb.FavDB;
import com.harshit.popcornpick.EntityAndDb.FavEntity;
import com.harshit.popcornpick.R;

import java.util.ArrayList;
import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyViewHolder> {

    private Context context;
    private List<Detail> Favouritelist = new ArrayList<>();
    private OnMovieListener onMovieListener;

    public FavouriteAdapter(Context context,List<Detail> Favouritelist,OnMovieListener onMovieListener){
        this.context = context;
        this.Favouritelist = Favouritelist;
        this.onMovieListener = onMovieListener;
    }

    public Detail getClickedMovie(int position){
        if(Favouritelist == null){
            return null;
        }
        return Favouritelist.size()>position?Favouritelist.get(position):null;
    }

    public void setFavouritelist(List<Detail> favouritelist) {
        Favouritelist = favouritelist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_item,parent,false);
        return new MyViewHolder(view, onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Detail detail = Favouritelist.get(position);
        holder.textView.setText(detail.getTitle());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+detail.getPosterPath())
                .into(holder.imageView);
        holder.starsTv.setText(String.valueOf(detail.getVoteAverage()));
        holder.del_imageView.setOnClickListener((v)->{
            Favouritelist.remove(position);
            FavDB.getInstance(context.getApplicationContext()).getFavDao().delete(new FavEntity(detail.getId()));
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return Favouritelist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView,del_imageView;
        private TextView textView,starsTv;

        OnMovieListener onMovieListener;

        public MyViewHolder(@NonNull View itemView,OnMovieListener onMovieListener){
            super(itemView);

            this.onMovieListener = onMovieListener;
            imageView = itemView.findViewById(R.id.favoriteImageView);
            textView = itemView.findViewById(R.id.favoriteMovieName);
            starsTv = itemView.findViewById(R.id.Favouritestar);
            textView.setOnClickListener(this);
            del_imageView = itemView.findViewById(R.id.del_from_fav);
        }

        public void onClick(View v) {
            onMovieListener.onMovieClick(getAdapterPosition(),false);
        }

    }

}
