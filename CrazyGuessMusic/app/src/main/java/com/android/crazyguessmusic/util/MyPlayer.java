package com.android.crazyguessmusic.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by lenovo on 2016/7/22.
 */
public class MyPlayer {

    public static final int INDEX_ENTER = 0;
    public static final int INDEX_CANCEL = 1;
    public static final int INDEX_COIN = 2;

    private static final String[] TONE_NAME = {"enter.mp3","cancel.mp3","coin.mp3"};
    //音效
    private static MediaPlayer[] mTonePlayer = new MediaPlayer[TONE_NAME.length];
    //歌曲
    private static MediaPlayer mMediaPlayer;

    /*
     *播放歌曲
     * @param context
     * @param fileName
     */
    public static  void playSong(Context context,String fileNmae){
        if (mMediaPlayer == null){
            mMediaPlayer = new MediaPlayer();
        }
        //强制重置
        mMediaPlayer.reset();
        //加载声音文件
        AssetManager assetManager = context.getAssets();
        try {
            AssetFileDescriptor fileDescriptor = assetManager.openFd(fileNmae);
            mMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(),fileDescriptor.getLength());
            mMediaPlayer.prepare();
            //声音播放
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
     *暂停播放歌曲
     * @param context
     * @param fileName
     */
    public static  void stopSong(Context context){
        if (mMediaPlayer != null){
            mMediaPlayer.stop();
        }
    }

    /*
     *播放音效，按钮点击的声音，不需要重复加载
     * @param context
     * @int index
     */
    public static void playTone(Context context,int index){
        //加载文件声音
        AssetManager assetManager = context.getAssets();

        if (mTonePlayer[index] == null){
            mTonePlayer[index] = new MediaPlayer();
            try {
                AssetFileDescriptor fileDescriptor = assetManager.openFd(TONE_NAME[index]);
                //setDataSource、prepare只需调用一次（这些相当于准备工作所以只需要调用一次）
                mTonePlayer[index].setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                mTonePlayer[index].prepare();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //start是需要多次调用的（这个是执行动作，多次执行，多次调用）
        mTonePlayer[index].start();
    }
}
