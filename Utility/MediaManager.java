package com.game.bentz.ShapeShift.Utility;


import android.content.Context;
import android.media.MediaPlayer;

import com.game.bentz.ShapeShift.R;

/**
 * Created by Michael on 7/1/16.
 */
public class MediaManager extends MediaPlayer {
    private static MediaManager mInstance = null;
    private MediaPlayer mPlayer;
    private Boolean mIsPaused;


    // read from files
    private MediaManager(){
        this.mIsPaused = false;
    }

    public static MediaManager getInstance(){
        if (mInstance == null){
            mInstance = new MediaManager();
        }
        return mInstance;
    }

    public Boolean isPaused(){
        return mIsPaused;
    }

    public void setPaused(Boolean pause){
        this.mIsPaused = pause;
    }

    public void mute(){
        if(mPlayer != null){
            mPlayer.setVolume(0,0);
        }
    }

    public void unmute(){
        if(mPlayer != null){
            mPlayer.setVolume(0,1);
        }
    }

    public void stop(){
        if (mPlayer != null){
            mPlayer.release();
            mPlayer = null;
        }
    }

    public void pause(){
        if (mPlayer != null && mPlayer.isPlaying()){
            mPlayer.pause();
        }
    }

    public void playCorrectSound(Context c){
        if (mPlayer == null){
            mPlayer = MediaPlayer.create(c, R.raw.pop);

            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
                @Override
                public void onCompletion(MediaPlayer mp){
                    stop();
                }
            });

            setPaused(false);
            mPlayer.start();
//            mPlayer.setLooping(true);
        } else {
            setPaused(false);
            mPlayer.start();
//            mPlayer.setLooping(true);
        }
    }
}
