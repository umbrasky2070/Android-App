package com.cj.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by lenovo on 2016/4/15.
 */
public class Card extends FrameLayout{
    private TextView label;

    public Card(Context context) {
        super(context);
        label = new TextView(getContext());
        label.setTextSize(32);
        label.setBackgroundColor(0x33FFD700);
        label.setGravity(Gravity.CENTER);
        LayoutParams lp = new LayoutParams(-1,-1);
        lp.setMargins(10, 10, 10, 10);
        addView(label,lp);
        setNum(0);
    }
    private  int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        if(num<=0) {
            label.setText("");
        }else{
            label.setText(num+"");
        }
    }


    public boolean equals(Card o) {
        return getNum() == o.getNum();
    }
}
