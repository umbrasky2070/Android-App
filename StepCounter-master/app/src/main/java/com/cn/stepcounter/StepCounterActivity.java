package com.cn.stepcounter;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.util.Calendar;

import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;
import com.cn.stepcounter.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;


/** Ӧ�ó�����û����棬
 * ��Ҫ���ܾ��ǰ���XML�����ļ���������ʾ���棬
 * �����û����н���
 * ����ǰ̨����չʾ
 * ��android��Activity����ǰ̨����չʾ��service�����̨����Ҫ�������е�����
 * Activity��Service֮���ͨ����Ҫ��Intent����
 */
@SuppressLint("HandlerLeak")
public class StepCounterActivity extends Activity {

	//�����ı���ؼ�
	private TextView tv_show_step;// ����
	private TextView tv_week_day;// ����
	private TextView tv_date;// ����

	private TextView tv_timer;// ����ʱ��

	private TextView tv_distance;// �г�
	private TextView tv_calories;// ��·��
	private TextView tv_velocity;// �ٶ�

	private Button btn_start;// ��ʼ��ť
	private Button btn_stop;// ֹͣ��ť

	private GifView gifView;
	private boolean isRun = false;

	// ʮ���Ǳ�
	private ImageView iv_star_1;                
	private ImageView iv_star_2;
	private ImageView iv_star_3;
	private ImageView iv_star_4;
	private ImageView iv_star_5;
	private ImageView iv_star_6;
	private ImageView iv_star_7;
	private ImageView iv_star_8;
	private ImageView iv_star_9;
	private ImageView iv_star_10;

	private long timer = 0;// �˶�ʱ��
	private  long startTimer = 0;// ��ʼʱ��

	private  long tempTime = 0;

	private Double distance = 0.0;// ·�̣���
	private Double calories = 0.0;// ��������·��
	private Double velocity = 0.0;// �ٶȣ���ÿ��

	private int step_length = 0;  //����
	private int weight = 0;       //����
	private int total_step = 0;   //�ߵ��ܲ���

	private Thread thread;  //�����̶߳���

	private TableRow hide1, hide2;
	private TextView step_counter;

	// ������һ���µ�Handlerʵ��ʱ, ����󶨵���ǰ�̺߳���Ϣ�Ķ�����,��ʼ�ַ�����
	// Handler����������, (1) : ��ʱִ��Message��Runnalbe ����
	// (2): ��һ������,�ڲ�ͬ���߳���ִ��.

	Handler handler = new Handler() {// Handler�������ڸ��µ�ǰ����,��ʱ������Ϣ�����÷�����ѯ����������ʾ��������������������
		//��Ҫ�������̷߳��͵�����, ���ô�����������̸߳���UI
		//Handler���������߳���(UI�߳���), �������߳̿���ͨ��Message��������������, 
		//Handler�ͳе��Ž������̴߳�������(���߳���sendMessage()��������Message����(�����������)
		//����Щ��Ϣ�������̶߳����У�������߳̽��и���UI��

		@Override                  //��������ǴӸ���/�ӿ� �̳й����ģ���Ҫ��дһ��
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);        // �˴����Ը���UI

			countDistance();     //���þ��뷽������һ�����˶�Զ

			if (timer != 0 && distance != 0.0) {

				// ���ء�����
				// �ܲ�������kcal�������أ�kg�������루�����1.036
				calories = weight * distance * 0.001;
				//�ٶ�velocity
				velocity = distance * 1000 / timer;
			} else {
				calories = 0.0;
				velocity = 0.0;
			}

			countStep();          //���ò�������

			tv_show_step.setText(total_step + "");// ��ʾ��ǰ����

			tv_distance.setText(formatDouble(distance));// ��ʾ·��
			tv_calories.setText(formatDouble(calories));// ��ʾ��·��
			tv_velocity.setText(formatDouble(velocity));// ��ʾ�ٶ�

			tv_timer.setText(getFormatTime(timer));// ��ʾ��ǰ����ʱ��

			changeStep();// ���õ�ǰ�������Ǳ�

		}

		/**
		 * ���õ�ǰ�������Ǳ�
		 */
		private void changeStep() {
			int level = StepDetector.CURRENT_SETP / 10;  //ÿ��150����һ������
			switch (level) {
			case 10:
				iv_star_10.setImageResource(R.drawable.start_disable);
			case 9:
				iv_star_9.setImageResource(R.drawable.start_red);
			case 8:
				iv_star_8.setImageResource(R.drawable.start_red);
			case 7:
				iv_star_7.setImageResource(R.drawable.start_red);
			case 6:
				iv_star_6.setImageResource(R.drawable.start_red);
			case 5:
				iv_star_5.setImageResource(R.drawable.start_green);
			case 4:
				iv_star_4.setImageResource(R.drawable.start_green);
			case 3:
				iv_star_3.setImageResource(R.drawable.start_green);
			case 2:
				iv_star_2.setImageResource(R.drawable.start_green);
			case 1:
				iv_star_1.setImageResource(R.drawable.start_green);
				break;
			case 0:
				iv_star_1.setImageResource(R.drawable.star_enable);
				iv_star_2.setImageResource(R.drawable.star_enable);
				iv_star_3.setImageResource(R.drawable.star_enable);
				iv_star_4.setImageResource(R.drawable.star_enable);
				iv_star_5.setImageResource(R.drawable.star_enable);
				iv_star_6.setImageResource(R.drawable.star_enable);
				iv_star_7.setImageResource(R.drawable.star_enable);
				iv_star_8.setImageResource(R.drawable.star_enable);
				iv_star_9.setImageResource(R.drawable.star_enable);
				iv_star_10.setImageResource(R.drawable.star_enable);
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.main);  //���õ�ǰ��Ļ

		if (SettingsActivity.sharedPreferences == null) {
			SettingsActivity.sharedPreferences = this.getSharedPreferences(
					SettingsActivity.SETP_SHARED_PREFERENCES,
					Context.MODE_PRIVATE);
		}

		Bundle extras = getIntent().getExtras(); 
		isRun = extras.getBoolean("run");

		if(isRun){
			//����(��������)
			gifView = (GifView)findViewById(R.id.gif_view);
			gifView.setGifImageType(GifImageType.COVER);
			gifView.setShowDimension(100, 100);
			gifView.setGifImage(R.drawable.walk_gif);
			gifView.showCover();

		}else{
			gifView = (GifView)findViewById(R.id.gif_view);
			gifView.setGifImageType(GifImageType.COVER);
			gifView.setShowDimension(100, 100);
			gifView.setGifImage(R.drawable.run_gif);
			gifView.showCover();
		}

		if (thread == null) {

			thread = new Thread() {// ���߳����ڼ�����ǰ�����ı仯

				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					int temp = 0;
					while (true) {
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (StepCounterService.FLAG) {
							Message msg = new Message();
							if (temp != StepDetector.CURRENT_SETP) {
								temp = StepDetector.CURRENT_SETP;
							}
							if (startTimer != System.currentTimeMillis()) {
								timer = tempTime + System.currentTimeMillis()
										- startTimer;
							}
							handler.sendMessage(msg);// ֪ͨ���߳�
						}
					}
				}
			};
			thread.start();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		Log.i("APP", "on resuame.");
		// ��ȡ����ؼ�
		addView();

		// ��ʼ���ؼ�
		init();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	/**
	 * ��ȡActivity��ؿؼ�
	 */
	private void addView() {
		tv_show_step = (TextView) this.findViewById(R.id.show_step);
		tv_week_day = (TextView) this.findViewById(R.id.week_day);
		tv_date = (TextView) this.findViewById(R.id.date);

		tv_timer = (TextView) this.findViewById(R.id.timer);

		tv_distance = (TextView) this.findViewById(R.id.distance);
		tv_calories = (TextView) this.findViewById(R.id.calories);
		tv_velocity = (TextView) this.findViewById(R.id.velocity);

		btn_start = (Button) this.findViewById(R.id.start);
		btn_stop = (Button) this.findViewById(R.id.stop);

		// �Ǳ�
		iv_star_1 = (ImageView) this.findViewById(R.id.iv_1);
		iv_star_2 = (ImageView) this.findViewById(R.id.iv_2);
		iv_star_3 = (ImageView) this.findViewById(R.id.iv_3);
		iv_star_4 = (ImageView) this.findViewById(R.id.iv_4);
		iv_star_5 = (ImageView) this.findViewById(R.id.iv_5);
		iv_star_6 = (ImageView) this.findViewById(R.id.iv_6);
		iv_star_7 = (ImageView) this.findViewById(R.id.iv_7);
		iv_star_8 = (ImageView) this.findViewById(R.id.iv_8);
		iv_star_9 = (ImageView) this.findViewById(R.id.iv_9);
		iv_star_10 = (ImageView) this.findViewById(R.id.iv_10);

		hide1 = (TableRow)findViewById(R.id.hide1);
		hide2 = (TableRow)findViewById(R.id.hide2);
		step_counter = (TextView)findViewById(R.id.step_counter);


		if(isRun){
			hide1.setVisibility(View.GONE);
			hide2.setVisibility(View.GONE);
			step_counter.setText("����");
		}

		Intent service = new Intent(this, StepCounterService.class);
		stopService(service);
		StepDetector.CURRENT_SETP = 0;
		tempTime = timer = 0;
		tv_timer.setText(getFormatTime(timer));      //����ر�֮�󣬸�ʽ��ʱ��
		tv_show_step.setText("0");
		tv_distance.setText(formatDouble(0.0));
		tv_calories.setText(formatDouble(0.0));
		tv_velocity.setText(formatDouble(0.0));

		handler.removeCallbacks(thread);

	}

	/**
	 * ��ʼ������
	 */
	private void init() {

		step_length = SettingsActivity.sharedPreferences.getInt(
				SettingsActivity.STEP_LENGTH_VALUE, 70);
		weight = SettingsActivity.sharedPreferences.getInt(
				SettingsActivity.WEIGHT_VALUE, 50);

		countDistance();
		countStep();
		if ((timer += tempTime) != 0 && distance != 0.0) {  //tempTime��¼�˶�����ʱ�䣬timer��¼ÿ���˶�ʱ��

			// ���ء�����
			// �ܲ�������kcal�������أ�kg�������루�����1.036������һ��
			calories = weight * distance * 0.001;

			velocity = distance * 1000 / timer;
		} else {
			calories = 0.0;
			velocity = 0.0;
		}

		tv_timer.setText(getFormatTime(timer + tempTime));

		tv_distance.setText(formatDouble(distance));
		tv_calories.setText(formatDouble(calories));
		tv_velocity.setText(formatDouble(velocity));

		tv_show_step.setText(total_step + "");

		btn_start.setEnabled(!StepCounterService.FLAG);
		btn_stop.setEnabled(StepCounterService.FLAG);

		if (StepCounterService.FLAG) {
			btn_stop.setText(getString(R.string.pause));
		} else if (StepDetector.CURRENT_SETP > 0) {
			btn_stop.setEnabled(true);
			btn_stop.setText(getString(R.string.cancel));
		}

		setDate();
	}

	/**
	 * ������ʾ������
	 */
	private void setDate() {
		Calendar mCalendar = Calendar.getInstance();// ��ȡ����Calendar����
		int weekDay = mCalendar.get(Calendar.DAY_OF_WEEK);// ���������
		int month = mCalendar.get(Calendar.MONTH) + 1;// ��ǰ�·�
		int day = mCalendar.get(Calendar.DAY_OF_MONTH);// ��ǰ����

		tv_date.setText(month + getString(R.string.month) + day
				+ getString(R.string.day));// ��ʾ��ǰ����

		String week_day_str = new String();
		switch (weekDay) {
		case Calendar.SUNDAY:// ������
			week_day_str = getString(R.string.sunday);
			break;

		case Calendar.MONDAY:// ����һ
			week_day_str = getString(R.string.monday);
			break;

		case Calendar.TUESDAY:// ���ڶ�
			week_day_str = getString(R.string.tuesday);
			break;

		case Calendar.WEDNESDAY:// ������
			week_day_str = getString(R.string.wednesday);
			break;

		case Calendar.THURSDAY:// ������
			week_day_str = getString(R.string.thursday);
			break;

		case Calendar.FRIDAY:// ������
			week_day_str = getString(R.string.friday);
			break;

		case Calendar.SATURDAY:// ������
			week_day_str = getString(R.string.saturday);
			break;
		}
		tv_week_day.setText(week_day_str);
	}

	/**
	 * ���㲢��ʽ��doubles��ֵ��������λ��Ч����
	 * 
	 * @param doubles
	 * @return ���ص�ǰ·��
	 */
	private String formatDouble(Double doubles) {
		DecimalFormat format = new DecimalFormat("####.##");
		String distanceStr = format.format(doubles);
		return distanceStr.equals(getString(R.string.zero)) ? getString(R.string.double_zero)
				: distanceStr;
	}

	public void onClick(View view) {
		Intent service = new Intent(this, StepCounterService.class);
		switch (view.getId()) {
		case R.id.start:
			gifView.showAnimation();
			startService(service);
			btn_start.setEnabled(false);
			btn_stop.setEnabled(true);
			btn_stop.setText(getString(R.string.pause));
			startTimer = System.currentTimeMillis();
			tempTime = timer;
			break;

		case R.id.stop:
			stopService(service);
			gifView.showCover();
			if (StepCounterService.FLAG && StepDetector.CURRENT_SETP > 0) {
				btn_stop.setText(getString(R.string.cancel));
			} else {
				StepDetector.CURRENT_SETP = 0;
				tempTime = timer = 0;

				btn_stop.setText(getString(R.string.pause));
				btn_stop.setEnabled(false);

				tv_timer.setText(getFormatTime(timer));      //����ر�֮�󣬸�ʽ��ʱ��

				tv_show_step.setText("0");
				tv_distance.setText(formatDouble(0.0));
				tv_calories.setText(formatDouble(0.0));
				tv_velocity.setText(formatDouble(0.0));

				handler.removeCallbacks(thread);
			}
			btn_start.setEnabled(true);
			break;	
		}
	}

	/**
	 * �õ�һ����ʽ����ʱ��
	 * 
	 * @param time
	 *            ʱ�� ����
	 * @return ʱ���֣��룺����
	 */
	private String getFormatTime(long time) {
		time = time / 1000;
		long second = time % 60;
		long minute = (time % 3600) / 60;
		long hour = time / 3600;

		// ��������ʾ��λ
		// String strMillisecond = "" + (millisecond / 10);
		// ����ʾ��λ
		String strSecond = ("00" + second)
				.substring(("00" + second).length() - 2);
		// ����ʾ��λ
		String strMinute = ("00" + minute)
				.substring(("00" + minute).length() - 2);
		// ʱ��ʾ��λ
		String strHour = ("00" + hour).substring(("00" + hour).length() - 2);

		return strHour + ":" + strMinute + ":" + strSecond;
		// + strMillisecond;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_step, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			break;

		case R.id.ment_information:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * �������ߵľ���                                                                        
	 */
	private void countDistance() {
		if (StepDetector.CURRENT_SETP % 2 == 0) {
			distance = (StepDetector.CURRENT_SETP / 2) * 3 * step_length * 0.01;
		} else {
			distance = ((StepDetector.CURRENT_SETP / 2) * 3 + 1) * step_length * 0.01;
		}
	}

	/**
	 * ʵ�ʵĲ���
	 */
	private void countStep() {
		if (StepDetector.CURRENT_SETP % 2 == 0) {
			total_step = StepDetector.CURRENT_SETP;
		} else {
			total_step = StepDetector.CURRENT_SETP +1;
		}

		total_step = StepDetector.CURRENT_SETP;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

}
