package com.example.lenovo.batterymonitor;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;


public class MainActivity extends Activity {
    private static final String CHARGER_CURRENT_NOW =
            "/sys/class/power_supply/battery/Current_now";


    private TextView mCurrent;


    public void onResume(){


        mCurrent = (TextView)findViewById(R.id.current);

        try {

            mCurrent.setText(readCurrentFile(new File(CHARGER_CURRENT_NOW)));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public String readCurrentFile(File file) throws IOException {
        InputStream input = new FileInputStream(file);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    input));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } finally {
            input.close();
        }
    }


}