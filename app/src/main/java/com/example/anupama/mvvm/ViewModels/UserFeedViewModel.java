package com.example.anupama.mvvm.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.anupama.mvvm.Models.UserFeedModel;
import com.example.anupama.mvvm.Network.ApiResponse;
import com.example.anupama.mvvm.Repository.UserFeedRepository;

import java.util.ArrayList;

public class UserFeedViewModel extends ViewModel {

    private MutableLiveData<ArrayList<UserFeedModel>> mUserFeeds ;
    private UserFeedRepository mUserFeedRepository;
    private MutableLiveData<ApiResponse> apiResponseMutableLiveData = new MutableLiveData<>();

    public void init(String page, String perpage, String momenttype, String auth_token){
        if(mUserFeeds!=null){
            return;
        }
        mUserFeedRepository = UserFeedRepository.getInstance();
        mUserFeeds = mUserFeedRepository.getUserFeeds(page,perpage,momenttype,auth_token);
        apiResponseMutableLiveData = mUserFeedRepository.getApiResponse();
    }

    public LiveData<ArrayList<UserFeedModel>> getUserFeeds() {
        return mUserFeeds;

    }

    public LiveData<ApiResponse> getApiResponse() {
        return apiResponseMutableLiveData;
    }


}
