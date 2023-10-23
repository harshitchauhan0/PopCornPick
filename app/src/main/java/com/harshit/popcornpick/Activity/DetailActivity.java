package com.harshit.popcornpick.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.harshit.popcornpick.EntityAndDb.Crediantials;
import com.harshit.popcornpick.EntityAndDb.Detail;
import com.harshit.popcornpick.EntityAndDb.FavDB;
import com.harshit.popcornpick.EntityAndDb.FavEntity;
import com.harshit.popcornpick.EntityAndDb.Genre;
import com.harshit.popcornpick.Helper.SingeltonRetrofit;
import com.harshit.popcornpick.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private NestedScrollView scrollView;
    private ProgressBar progressBar;
    private TextView titletext,movieRatetext,movieTimetext,movieDatetext,movieSummaryInfo,movieActorInfo,movieHomepageLink;
    private ImageView pic2,backImg,favoriteIMG;
    private ShapeableImageView pic1;
    private int movieID;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        Intent i = getIntent();
        progressBar.setVisibility(View.VISIBLE);
        if(i.hasExtra("movie")){
            movieID = i.getIntExtra("movie",0);
        }
        fetchMovie();
        favoriteIMG.setOnClickListener(v->{
            // ADD FAVORITE FROM HERE
            FavDB.getInstance(getApplicationContext()).getFavDao().insert(new FavEntity(movieID));
        });


    }

    private void fetchMovie() {
        Call<Detail> call = SingeltonRetrofit.getMovieApi().getMovieById(movieID, Crediantials.API_KEY);
        call.enqueue(new Callback<Detail>() {
            @Override
            public void onResponse(Call<Detail> call, Response<Detail> response) {
                if(response.isSuccessful()){
                    Detail detail = response.body();
                    progressBar.setVisibility(View.GONE);
                    if(detail!=null){
                        Glide.with(getApplicationContext()).load(Crediantials.MOVIE_BASE_URL+detail.getPosterPath())
                                .into(pic1);
                        Glide.with(getApplicationContext())
                                .load(Crediantials.MOVIE_BASE_URL+detail.getBackdropPath()).into(pic2);
                        titletext.setText(detail.getOriginalTitle());
                        movieRatetext.setText(String.format("%.2f",detail.getVoteAverage()));
                        movieTimetext.setText(""+detail.getRuntime());
                        movieDatetext.setText(detail.getReleaseDate());
                        movieHomepageLink.setText(detail.getHomepage());
                        movieSummaryInfo.setText(detail.getOverview());
                        List<Genre> list = detail.getGenres();
                        StringBuilder s = new StringBuilder();
                        for(Genre genre:list){
                            s.append(genre.getName());
                            s.append(",");
                        }
                        s.deleteCharAt(s.length()-1);
                        movieActorInfo.setText(s.toString());
                    }
                    else{
                        Log.v("TAG","detail is null");
                    }
                }
                else{
                    Log.v("TAG","This is the problem from response "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Detail> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.v("TAG","This is the problem from failure "+t.getMessage());
            }
        });

        movieHomepageLink.setOnClickListener(v->{
            String url = movieHomepageLink.getText().toString();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
    }

    public void initView(){
        scrollView = findViewById(R.id.scrollView3);
        titletext = findViewById(R.id.movieNametext);
        progressBar = findViewById(R.id.detailLoading);
        pic1 = findViewById(R.id.posterNormalimg);
        pic2 = findViewById(R.id.posterBigImg);
        movieRatetext = findViewById(R.id.movieRatintext);
        movieSummaryInfo = findViewById(R.id.movieSummaryText);
        movieDatetext = findViewById(R.id.movieDatetxt);
        movieTimetext = findViewById(R.id.movieTimetxt);
        movieActorInfo = findViewById(R.id.movieActionInfo);
        backImg = findViewById(R.id.backimg);
        movieHomepageLink = findViewById(R.id.movieHomepageLink);
        recyclerView = findViewById(R.id.imageRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        backImg.setOnClickListener(v->finish());
        favoriteIMG  = findViewById(R.id.imageView9);

    }


}