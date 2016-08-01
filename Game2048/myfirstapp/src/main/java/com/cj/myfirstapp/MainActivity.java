package com.cj.myfirstapp;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class MainActivity extends AppCompatActivity {

   public final static String EXTRA_MESSAGE = "com.cj.myfirstapp.Message";
    //定义ActionBar
    private ActionBar actionBar;

    //定义Handler对象
    private final Handler handler = new Handler() {
        @Override
        public void close() {

        }

        @Override
        public void flush() {

        }

        @Override
        public void publish(LogRecord record) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch(item.getItemId()){
            case R.id.action_search:
                openSearch();
                return true;
            case R.id.action_settings:
                openSetting();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSetting() {

    }

    private void openSearch() {

    }

    public  void sendMessage(View view){
        Intent intent = new Intent(this,DisplayMessage.class);
        EditText editText = (EditText)findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
    }
    /**
     * 初始化组件
     */
    private void initView(){
        //得到ActionBar
        actionBar = getActionBar();
    }

    /**
     * 初始化数据
     */
    private void initData(){
        //设置ActionBar标题不显示
        actionBar.setDisplayShowTitleEnabled(false);

        //设置ActionBar的背景
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));

        //设置ActionBar左边默认的图标是否可用
        actionBar.setDisplayUseLogoEnabled(true);

        //设置导航模式为Tab选项标签导航模式
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //设置ActinBar添加Tab选项标签
        actionBar.addTab(actionBar.newTab().setText("TAB1"));
        actionBar.addTab(actionBar.newTab().setText("TAB2"));
        actionBar.addTab(actionBar.newTab().setText("TAB3"));

    }
}
