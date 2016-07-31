package com.android.crazyguessmusic.model;

import android.widget.Button;

/**
 * ������������
 * 
 * @author Li Jian
 *
 */
public class WordButton {

	public int mIndex;
	public boolean mIsVisiable;
	public String mWordString;
	
	public Button mViewButton;
	
	public WordButton() {
		mIsVisiable = true;
		mWordString = "";
	}
}
