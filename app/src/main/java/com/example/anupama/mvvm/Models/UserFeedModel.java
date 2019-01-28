package com.example.anupama.mvvm.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserFeedModel implements Parcelable {

    private String momentId, momentImage, momentVideo, momentDescription , momentLocation;

    private String publisherUsername , publisherImage, publisherId, publisherFullname ;


    public UserFeedModel(){

    }

    protected UserFeedModel(Parcel in) {
        momentId = in.readString();
        momentImage = in.readString();
        momentVideo = in.readString();
        momentDescription = in.readString();
        momentLocation = in.readString();
        publisherUsername = in.readString();
        publisherImage = in.readString();
        publisherId = in.readString();
        publisherFullname = in.readString();
    }

    public static final Creator<UserFeedModel> CREATOR = new Creator<UserFeedModel>() {
        @Override
        public UserFeedModel createFromParcel(Parcel in) {
            return new UserFeedModel(in);
        }

        @Override
        public UserFeedModel[] newArray(int size) {
            return new UserFeedModel[size];
        }
    };

    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }

    public String getMomentImage() {
        return momentImage;
    }

    public void setMomentImage(String momentImage) {
        this.momentImage = momentImage;
    }

    public String getMomentVideo() {
        return momentVideo;
    }

    public void setMomentVideo(String momentVideo) {
        this.momentVideo = momentVideo;
    }

    public String getMomentDescription() {
        return momentDescription;
    }

    public void setMomentDescription(String momentDescription) {
        this.momentDescription = momentDescription;
    }

    public String getMomentLocation() {
        return momentLocation;
    }

    public void setMomentLocation(String momentLocation) {
        this.momentLocation = momentLocation;
    }

    public String getPublisherUsername() {
        return publisherUsername;
    }

    public void setPublisherUsername(String publisherUsername) {
        this.publisherUsername = publisherUsername;
    }

    public String getPublisherImage() {
        return publisherImage;
    }

    public void setPublisherImage(String publisherImage) {
        this.publisherImage = publisherImage;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherFullname() {
        return publisherFullname;
    }

    public void setPublisherFullname(String publisherFullname) {
        this.publisherFullname = publisherFullname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(momentId);
        parcel.writeString(momentImage);
        parcel.writeString(momentVideo);
        parcel.writeString(momentDescription);
        parcel.writeString(momentLocation);
        parcel.writeString(publisherUsername);
        parcel.writeString(publisherImage);
        parcel.writeString(publisherId);
        parcel.writeString(publisherFullname);
    }
}
