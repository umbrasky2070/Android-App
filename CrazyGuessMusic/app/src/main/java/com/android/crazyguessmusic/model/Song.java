package com.android.crazyguessmusic.model;

/**
 * Created by lenovo on 2016/7/16.
 */
public class Song {
    //歌曲名称
    private String mSongName;
    //歌曲文件名
    private  String mFileName;
    //歌曲名称长度
    private int mNameLength;

    public char[] getNameCharacters(){
        return  mSongName.toCharArray();
    }

    public void setmSongName(String SongName) {
        this.mSongName = SongName;
        this.mNameLength = SongName.length();
    }

    public void setmFileName(String FileName) {
        this.mFileName = FileName;
    }

    public String getSongName()
    {
        return mSongName;
    }

    public String getFileName() {
        return mFileName;
    }

    public int getNameLength() {

        return mNameLength;
    }

}
