package com.example.prototimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

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
		final EditText min= (EditText) findViewById(R.id.editTextMinutes);
		final EditText sec= (EditText) findViewById(R.id.editTextSeconds);
		//get values entered by user in the form of editables 
		long seconds=0, minutes=0;
		
		//convert editables to long
		if(min.getText()!=null)
			minutes=Integer.parseInt((sec.getText().toString()))*60;
		if(sec.getText()!=null)
			seconds= Integer.parseInt((min.getText().toString()));

			System.out.println("Seconds: "+seconds);
		System.out.println("inutes: "+minutes);
		//calculate final time by adding milliseconds
		long time= (seconds+minutes)*1000;

		final ViewGroup vg= (ViewGroup)findViewById(R.id.LinearLayout);
		new CountDownTimer(time+1000, 1000)
		{
			public void onTick(long millisUntilFinished)
			{
				System.out.println("tick..."+(millisUntilFinished-1000)/1000%60);
				min.setText((millisUntilFinished-1000)/60/1000+"");
				sec.setText(((millisUntilFinished-1000)/1000%60)+"");
				vg.invalidate();
			}

			public void onFinish()
			{
				System.out.println("Finished");
			}
		}.start();
	}
}
