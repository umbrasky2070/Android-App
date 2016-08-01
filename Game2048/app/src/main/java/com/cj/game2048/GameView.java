package com.cj.game2048;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Point;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;


public class GameView extends GridLayout{
    private  Card[][] cardsMap = new Card[4][4];
    private List<Point> emptyPoint = new ArrayList<Point>();
    int cardwidth;


    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initGameView();
    }

    private void initGameView(){
        setColumnCount(4);
        setBackgroundColor(0xffbbada0);
        setOnTouchListener(new OnTouchListener() {
            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;

                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5) {
                                swipeLeft();
                                System.out.println("Left");
                            } else if (offsetX > 5) {
                                swipeRight();
                                System.out.println("Right");
                            }
                        } else {
                            if (offsetY < -5) {
                                swipeUp();
                                System.out.println("Up");
                            } else if (offsetY > 5) {
                                swipeDown();
                                System.out.println("Down");
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cardwidth = (Math.min(w, h)-10)/4;
        addCards(cardwidth, cardwidth);
        startGame();
        System.out.println("onSizeChanged");

    }

    private void addCards(int cardwidth, int cardheight) {
        Card c = null;
        if(c==null) {
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    c = new Card(getContext());
                    c.setNum(0);
                    addView(c, cardwidth, cardheight);
                    cardsMap[x][y] = c;
                }
            }
        }
        System.out.println("AddCards");
    }
    private void startGame(){
        for (int y=0;y<4;y++){
            for(int x=0;x<4;x++){
                cardsMap[x][y].setNum(0);
            }
        }
        MainActivity.getMainActivity().clearScore();
        addRandom();
        addRandom();
        System.out.println("StartGame");
    }
    public  void addRandom(){
        emptyPoint.clear();
        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
                if(cardsMap[x][y].getNum()<=0){
                    emptyPoint.add(new Point(x,y));
                }
            }
        }
        Point p = emptyPoint.remove((int)(Math.random()*emptyPoint.size()));
System.out.println(p);
        cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
    }

    private void swipeLeft(){

        boolean merge = false;

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {

                for (int x1 = x+1; x1 < 4; x1++) {
                    if (cardsMap[x1][y].getNum()>0) {

                        if (cardsMap[x][y].getNum()<=0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x--;
                            merge = true;

                        }else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);
                            merge = true;
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                        }
                        break;
                    }
                }
            }
        }
        if(merge){
            addRandom();
            checkComplete();
        }

    }
    private void swipeRight(){

        boolean merge = false;

        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >=0; x--) {

                for (int x1 = x-1; x1 >=0; x1--) {
                    if (cardsMap[x1][y].getNum()>0) {

                        if (cardsMap[x][y].getNum()<=0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x++;
                            merge = true;

                        }else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);
                            merge = true;
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                        }
                        break;
                    }
                }
            }
        }
        if (merge){
            addRandom();
            checkComplete();
        }
    }
    private void swipeUp(){

        boolean merge = false;

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int y1 = y+1; y1 < 4; y1++) {
                    if (cardsMap[x][y1].getNum()>0) {

                        if (cardsMap[x][y].getNum()<=0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y--;
                            merge = true;

                        }else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            merge = true;
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                        }
                        break;

                    }
                }
            }
        }
        if(merge){
            addRandom();
            checkComplete();
        }
    }
    private void swipeDown(){

        boolean merge = false;

        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >=0; y--) {

                for (int y1 = y-1; y1 >=0; y1--) {
                    if (cardsMap[x][y1].getNum()>0) {

                        if (cardsMap[x][y].getNum()<=0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y++;
                            merge = true;
                        }else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            merge = true;
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                        }
                        break;
                    }
                }
            }
        }
        if(merge){
            addRandom();
            checkComplete();
        }
    }
    public  void  checkComplete(){
        boolean Complete = true;
        ALL:
        for (int y=0;y<4;y++){
            for (int x=0;x<4;x++){
                if ((cardsMap[x][y].getNum()==0)||
                        (x>0&&cardsMap[x][y].equals(cardsMap[x-1][y]))||
                        (x<3&&cardsMap[x][y].equals(cardsMap[x+1][y]))||
                        (y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))||
                        (y<3&&cardsMap[x][y].equals(cardsMap[x][y+1]))){
                    Complete = false;
                    break ALL;
                }
            }
        }
        if(Complete){
            new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重来", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();
        }
    }
}