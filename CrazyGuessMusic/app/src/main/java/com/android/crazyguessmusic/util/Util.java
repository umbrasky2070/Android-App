package com.android.crazyguessmusic.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.ui.R;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.crazyguessmusic.data.Const;
import com.android.crazyguessmusic.model.IAlertDialogListener;
import com.android.crazyguessmusic.ui.MainActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Util {

	private static AlertDialog mAlertDialog;

	public static View getView(Context context, int layoutId) {
		LayoutInflater inflater = (LayoutInflater)context.
				getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View layout = inflater.inflate(layoutId, null);
		
		return layout;
	}
	/*
	 *界面跳转
	 * @param context
	 * @param desti
	 */
	public static void startActivity(Context context,Class desti){
		Intent intent = new Intent();
		intent.setClass(context, desti);
		context.startActivity(intent);
		//关闭当前的Activity
		((Activity)context).finish();
	}

	/*
	 *AlertDialog
	 * @param context
	 * @param message
	 * @param listener
	 */
	public static void showDialog(final Context context,String message,final IAlertDialogListener listener){
		View dialogView = null;
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		dialogView = getView(context, R.layout.dialog_view);

		ImageButton okButton = (ImageButton) dialogView.findViewById(R.id.ok_button);
		ImageButton cancelButton = (ImageButton) dialogView.findViewById(R.id.cancel_button);
		TextView messageText = (TextView) dialogView.findViewById(R.id.txt_dialog_message);
		messageText.setText(message);
		okButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//关闭对话框
				if (mAlertDialog != null) {
					mAlertDialog.cancel();
				}
				//事件回调
				if (listener != null) {
					listener.onClick();
				}

				MyPlayer.playTone(context,MyPlayer.INDEX_ENTER);
			}
		});

		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//关闭对话框
				if (mAlertDialog != null) {
					mAlertDialog.cancel();
				}
			//cancel取消对话框，不做任何操作

				MyPlayer.playTone(context,MyPlayer.INDEX_CANCEL);
			}
		});

		//为dialog设置View
		builder.setView(dialogView);
		mAlertDialog = builder.create();

		//显示对话框
		mAlertDialog.show();
	}

	/*
     *数据保存
     * @param context
     * @param stageIndex
     * @param coins
     */
	public static void saveData(Context context,int stageIndex,int coins){
		FileOutputStream fos  = null;
		try {
			fos = context.openFileOutput(Const.FILE_NAME_DATA_SAVE,Context.MODE_PRIVATE);
			DataOutputStream dos = new DataOutputStream(fos);
			dos.writeInt(stageIndex);
			dos.writeInt(coins);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (fos !=  null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
     *数据读取
     * @param context
     */
	public static int[] readData(Context context){
		FileInputStream fis = null;
		int[] datas ={-1,Const.TOTAL_COINS};
		try {
			fis = context.openFileInput(Const.FILE_NAME_DATA_SAVE);
			DataInputStream dis = new DataInputStream(fis);
			datas[Const.INDEX_STAGE] = dis.readInt();
			datas[Const.INDEX_COINS] = dis.readInt();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		return datas;
	}
}
