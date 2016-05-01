package com.example.prototimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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
		EditText rounds= (EditText)findViewById(R.id.editTextRounds);

		final MediaPlayer beepWork= MediaPlayer.create(getApplicationContext(), R.raw.beep_work);
		final MediaPlayer beepRest= MediaPlayer.create(getApplicationContext(), R.raw.beep_rest);
		final MediaPlayer beepEnd= MediaPlayer.create(getApplicationContext(), R.raw.beep_end);
		
		final int wMin, rSec,wSec, rMin, totalTime,round;
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
		if(rounds.getText().toString().trim().length()!=0)
			round=Integer.parseInt(rounds.getText().toString());
		else
			round=0;

		//convert editables to long
		final int wCount=wSec+wMin;
		final int rCount=rSec+rMin;

		new CountDownTimer(getTotalTime()*round,975)
		{
			int workCheck=wCount;
			int restCheck= rCount;
			int roundCheck=round;
			@Override
			public void onTick(long millisUntilFinished) 
			{

				setContentView(R.layout.activity_timer);
				if(workCheck>0)
				{
					beepWork.start();
					((EditText) findViewById(R.id.editTextIntervalTime)).setText(""+workCheck);
					((EditText) findViewById(R.id.editTextIntervalType)).setText(""+"Work");
					((EditText) findViewById(R.id.editTextRoundValue)).setText(""+roundCheck);
					workCheck--;
				}
				else if(restCheck>0)
				{
					beepWork.pause();
					((EditText) findViewById(R.id.editTextIntervalTime)).setText(""+restCheck);
					((EditText) findViewById(R.id.editTextIntervalType)).setText(""+"Rest");
					((EditText) findViewById(R.id.editTextRoundValue)).setText(""+roundCheck);
					restCheck--;
				}
				else
				{
					beepWork.start();
					roundCheck--;
					workCheck=wCount;
					restCheck=rCount;
					((EditText) findViewById(R.id.editTextIntervalTime)).setText(""+workCheck);
					((EditText) findViewById(R.id.editTextIntervalType)).setText(""+"Work");
					((EditText) findViewById(R.id.editTextRoundValue)).setText(""+roundCheck);
					workCheck--;
				}
			}

			@Override
			public void onFinish() 
			{
				MediaPlayer beepWork= MediaPlayer.create(getApplicationContext(), R.raw.beep_end);
				beepWork.start();
				setContentView(R.layout.finished_layout);
			}
		}.start();
	}

}
