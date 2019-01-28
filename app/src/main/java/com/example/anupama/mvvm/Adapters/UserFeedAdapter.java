package com.example.anupama.mvvm.Adapters;

import android.app.Activity;
import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.anupama.mvvm.Models.UserFeedModel;
import com.example.anupama.mvvm.Network.RetrofitInstance;
import com.example.anupama.mvvm.R;

import java.util.ArrayList;

public class UserFeedAdapter extends PagedListAdapter<UserFeedModel,UserFeedAdapter.UserFeedViewHolder> {


    private Activity context ;
    private OnItemClickListener listener ;

    public UserFeedAdapter(Activity context, OnItemClickListener listener) {
        super(new DiffUtil.ItemCallback<UserFeedModel>() {
            @Override
            public boolean areItemsTheSame(@NonNull UserFeedModel userFeedModel, @NonNull UserFeedModel t1) {
                Log.e("Rishabh","are Items The same");
                return userFeedModel.getMomentId().equalsIgnoreCase(t1.getMomentId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull UserFeedModel userFeedModel, @NonNull UserFeedModel t1) {
                Log.e("Rishabh","are Contents The same");
                return userFeedModel.equals(t1);
            }
        });
        this.context = context;
        this.listener = listener ;
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

        final UserFeedModel userFeedModel = getItem(i);


        if(userFeedModel != null){
            Glide
                    .with(context)
                    .load(RetrofitInstance.BASE_URL.concat(userFeedModel.getPublisherImage()))
                    .into(userFeedViewHolder.profileIv) ;

            userFeedViewHolder.fullnameTv.setText(userFeedModel.getPublisherFullname());
            userFeedViewHolder.descriptionTv.setText(userFeedModel.getMomentDescription());

            userFeedViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("Rishabh","On View clicked");
                    listener.itemClicked(userFeedModel);
                }
            });

        }else {
            Log.e("Rishabh","Adapter model null");
        }

    }



    public class UserFeedViewHolder extends RecyclerView.ViewHolder{

        ImageView momentIv, profileIv;
        TextView descriptionTv, fullnameTv ;

        public UserFeedViewHolder(@NonNull View itemView) {
            super(itemView);
            momentIv = itemView.findViewById(R.id.moment);
            profileIv = itemView.findViewById(R.id.publisher_profile);
            descriptionTv = itemView.findViewById(R.id.publisher_description);
            fullnameTv = itemView.findViewById(R.id.publisher_fullname);
        }
    }

    private static DiffUtil.ItemCallback<UserFeedModel> DIFF_CALLBACKK =
            new DiffUtil.ItemCallback<UserFeedModel>() {
                @Override
                public boolean areItemsTheSame(UserFeedModel oldItem, UserFeedModel newItem) {
                    Log.e("Rishabh","are Items The same");
                    return oldItem.getMomentId().equalsIgnoreCase(newItem.getMomentId());
                }

                @Override
                public boolean areContentsTheSame(UserFeedModel oldItem, UserFeedModel newItem) {
                    Log.e("Rishabh","are Contents The same");
                    return oldItem.equals(newItem);
                }
            };


    public interface OnItemClickListener {
        void itemClicked(UserFeedModel userFeedModel);
    }
}
