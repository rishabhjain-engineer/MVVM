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
import android.widget.Toast;

import com.example.anupama.mvvm.Adapters.UserFeedAdapter;
import com.example.anupama.mvvm.Models.UserFeedModel;
import com.example.anupama.mvvm.Network.ApiResponse;
import com.example.anupama.mvvm.R;
import com.example.anupama.mvvm.ViewModels.UserFeedViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {


    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager ;
    private UserFeedAdapter mUserFeedAdapter;
    private UserFeedViewModel mUserFeedViewModel;
    private final String AUTH_TOKEN = "kbvNISE2swVMxWj29EnZhg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("Rishabh","ON CREATE");

        mUserFeedViewModel = ViewModelProviders.of(this).get(UserFeedViewModel.class);

        mUserFeedViewModel.init("1","20","",AUTH_TOKEN);
        mUserFeedViewModel.getUserFeeds().observe(this, new Observer<ArrayList<UserFeedModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<UserFeedModel> userFeedModels) {
               mUserFeedAdapter.notifyDataSetChanged();
            }
        });

        mUserFeedViewModel.getApiResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                Log.e("Rishabh","api response on change") ;
                consumeResponse(apiResponse);
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

    /*
     * method to handle response
     * */
    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                showProgressBar();
                break;

            case SUCCESS:
                hideProgessBar();
                break;

            case ERROR:
                hideProgessBar();
                Toast.makeText(MainActivity.this,apiResponse.error.getMessage(), Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }


}
