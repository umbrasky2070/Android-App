package com.android.crazyguessmusic.ui;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.ui.R;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.crazyguessmusic.data.Const;
import com.android.crazyguessmusic.model.IAlertDialogListener;
import com.android.crazyguessmusic.model.IWordButtonClickListener;
import com.android.crazyguessmusic.model.Song;
import com.android.crazyguessmusic.model.WordButton;
import com.android.crazyguessmusic.myui.MyGridView;
import com.android.crazyguessmusic.util.MyLog;
import com.android.crazyguessmusic.util.MyPlayer;
import com.android.crazyguessmusic.util.Util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;



public class MainActivity extends AppCompatActivity implements IWordButtonClickListener {
    public static final String TAG = " MainActivity";
    //定义答案状态
    public static final int ANSWER_RIGHT = 1;
    public static final int ANSWER_ERROR = 2;
    public static final int ANSWER_LACK = 3;
    //定义AlertDialog状态
    public static final int ID_DEL = 1;
    public static final int ID_Tip = 2;
    public static final int ID_LOCKCOIN = 3;
    //闪烁次数
    public static final int SPARK_TIMES = 6;
    //唱片动画
    private Animation mPanAnim;
    private LinearInterpolator mPanLin;

    private Animation mBarInAnim;
    private LinearInterpolator mBarInLin;

    private Animation mBarOutAnim;
    private LinearInterpolator mBarOutLin;

    private ImageView mPanView;
    private ImageView mBarView;
    private ImageButton mPlayButton;
    private boolean mIsRuning = false;

    //文字框容器
    private MyGridView mMyGridView;
    private ArrayList<WordButton> mAllWords;
    private ArrayList<WordButton> mBtnSelectWords;

    //当前歌曲名字
    private Song mCurrentSong;
    //当前歌曲的索引
    private int mCurrentSongIndex = -1;

    //当前关卡的界面索引
    private TextView mCurrentIndexView;

    //当前通关界面的索引
    private  TextView mCurrentSongIndexPassView;

    //当前通过界面的歌曲名字
    private  TextView mCurrentSongNamePassView;

    //已选文字框UI容器
    private LinearLayout mViewWordsContainer;

    //答案正确姐，界面
    private LinearLayout mPassView;

    //金币数量
    private int mCurrentCoins = Const.TOTAL_COINS;

    //当前金币的view
    private TextView mViewCoins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //读取数据
        int[] datas = Util.readData(MainActivity.this);
        mCurrentSongIndex = datas[Const.INDEX_STAGE];
        mCurrentCoins = datas[Const.INDEX_COINS];

        mMyGridView = (MyGridView) findViewById(R.id.gridview);
        mViewWordsContainer = (LinearLayout) findViewById(R.id.word_select_container);
        // 初始化控件
        mPanView = (ImageView) findViewById(R.id.imageView1);
        mBarView = (ImageView) findViewById(R.id.imageView2);
        mPlayButton = (ImageButton) findViewById(R.id.btn_play_start);
        mViewCoins = (TextView) findViewById(R.id.txt_bar_coins);
        mViewCoins.setText(mCurrentCoins + "");

        //注册监听器
        mMyGridView.registOnWordButtonClick(this);

        //初始化动画
        mPanAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        mPanLin = new LinearInterpolator();
        mPanAnim.setInterpolator(mPanLin);
        mPanAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 开启拨杆退出动画
                mBarView.startAnimation(mBarOutAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mBarInAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_45);
        mBarInLin = new LinearInterpolator();
        mBarInAnim.setFillAfter(true);
        mBarInAnim.setInterpolator(mBarInLin);
        mBarInAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 开始唱片动画
                mPanView.startAnimation(mPanAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mBarOutAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_d_45);
        mBarOutLin = new LinearInterpolator();
        mBarOutAnim.setFillAfter(true);
        mBarOutAnim.setInterpolator(mBarOutLin);
        mBarOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 整套动画播放完毕
                mIsRuning = false;
                mPlayButton.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handPlayButton();
            }
        });
        //初始化游戏数据
        initCurrentStageData();

        //处理删除一个文字事件
        handDeleteCoins();

        //提示答案
        handTipCoins();
    }

    private void handPlayButton() {
        if (mBarView != null) {
            if (!mIsRuning) {
                mIsRuning = true;
                // 开始拨杆进入动画
                mBarView.startAnimation(mBarInAnim);
                mPlayButton.setVisibility(View.INVISIBLE);
                //播放音乐
                MyPlayer.playSong(MainActivity.this,mCurrentSong.getFileName());
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //保存有些数据
        Util.saveData(MainActivity.this,mCurrentSongIndex -1,mCurrentCoins);
        //停止动画
        mPanView.clearAnimation();
        //停止音乐
        MyPlayer.stopSong(MainActivity.this);
    }

    private Song LoadStateSongIngo(int stateindex) {
        Song song = new Song();
        String[] state = Const.SONG_INFO[stateindex];
        song.setmFileName(state[Const.SONG_FILENAME]);
        song.setmSongName(state[Const.SONG_SONGNAME]);
        return song;
    }

    /*
     *加载当前关数据
     */
    private void initCurrentStageData() {
        //读取当前关的歌曲信息
        mCurrentSong = LoadStateSongIngo(++mCurrentSongIndex);
        //初始化已选择框
        mBtnSelectWords = initWordSelect();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);

        //清空原来的答案
        mViewWordsContainer.removeAllViews();

        //增加新的答案框
        for (int i = 0; i < mBtnSelectWords.size(); i++) {
            mViewWordsContainer.addView(mBtnSelectWords.get(i).mViewButton, params);
        }

        //显示当前关的索引
       mCurrentIndexView = (TextView) findViewById(R.id.text_current_stage);
        if (mCurrentIndexView != null) {
            mCurrentIndexView.setText((mCurrentSongIndex + 1) + "");
        }

        //获得数据
        mAllWords = initAllWord();
        //更新数据-MyGridView
        mMyGridView.updateData(mAllWords);

        //一开始就播放音乐
        handPlayButton();
    }

    /**
     * 初始化待选文字框
     */
    private ArrayList<WordButton> initAllWord() {
        ArrayList<WordButton> data = new ArrayList<>();
        // 获得所有待选文字
        String[] words = generateWords();
        // .........
        for (int i = 0; i < MyGridView.COUNTS_WORDS; i++) {
            WordButton button = new WordButton();
            button.mWordString = words[i];
            data.add(button);
        }
        return data;
    }

    /**
     * 初始化已选择文字框
     *
     * @return
     */
    private ArrayList<WordButton> initWordSelect() {
        ArrayList<WordButton> data = new ArrayList<>();
        for (int i = 0; i < mCurrentSong.getNameLength(); i++) {
            View view = Util.getView(MainActivity.this, R.layout.self_ui_gridview_item);

            final WordButton holder = new WordButton();
            holder.mViewButton = (Button) view.findViewById(R.id.item_btn);
            holder.mViewButton.setTextColor(Color.WHITE);
            holder.mViewButton.setText("");
            holder.mIsVisiable = false;
            holder.mViewButton.setBackgroundResource(R.drawable.game_wordblank);
            holder.mViewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearAnswer(holder);
                }
            });
            data.add(holder);

        }
        return data;
    }

    private String[] generateWords() {
        Random random = new Random();
        String[] words = new String[MyGridView.COUNTS_WORDS];
        //存入歌名
        for (int i = 0; i < mCurrentSong.getNameLength(); i++) {
            words[i] = mCurrentSong.getNameCharacters()[i] + "";
        }
        //存入随机文字
        for (int i = mCurrentSong.getNameLength(); i < MyGridView.COUNTS_WORDS; i++) {
            words[i] = getRandomChar() + "";
        }
        // 打乱文字顺序：首先从所有元素中随机选取一个与第一个元素进行交换，
        // 然后在第二个之后选择一个元素与第二个交换，直到最后一个元素。
        // 这样能够确保每个元素在每个位置的概率都是1/n。
        for (int i = MyGridView.COUNTS_WORDS - 1; i >= 0; i--) {
            int index = random.nextInt(i + 1);
            String buf = words[index];
            words[index] = words[i];
            words[i] = buf;
        }
        return words;
    }

    private char getRandomChar() {
        String str = "";
        int hightPos;
        int lowPos;

        Random random = new Random();
        hightPos = 176 + Math.abs(random.nextInt(39));  //176 = 0xB0
        lowPos = 161 + Math.abs(random.nextInt(93));     //161 = 0xA1

        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(hightPos)).byteValue();
        b[1] = (Integer.valueOf(lowPos)).byteValue();
        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str.charAt(0);
    }

    @Override
    public void onWordButtonClick(WordButton wordButton) {
        Toast.makeText(this, wordButton.mIndex + "", Toast.LENGTH_SHORT).show();
        setSelectWord(wordButton);
        int checkResult = checkAnswer();

        if (checkResult == ANSWER_RIGHT) {
            //答案正确
            handlePass();

        } else if (checkResult == ANSWER_ERROR) {
            //答案错误，闪烁效果
            SparkWrods();

        } else if (checkResult == ANSWER_LACK) {
            //设置字体为白色
            for (int i = 0; i < mBtnSelectWords.size(); i++) {
                mBtnSelectWords.get(i).mViewButton.setTextColor(Color.WHITE);
            }
        }
    }

    /**
     * 设置答案
     *
     * @param wordButton
     */
    private void setSelectWord(WordButton wordButton) {
        for (int i = 0; i < mBtnSelectWords.size(); i++) {
            if (mBtnSelectWords.get(i).mWordString.length() == 0) {   //等于0，说明当前选择框，没有选中文字
                //设置文字框内容可见性
                mBtnSelectWords.get(i).mViewButton.setText(wordButton.mWordString);
                mBtnSelectWords.get(i).mIsVisiable = true;
                mBtnSelectWords.get(i).mWordString = wordButton.mWordString;
                //记录索引
                mBtnSelectWords.get(i).mIndex = wordButton.mIndex;

                MyLog.d(TAG, mBtnSelectWords.get(i).mIndex + "");
                //设置待选框可见性
                setButtonVisiable(wordButton, View.INVISIBLE);

                break;
            }
        }
    }

    /**
     * 设置待选文字框是否可见
     */
    private void setButtonVisiable(WordButton button, int visibility) {
        button.mViewButton.setVisibility(visibility);
        button.mIsVisiable = (visibility == View.VISIBLE) ? true : false;
        MyLog.d(TAG, button.mIsVisiable + "");
    }

    private void clearAnswer(WordButton wordButton) {
        wordButton.mViewButton.setText("");
        wordButton.mIsVisiable = false;
        wordButton.mWordString = "";
        //设置待选框可见性
        setButtonVisiable(mAllWords.get(wordButton.mIndex), View.VISIBLE);
    }

    //判断答案是否正确
    private int checkAnswer() {
        //检查答案是否完整
        for (int i = 0; i < mBtnSelectWords.size(); i++) {
            if (mBtnSelectWords.get(i).mWordString.length() == 0) {
                return ANSWER_LACK;
            }
        }
        //答案完整，检查正确性
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mBtnSelectWords.size(); i++) {
            sb.append(mBtnSelectWords.get(i).mWordString);
        }
        return (sb.toString().equals(mCurrentSong.getSongName())) ? ANSWER_RIGHT : ANSWER_ERROR;
    }

    //闪烁效果
    private void SparkWrods() {
        TimerTask task = new TimerTask() {
            boolean mChange = false;
            int mSparkTimes = 0;

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (++mSparkTimes > SPARK_TIMES) {
                            return;
                        }
                        // 执行闪烁逻辑：交替显示红色和白色文字
                        for (int i = 0; i < mBtnSelectWords.size(); i++) {
                            mBtnSelectWords.get(i).mViewButton.setTextColor(
                                    mChange == true ? Color.RED : Color.WHITE);
                        }
                        mChange = !mChange;
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1, 150);
    }

    //过关界面逻辑
    public void handlePass() {
        mPassView = (LinearLayout) findViewById(R.id.pass_view);
        mPassView.setVisibility(View.VISIBLE);

        //停止播放动画
        mPanView.clearAnimation();

        //停止正在播放的声音
        MyPlayer.stopSong(MainActivity.this);
        MyPlayer.playTone(MainActivity.this,MyPlayer.INDEX_COIN);

        //当前通关的索引
        mCurrentSongIndexPassView = (TextView) findViewById(R.id.level);
        if (mCurrentSongIndexPassView != null){
            mCurrentSongIndexPassView.setText((mCurrentSongIndex+1)+"");
        }

        //当前通歌曲的名字
        mCurrentSongNamePassView = (TextView) findViewById(R.id.song_name);
        if (mCurrentSongNamePassView != null){
            mCurrentSongNamePassView.setText(mCurrentSong.getSongName());
        }

        //下一关按键监听
        ImageButton nextBtn = (ImageButton) findViewById(R.id.next_button);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allPass()){
                    //全部通关动画
                    Util.startActivity(MainActivity.this,AllPassView.class);
                }else {
                    //进入到下一关
                    mPassView.setVisibility(View.GONE);
                    initCurrentStageData();
                }
            }
        });
    }

    //判断是否全部通关
    private boolean allPass(){
        return mCurrentSongIndex == Const.SONG_INFO.length - 1;
    }

    /*
     *当前金币数量是否可以减少
     * @param data
     * @return true可以减少，false不能减少
     */
    private boolean handleCoins(int data){
        if (mCurrentCoins + data >0){
            mCurrentCoins += data;
            mViewCoins.setText(mCurrentCoins+"");
            return true;
        }else {
            return false;
        }
    }

    /*
     *从配置文件读取删除操作所用的金币
     * @return
     */
    private int getDelateCoins(){
        return  this.getResources().getInteger(R.integer.pay_delete_word);
    }

    /*
    *从配置文件读取提示操作所用的金币
    * @return
    */
    private int getTipCoins(){
        return this.getResources().getInteger(R.integer.pay_tip_answer);
    }

    /*
     *处理删除事件
     */
    private void handDeleteCoins(){
        ImageButton deleteCoins = (ImageButton) findViewById(R.id.btn_delete_word);
        deleteCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirm(ID_DEL);
            }
        });
    }

   /*
    *处理提示事件
    */
    private void handTipCoins(){
        ImageButton tipCoins = (ImageButton) findViewById(R.id.tip_delete_word);
        tipCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirm(ID_Tip);
            }
        });
    }

    /**
     * 自动选择一个答案
     */
    private void tipAnswer(){
        boolean tipWord = false;
        for (int i=0;i<mBtnSelectWords.size();i++){
            if (mBtnSelectWords.get(i).mWordString.length() == 0){

                //减少金币的数量
                if (!(handleCoins(-getTipCoins()))){
                    //金币数量不够显示对话框
                    showConfirm(ID_LOCKCOIN);
                    return;
                }
                onWordButtonClick(findIsAnswer(i));
                //获取一个正确文字并填入
                tipWord = true;
                break;
            }
        }
        //如果没有可填充的答案，闪烁文字
        if (!tipWord){
            SparkWrods();
        }
    }
    /*
     *删除文字的提示
     */
    private void deleteOneWord(){
        //减少金币的数量
        if (!(handleCoins(-getDelateCoins()))){
            //金币数量不够显示对话框
            showConfirm(ID_LOCKCOIN);
            return;
        }
        setButtonVisiable(findNotWord(), View.INVISIBLE);
    }

    /*
     *找到一个不是正确答案的文字
     */
    private WordButton findNotWord(){
        Random random = new Random();
        WordButton buf;
        while (true){
            int index = random.nextInt(MyGridView.COUNTS_WORDS);
            buf = mAllWords.get(index);
            if (buf.mIsVisiable && !isTheAnswerWord(buf)){
                return buf;
            }
        }

    }

    /*
	 * 判断某个文字是否为答案
	 * @param word
	 * @return
     */
  private boolean isTheAnswerWord(WordButton word){
      boolean result = false;

      for (int i=0;i<mCurrentSong.getNameLength();i++){
          if (word.mWordString.equals(""+mCurrentSong.getNameCharacters()[i])){
              result = true;
          }
      }
      return result;
  }

    /**
     * 找到一个答案文字
     *
     * @param index 当前需要填入答案框的索引
     * @return
     */
    private WordButton findIsAnswer(int index){
        WordButton buf;

        for (int i=0;i<MyGridView.COUNTS_WORDS;i++){
            buf = mAllWords.get(i);
            if (buf.mWordString.equals(mCurrentSong.getNameCharacters()[index]+"")){
                return buf;
            }
        }
        return null;
    }

    //删除答案的AlertDialog
    private IAlertDialogListener mDelAlertDialog = new IAlertDialogListener() {
        @Override
        public void onClick() {
            //减少金币
            deleteOneWord();
        }
    };

    //提示答案的AlertDialog
    private IAlertDialogListener mTipAlertDialog = new IAlertDialogListener() {

        @Override
        public void onClick() {
            //减少金币
            tipAnswer();
        }
    };

    //金币不足的AlertDialog
    private IAlertDialogListener mLockCoinAlertDialog = new IAlertDialogListener() {
        @Override
        public void onClick() {
            //金币不足的相关操作

        }
    };

    /*
     *显示对话框
     */
    private void showConfirm(int id){
        switch (id){
            case ID_DEL:
                Util.showDialog(MainActivity.this,"确定花费"+getDelateCoins()+"个金币，删除一个错误答案！",mDelAlertDialog);
                break;
            case ID_Tip:
                Util.showDialog(MainActivity.this,"确定花费"+getTipCoins()+"个金币，提示一个正确答案！",mTipAlertDialog);
                break;
            case ID_LOCKCOIN:
                Util.showDialog(MainActivity.this,"金币不足，请充值！",mLockCoinAlertDialog);
                break;
        }
    }

}
