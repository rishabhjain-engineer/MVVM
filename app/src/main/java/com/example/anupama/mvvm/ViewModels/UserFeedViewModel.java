package com.example.anupama.mvvm.ViewModels;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.anupama.mvvm.DataSource.DataSourceFactory;
import com.example.anupama.mvvm.Models.UserFeedModel;
import com.example.anupama.mvvm.Network.ApiResponse;
import com.example.anupama.mvvm.Repository.UserFeedRepository;

import java.util.ArrayList;

public class UserFeedViewModel extends ViewModel {


    private DataSourceFactory newsDataSourceFactory;
    private LiveData<PagedList<UserFeedModel>> listLiveData;
    private MutableLiveData<UserFeedRepository> re = new MutableLiveData<>();


    public UserFeedViewModel() {

        newsDataSourceFactory = new DataSourceFactory();
        initializePaging();

    }

    private void initializePaging() {

        PagedList.Config pagedListConfig =
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(true)
                        .setPageSize(UserFeedRepository.PAGE_SIZE).build();

        listLiveData = new LivePagedListBuilder<>(newsDataSourceFactory, pagedListConfig)
                .build();



        re = newsDataSourceFactory.getMutableLiveData();


    }


    public LiveData<PagedList<UserFeedModel>> getListLiveData() {
        return listLiveData;
    }

    public MutableLiveData<UserFeedRepository> getApiResponseLiveData() {

        return re;
    }

}
