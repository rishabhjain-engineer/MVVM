package com.example.anupama.mvvm.DataSource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.example.anupama.mvvm.Models.UserFeedModel;
import com.example.anupama.mvvm.Repository.UserFeedRepository;

public class DataSourceFactory extends DataSource.Factory<Integer, UserFeedModel>{

    private MutableLiveData<UserFeedRepository> liveData;

    public DataSourceFactory() {
        liveData = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, UserFeedModel> create() {
        UserFeedRepository mUserFeedRepository = UserFeedRepository.getInstance();
        liveData.postValue(mUserFeedRepository);
        return mUserFeedRepository;
    }

    public MutableLiveData<UserFeedRepository> getMutableLiveData() {
        return liveData;
    }


}
