package com.example.firebasevideotest.Interface;

import com.example.firebasevideotest.Model.MediaObject;

import java.util.ArrayList;


public interface IVideoLoadListener {
    void onVideoLoadSuccess(ArrayList<MediaObject> videoList);
    void onVideoLoadFailed(String message);
}
