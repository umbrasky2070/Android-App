package com.android.crazyguessmusic.myui;

import java.util.ArrayList;

import android.content.Context;
import android.ui.R;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;


import com.android.crazyguessmusic.model.IWordButtonClickListener;
import com.android.crazyguessmusic.model.WordButton;
import com.android.crazyguessmusic.util.Util;

public class MyGridView extends GridView {
	public final static int COUNTS_WORDS = 24;
	
	private ArrayList<WordButton> mArrayList = new ArrayList<WordButton>();

	private MyGridAdapter mAdapter;
	
	private Context mContext;

	private Animation mScaleAnimation;

	private IWordButtonClickListener mWordButtonClickListener;

	public MyGridView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		
		mContext = context;
		
		mAdapter = new MyGridAdapter();
		this.setAdapter(mAdapter);
	}

    public void updateData(ArrayList<WordButton> list){
        mArrayList = list;
        //重新设置数据源
        setAdapter(mAdapter);
    }
	class MyGridAdapter extends BaseAdapter {
		public int getCount() {
			return mArrayList.size();
		}

		public Object getItem(int pos) {
			return mArrayList.get(pos);
		}

		public long getItemId(int pos) {
			return pos;
		}

		public View getView(int pos, View v, ViewGroup p) {
			 final WordButton holder;
			
			if (v == null) {
				v = Util.getView(mContext, R.layout.self_ui_gridview_item);

				//加载动画
				mScaleAnimation = AnimationUtils.loadAnimation(mContext,R.anim.scale);
				//设置时间间隔
				mScaleAnimation.setStartOffset(pos*100);

				holder = mArrayList.get(pos);
				holder.mIndex = pos;
				holder.mViewButton = (Button)v.findViewById(R.id.item_btn);
				holder.mViewButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mWordButtonClickListener.onWordButtonClick(holder);
					}
				});
				v.setTag(holder);
			} else {
				holder = (WordButton)v.getTag();
			}
			holder.mViewButton.setText(holder.mWordString);
			//播放动画
			v.startAnimation(mScaleAnimation);

			return v;
		}
	}
	
/*
 *注册监听器
 */
	public void registOnWordButtonClick(IWordButtonClickListener listener){
		mWordButtonClickListener = listener;
	}
}
