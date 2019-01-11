package com.example.anupama.mvvm.Views.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.anupama.mvvm.Adapters.UserFeedAdapter;
import com.example.anupama.mvvm.Models.UserFeedModel;
import com.example.anupama.mvvm.R;
import com.example.anupama.mvvm.ViewModels.UserFeedViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {


    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager ;
    private UserFeedAdapter mUserFeedAdapter;
    private UserFeedViewModel mUserFeedViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mUserFeedViewModel = ViewModelProviders.of(this).get(UserFeedViewModel.class);

        mUserFeedViewModel.init();
        mUserFeedViewModel.getUserFeeds().observe(this, new Observer<ArrayList<UserFeedModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<UserFeedModel> userFeedModels) {
               mUserFeedAdapter.notifyDataSetChanged();
            }
        });

        mUserFeedViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean)
                    showProgressBar();
                else
                    hideProgessBar();
            }
        });

        initComponent();

    }


    private void initComponent(){

        mRecyclerView = findViewById(R.id.mainfeed_rv);
        mProgressBar = findViewById(R.id.mainfeed_pb);

        mUserFeedAdapter = new UserFeedAdapter(this,mUserFeedViewModel.getUserFeeds().getValue());
        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mUserFeedAdapter);
    }


    private void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgessBar(){
        mProgressBar.setVisibility(View.GONE);
    }


}
