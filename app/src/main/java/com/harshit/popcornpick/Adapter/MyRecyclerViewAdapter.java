package com.harshit.popcornpick.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.harshit.popcornpick.EntityAndDb.MovieModel;
import com.harshit.popcornpick.R;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private List<MovieModel> list = new ArrayList<>();

    private OnMovieListener onMovieListener;
    private boolean adapter;
    public MyRecyclerViewAdapter(OnMovieListener onMovieListener,boolean adapter){
        this.onMovieListener = onMovieListener;
        this.adapter=adapter;
    }

    public MovieModel getClickedMovie(int position){
        if(list==null)  return null;
        return list.size()>position?list.get(position):null;
    }

    public void setList(List<MovieModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_film,parent,false);
        return new MyViewHolder(view,onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MovieModel model =  list.get(position);
        holder.title.setText(model.getTitle());
        holder.stars.setText(String.valueOf(model.getVoteAverage()));
        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500/"+model.getPosterPath())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;
        private TextView title,stars;

        OnMovieListener onMovieListener;
        public MyViewHolder(@NonNull View itemView,OnMovieListener onMovieListener) {
            super(itemView);
            this.onMovieListener = onMovieListener;
            imageView = itemView.findViewById(R.id.pic);
            title= itemView.findViewById(R.id.titleText);
            stars = itemView.findViewById(R.id.screentext);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onMovieListener.onMovieClick(getAdapterPosition(),adapter);
        }
    }
}
