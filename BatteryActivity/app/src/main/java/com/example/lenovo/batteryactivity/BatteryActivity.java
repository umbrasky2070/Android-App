package com.example.lenovo.batteryactivity;



import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class BatteryActivity extends Activity {
    private static final String LOG_TAG = "Info";
    private ToggleButton tb=null;
    private TextView tv=null;
    private BatteryReceiver receiver=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);
        receiver = new BatteryReceiver();
        tb = (ToggleButton) findViewById(R.id.tb);
        tv = (TextView) findViewById(R.id.tv);
        tb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                //获取电池电量
                if (isChecked) {
                    IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                    registerReceiver(receiver, filter);//注册BroadcastReceiver
                } else {
                    //停止获取电池电量
                    unregisterReceiver(receiver);
                    tv.setText(null);
                }
            }
        });
        WriteFile(tv.getText().toString());


}
private class BatteryReceiver extends BroadcastReceiver {
    String s0 = new String("电量信息如下："+"\n");
    String s1 = new String("电量实时信息："+"\n");
    String s2 = null;

    SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
    Date date = new Date();

    @Override
    public void onReceive(Context context,  Intent intent) {
            int voltage = intent.getIntExtra("voltage", 0);
            int current = intent.getExtras().getInt("level");//获得当前电量
            int total = intent.getExtras().getInt("scale");//获得总电量
            int percent = current*100/total;

            Date date1 = new Date();
            String d1 = df.format(date1);

            long t = date1.getTime() - date.getTime();
            long m = t/(1000*60);
            System.out.println("date:"+date+"   date1:"+date1+"   m:"+m);
            s2 = new String("当前的电量是" + percent + "%,电压是" + voltage + "mV   " + d1 + "\n");
                if ((m >= 1 && m <= 2)||(t >=0 && t<=3)) {
                    s0 = new String(s0 + "当前的电量是" + percent + "%,电压是" + voltage + "mV   " + d1 + "\n");
                    if (m>=1 && m<=2) {
                        date = date1;
                    }
                }
            tv.setText(s1+s2 + s0);
        System.out.println(tv.getText());

                }

}

    public void WriteFile(String content){
        try {

            FileOutputStream fos = openFileOutput("Battery.txt",MODE_PRIVATE+MODE_WORLD_READABLE+MODE_WORLD_WRITEABLE);
            fos.write(content.getBytes());
            fos.close();
            System.out.println("保存成功");
            System.out.println(this.getFilesDir());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
    public File getAlbumStorageDir(Context context, String albumName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }
}

