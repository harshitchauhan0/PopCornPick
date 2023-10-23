package com.harshit.popcornpick.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.harshit.popcornpick.Adapter.MyRecyclerViewAdapter;
import com.harshit.popcornpick.Adapter.OnMovieListener;
import com.harshit.popcornpick.EntityAndDb.MovieModel;
import com.harshit.popcornpick.EntityAndDb.MovieModelList;
import com.harshit.popcornpick.R;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements OnMovieListener {
    RecyclerView recyclerView;
    MyRecyclerViewAdapter myRecyclerViewAdapter;
    ImageView backFromSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent i = getIntent();
        List<MovieModel> list = new ArrayList<>();
        if(i.hasExtra("movielist")){
            MovieModelList movieModelList = i.getParcelableExtra("movielist");
            assert movieModelList != null;
            list = movieModelList.getList();
        }
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(this,false);
        myRecyclerViewAdapter.setList(list);
        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.search_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(myRecyclerViewAdapter);
        backFromSearch = findViewById(R.id.backfromsearchimg);
        backFromSearch.setOnClickListener(v->finish());
    }

    @Override
    public void onMovieClick(int position, boolean adapter) {
        Intent i =new Intent(this, DetailActivity.class);
        MovieModel model = myRecyclerViewAdapter.getClickedMovie(position);

        if(model!=null){
            int id = model.getId();
            i.putExtra("movie",id);
            Log.v("TAG","The model is succes");
            startActivity(i);
        }
        else{
            Log.v("TAG","There is problem in intent");
        }
    }
}