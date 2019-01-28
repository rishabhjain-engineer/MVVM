package com.example.anupama.mvvm.Repository;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.anupama.mvvm.Models.UserFeedModel;
import com.example.anupama.mvvm.Network.ApiResponse;
import com.example.anupama.mvvm.Network.ApiServices;
import com.example.anupama.mvvm.Network.RetrofitInstance;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFeedRepository extends PageKeyedDataSource<Integer,UserFeedModel>{

    private static UserFeedRepository instance;

    private MutableLiveData<ApiResponse> apiResponseMutableLiveData = new MutableLiveData<>();
    public static final int PAGE_SIZE = 20;
    private static final int FIRST_PAGE = 1;
    private final String AUTH_TOKEN = "kbvNISE2swVMxWj29EnZhg";


    public static UserFeedRepository getInstance() {

        if (instance == null) {
            instance = new UserFeedRepository();
        }
        return instance;
    }


    private ArrayList<UserFeedModel> parseFeedResult(JSONObject jsonObject) {

        ArrayList<UserFeedModel> dataset = new ArrayList<>();

        try {
            String moments = jsonObject.optString("moments");
            JSONArray jsonArray = new JSONArray(moments);
            if (jsonArray.length() > 0) {

                for (int i = 0; i < jsonArray.length(); i++) {

                    UserFeedModel userFeedModel = new UserFeedModel();
                    JSONObject innerJsonObject = jsonArray.optJSONObject(i);


                    String momentId = innerJsonObject.optString("id");
                    String description = innerJsonObject.optString("description");
                    String location = innerJsonObject.optString("location");
                    String sportId = innerJsonObject.optString("sport_id");
                    String commentCount = innerJsonObject.optString("comment_count");
                    String likeCount = innerJsonObject.optString("like_count");
                    String viewCount = innerJsonObject.optString("view_count");

                    /*String momentVideo = innerJsonObject.optString("video");
                    if(!TextUtils.isEmpty(momentVideo)){
                        String momentVideoUrl = new JSONObject(momentVideo).optString("url");
                    }*/

                    String momentImage = innerJsonObject.optString("image");
                    if (!TextUtils.isEmpty(momentImage)) {
                        String momentImageUrl = new JSONObject(momentImage).optString("url");
                        userFeedModel.setMomentImage(momentImageUrl);
                    }

                    String publisherInfo = innerJsonObject.optString("user_info");
                    JSONObject publisherInfoObject = new JSONObject(publisherInfo);
                    String publisherUsername = publisherInfoObject.optString("username");
                    String publisherUserId = publisherInfoObject.optString("id");
                    String publisherUserFullname = publisherInfoObject.optString("full_name");
                    String publisherImage = publisherInfoObject.optJSONObject("imag").optString("url");

                    userFeedModel.setMomentId(momentId);
                    userFeedModel.setMomentDescription(description);

                    //userFeedModel.setMomentVideo(momentVideo);
                    userFeedModel.setMomentLocation(location);
                    userFeedModel.setPublisherId(publisherUserId);
                    userFeedModel.setPublisherUsername(publisherUsername);
                    userFeedModel.setPublisherImage(publisherImage);
                    userFeedModel.setPublisherFullname(publisherUserFullname);

                    dataset.add(userFeedModel);
                    apiResponseMutableLiveData.postValue(ApiResponse.success(null));
                }
            }else {
                Log.e("Rishabh","List ended");
            }


        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Rishabh", "exception: " + e.toString());
            apiResponseMutableLiveData.postValue(ApiResponse.error(new Throwable(e)));
        }

       // Log.e("Rishabh","Dataset size: "+dataset.size());
        return dataset;

    }



    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, UserFeedModel> callback) {
        apiResponseMutableLiveData.postValue(ApiResponse.loading());
        ApiServices apiServices = RetrofitInstance.getRetrofitInstance().create(ApiServices.class);
        Call<JsonElement> call = apiServices.getMoments(Integer.toString(FIRST_PAGE), Integer.toString(PAGE_SIZE), "", AUTH_TOKEN);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {


                String d = response.body().toString() ;
                try {
                    JSONObject jsonObject = new JSONObject(d);
                    if(jsonObject.optBoolean("status")){
                        callback.onResult(parseFeedResult(jsonObject),null,FIRST_PAGE+1);
                    }else {
                        apiResponseMutableLiveData.postValue(ApiResponse.error(new Throwable("Status is false")));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    apiResponseMutableLiveData.postValue(ApiResponse.error(new Throwable(e)));
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                apiResponseMutableLiveData.postValue(ApiResponse.error(t));
            }
        });


    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, UserFeedModel> callback) {
        apiResponseMutableLiveData.postValue(ApiResponse.loading());
        ApiServices apiServices = RetrofitInstance.getRetrofitInstance().create(ApiServices.class);

        Call<JsonElement> call = apiServices.getMoments(Integer.toString(params.key), Integer.toString(PAGE_SIZE), "", AUTH_TOKEN);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {


                Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                String d = response.body().toString() ;
                try {
                    JSONObject jsonObject = new JSONObject(d);
                    if(jsonObject.optBoolean("status")){
                        callback.onResult(parseFeedResult(jsonObject),adjacentKey);
                    }else {
                        apiResponseMutableLiveData.postValue(ApiResponse.error(new Throwable("Status is false")));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    apiResponseMutableLiveData.postValue(ApiResponse.error(new Throwable(e)));
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                apiResponseMutableLiveData.postValue(ApiResponse.error(t));
            }
        });


    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, UserFeedModel> callback) {
        //apiResponseMutableLiveData.postValue(ApiResponse.loading());
        ApiServices apiServices = RetrofitInstance.getRetrofitInstance().create(ApiServices.class);
        Call<JsonElement> call = apiServices.getMoments(Integer.toString(params.key), Integer.toString(PAGE_SIZE), "", AUTH_TOKEN);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                String d = response.body().toString() ;
                try {
                    JSONObject jsonObject = new JSONObject(d);
                    if(jsonObject.optBoolean("status") && parseFeedResult(jsonObject).size()>0){
                        Integer key = parseFeedResult(jsonObject).size()>0?params.key + 1 : null ;
                        callback.onResult(parseFeedResult(jsonObject),key);
                    }else {
                        //Log.e("Rishabh","Load After: Key: "+key);
                        apiResponseMutableLiveData.postValue(ApiResponse.error(new Throwable("Status is false")));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                   // apiResponseMutableLiveData.postValue(ApiResponse.error(new Throwable(e)));
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                apiResponseMutableLiveData.postValue(ApiResponse.error(t));
            }
        });


    }

    public MutableLiveData<ApiResponse> getApiResponse(){
        return apiResponseMutableLiveData ;
    }
}
