package com.example.anupama.mvvm.Models;

public class UserFeedModel {

    private String momentId, momentImage, momentVideo, momentDescription , momentLocation;

    private String publisherUsername , publisherImage, publisherId, publisherFullname ;


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
}
