package com.example.anupama.mvvm.Repository;

import android.arch.lifecycle.MutableLiveData;
import android.text.TextUtils;
import android.util.Log;

import com.example.anupama.mvvm.Models.UserFeedModel;
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

public class UserFeedRepository {

    private static UserFeedRepository instance;
    private ArrayList<UserFeedModel> dataset = new ArrayList<>();
    private final String AUTH_TOKEN = "kbvNISE2swVMxWj29EnZhg";

    public static UserFeedRepository getInstance() {

        if (instance == null) {
            instance = new UserFeedRepository();
        }
        return instance;
    }

    public MutableLiveData<ArrayList<UserFeedModel>> getUserFeeds(){


        final MutableLiveData<ArrayList<UserFeedModel>> data = new MutableLiveData<>();
        data.setValue(dataset);

        ApiServices apiServices = RetrofitInstance.getRetrofitInstance().create(ApiServices.class);
        Call<JsonElement> call = apiServices.getMoments("1", "20", "", AUTH_TOKEN);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                String d = response.body().toString() ;
                try {
                    JSONObject jsonObject = new JSONObject(d);
                    if(jsonObject.optBoolean("status")){
                        data.setValue(parseFeedResult(jsonObject));
                    }else {
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

        return data ;
    }

    private ArrayList<UserFeedModel> parseFeedResult(JSONObject jsonObject) {

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

                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Rishabh", "exception: " + e.toString());
        }

        return dataset;

    }

}
