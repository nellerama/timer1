package com.example.prototimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	CountDownTimer work;
	CountDownTimer rest;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void startTimer(View v)
	{
		EditText workSec= (EditText)findViewById(R.id.editTextWorkSeconds);
		EditText workMin= (EditText)findViewById(R.id.editTextWorkMinutes);
		EditText restSec= (EditText)findViewById(R.id.editTextRestSeconds);
		EditText restMin= (EditText)findViewById(R.id.editTextRestMinutes);

		work= getWorkTimer(workMin, workSec);
		rest= getRestTimer(restMin, restSec);
		work.start();
	}

	private long getTotalTime()
	{
		EditText workSec= (EditText)findViewById(R.id.editTextWorkSeconds);
		EditText workMin= (EditText)findViewById(R.id.editTextWorkMinutes);
		EditText restSec= (EditText)findViewById(R.id.editTextRestSeconds);
		EditText restMin= (EditText)findViewById(R.id.editTextRestMinutes);

		int wSec=0, wMin=0, rSec=0, rMin=0, round=0, count=0, totalTime=0;

		//convert editables to long
		if(workMin.getText().toString().trim().length()!=0)
			wMin=Integer.parseInt((workMin.getText().toString()))*60;
		if(workSec.getText().toString().trim().length()!=0)
			wSec= Integer.parseInt((workSec.getText().toString()));

		if(restMin.getText().toString().trim().length()!=0)
			rMin=Integer.parseInt((restMin.getText().toString()))*60;
		if(restSec.getText().toString().trim().length()!=0)
			rSec= Integer.parseInt((restSec.getText().toString()));

		totalTime=wSec+wMin+rSec+rMin;
		long timeMilli= totalTime*1000;
		return timeMilli;
	}

	public void timer(View v)
	{


		//get user enterd values
		EditText workSec= (EditText)findViewById(R.id.editTextWorkSeconds);
		EditText workMin= (EditText)findViewById(R.id.editTextWorkMinutes);
		EditText restSec= (EditText)findViewById(R.id.editTextRestSeconds);
		EditText restMin= (EditText)findViewById(R.id.editTextRestMinutes);

		int wMin, rSec,wSec, rMin, round=3, totalTime;

		if(workMin.getText().toString().trim().length()!=0)
			wMin=Integer.parseInt((workMin.getText().toString()))*60;
		else
			wMin=0;
		if(workSec.getText().toString().trim().length()!=0)
			wSec= Integer.parseInt((workSec.getText().toString()));
		else
			wSec=0;

		if(restMin.getText().toString().trim().length()!=0)
			rMin=Integer.parseInt((restMin.getText().toString()))*60;
		else
			rMin=0;
		if(restSec.getText().toString().trim().length()!=0)
			rSec= Integer.parseInt((restSec.getText().toString()));
		else 
			rSec=0;

		//convert editables to long
		 final int wCount=wSec+wMin;
		 final int rCount=rSec+rMin;

		new CountDownTimer(getTotalTime()*round,975)
		{
			int workCheck=wCount;
			int restCheck= rCount;
			@Override
			public void onTick(long millisUntilFinished) 
			{

				setContentView(R.layout.activity_timer);
				if(workCheck>0)
				{
					((EditText) findViewById(R.id.editTextIntervalTime)).setText(""+workCheck);
					((EditText) findViewById(R.id.editTextIntervalType)).setText(""+"Work");
					workCheck--;
				}
				else if(restCheck>0)
				{
					((EditText) findViewById(R.id.editTextIntervalTime)).setText(""+restCheck);
					((EditText) findViewById(R.id.editTextIntervalType)).setText(""+"Rest");
					restCheck--;
				}
				else
				{
					workCheck=wCount;
					restCheck=rCount;
					((EditText) findViewById(R.id.editTextIntervalTime)).setText(""+workCheck);
					((EditText) findViewById(R.id.editTextIntervalType)).setText(""+"Work");
					workCheck--;

				}
			}

			@Override
			public void onFinish() 
			{
				System.out.println("finished");

			}
		}.start();

	}

	public CountDownTimer getWorkTimer(final EditText min,final EditText sec)
	{
		//get values entered by user in the form of editables 
		long seconds=0, minutes=0;

		//convert editables to long
		if(min.getText().toString().trim().length()!=0)
			minutes=Integer.parseInt((min.getText().toString()))*60;
		if(sec.getText().toString().trim().length()!=0)
			seconds= Integer.parseInt((sec.getText().toString()));

		//calculate final time by adding milliseconds
		long time= (seconds+minutes)*1000;

		final ViewGroup vg= (ViewGroup)findViewById(R.id.LinearLayout);
		CountDownTimer timer= new CountDownTimer(time+1000, 1000)
		{
			public void onTick(long millisUntilFinished)
			{
				System.out.println("tick..."+(millisUntilFinished-1000)/1000%60);
				min.setText((millisUntilFinished)/60/1000+"");
				sec.setText(((millisUntilFinished)/1000%60)+"");
				vg.invalidate();
			}

			public void onFinish()
			{
				System.out.println("Work finished");
				rest.start();		
			}
		};

		return timer;
	}
	public CountDownTimer getRestTimer(final EditText min,final EditText sec)
	{

		//get values entered by user in the form of editables 
		long seconds=0, minutes=0;

		//convert editables to long
		if(min.getText().toString().trim().length()!=0)
			minutes=Integer.parseInt((min.getText().toString()))*60;
		if(sec.getText().toString().trim().length()!=0)
			seconds= Integer.parseInt((sec.getText().toString()));

		//calculate final time by adding milliseconds
		long time= (seconds+minutes)*1000;

		final ViewGroup vg= (ViewGroup)findViewById(R.id.LinearLayout);
		CountDownTimer timer= new CountDownTimer(time+1000, 1000)
		{
			public void onTick(long millisUntilFinished)
			{
				System.out.println("tick..."+(millisUntilFinished-1000)/1000%60);
				min.setText((millisUntilFinished)/60/1000+"");
				sec.setText(((millisUntilFinished)/1000%60)+"");
				vg.invalidate();
			}

			public void onFinish()
			{
				System.out.println("Work finished");
				work.start();		
			}
		};

		return timer;
	}
}
