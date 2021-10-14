package com.example.firebasevideotest.Model;

public class VideoModel {
    private String name, thumbnail, mediaurl;

    public VideoModel(){
    }

    public VideoModel(String name, String thumbnail, String mediaurl){
        this.name = name;
        this.mediaurl = mediaurl;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getMediaurl() {
        return mediaurl;
    }

    public void setMediaurl(String mediaurl) {
        this.mediaurl = mediaurl;
    }
}
