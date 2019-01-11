package com.example.anupama.mvvm.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.anupama.mvvm.Models.UserFeedModel;
import com.example.anupama.mvvm.Network.RetrofitInstance;
import com.example.anupama.mvvm.R;

import java.util.ArrayList;

public class UserFeedAdapter extends RecyclerView.Adapter<UserFeedAdapter.UserFeedViewHolder> {


    private ArrayList<UserFeedModel> mUserFeedModelList = new ArrayList<>();
    private Activity context ;

    public UserFeedAdapter(Activity context , ArrayList<UserFeedModel> mUserFeedModelList) {
        this.mUserFeedModelList = mUserFeedModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserFeedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userfeed_single_row,viewGroup,false);
        UserFeedViewHolder userFeedViewHolder = new UserFeedViewHolder(view);
        return userFeedViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserFeedViewHolder userFeedViewHolder, int i) {

        String moment = mUserFeedModelList.get(i).getMomentImage();
        if(!TextUtils.isEmpty(moment) && !"null".equalsIgnoreCase(moment)){
            Glide
                    .with(context)
                    .load(RetrofitInstance.BASE_URL.concat(mUserFeedModelList.get(i).getMomentImage()))
                    .into(userFeedViewHolder.imageView) ;
        }

    }

    @Override
    public int getItemCount() {
        return mUserFeedModelList.size();
    }

    public class UserFeedViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public UserFeedViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.moment);
        }
    }
}
