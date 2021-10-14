package com.example.firebasevideotest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.firebasevideotest.Interface.IVideoLoadListener;
import com.example.firebasevideotest.Model.VideoModel;
import com.example.firebasevideotest.Model.MediaObject;
import com.example.firebasevideotest.Util.VerticalSpacingItemDecorator;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements IVideoLoadListener {
//
//    @BindView(R.id.shimmer_frame_layout)
//    ShimmerFrameLayout mShimmerFrameLayout;
    @BindView(R.id.recycler_view)
    VideoPlayerRecyclerView video_player;

    IVideoLoadListener mIVideoLoadListener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Video");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        ButterKnife.bind(this);
        mIVideoLoadListener = this;

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        video_player.setLayoutManager(layoutManager);
        VerticalSpacingItemDecorator decorator = new VerticalSpacingItemDecorator(10);
        video_player.addItemDecoration(decorator);

        loadFirebaseVideo();
    }

    private void loadFirebaseVideo() {
//        mShimmerFrameLayout.startShimmerAnimation();
        ArrayList<MediaObject> videoList = new ArrayList<>();

        //Delay for secs
        new Handler().postDelayed((Runnable) () -> {
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                for(DataSnapshot videoSnapShot : snapshot.getChildren())
                                {
                                    VideoModel videoModel = videoSnapShot.getValue(VideoModel.class);
                                    MediaObject mediaObject = new MediaObject(
                                            videoModel.getName(),
                                            videoModel.getMediaurl(),
                                            videoModel.getThumbnail(),
                                            "");
                                videoList.add(mediaObject);
                                }
                                mIVideoLoadListener.onVideoLoadSuccess(videoList);
                                }else{
                                mIVideoLoadListener.onVideoLoadFailed("Video not Found");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            mIVideoLoadListener.onVideoLoadFailed(error.getMessage());
                        }
                    });
        }, 3000);
    }

    @Override
    public void onVideoLoadSuccess(ArrayList<MediaObject> videoList) {
        video_player.setMediaObjects(videoList);
        VideoPlayerRecyclerAdapter adapter = new VideoPlayerRecyclerAdapter(videoList, initGlide());
        video_player.setAdapter(adapter);
//        mShimmerFrameLayout.stopShimmerAnimation();
//        mShimmerFrameLayout.setVisibility(View.GONE);
    }

    private RequestManager initGlide() {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.white_background)
                .error(R.drawable.white_background);
        return Glide.with(this).setDefaultRequestOptions(options);
    }

    @Override
    public void onVideoLoadFailed(String message) {
//        mShimmerFrameLayout.stopShimmerAnimation();
//        mShimmerFrameLayout.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}