package com.example.anupama.mvvm.Views.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.anupama.mvvm.Models.UserFeedModel;
import com.example.anupama.mvvm.Network.RetrofitInstance;
import com.example.anupama.mvvm.R;

public class ActivityB extends AppCompatActivity {

    private UserFeedModel mReceivedUserFeedModel;
    private ImageView profileIv ;
    private TextView descriptionTv,fullnameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        init();
        Intent receivedIntent = getIntent();
        mReceivedUserFeedModel = receivedIntent.getParcelableExtra("UserFeed");

        Glide
                .with(this)
                .load(RetrofitInstance.BASE_URL.concat(mReceivedUserFeedModel.getPublisherImage()))
                .into(profileIv) ;

        descriptionTv.setText(mReceivedUserFeedModel.getMomentDescription());
        fullnameTv.setText(mReceivedUserFeedModel.getPublisherFullname());

    }


    void init(){

        profileIv = findViewById(R.id.publisher_profile);
        descriptionTv = findViewById(R.id.publisher_description);
        fullnameTv = findViewById(R.id.publisher_fullname);

    }
}
