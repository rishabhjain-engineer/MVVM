package com.example.anupama.mvvm.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.anupama.mvvm.Models.UserFeedModel;
import com.example.anupama.mvvm.Repository.UserFeedRepository;

import java.util.ArrayList;

public class UserFeedViewModel extends ViewModel {

    private MutableLiveData<ArrayList<UserFeedModel>> mUserFeeds ;
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private UserFeedRepository mUserFeedRepository;

    public void init(){
        if(mUserFeeds!=null){
            return;
        }
       // loading.setValue(true);
        mUserFeedRepository = UserFeedRepository.getInstance();
        mUserFeeds = mUserFeedRepository.getUserFeeds();
    }

    public LiveData<ArrayList<UserFeedModel>> getUserFeeds() {
        return mUserFeeds;

    }


    public LiveData<Boolean> getLoading() {
        return loading;
    }
}
