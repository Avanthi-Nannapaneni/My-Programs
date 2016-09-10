package com.pavan.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button start,stop;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		start=(Button)findViewById(R.id.start);
		stop=(Button)findViewById(R.id.stop);
		stop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				stopService(new Intent(getApplicationContext(),Myservice.class));
			}
		});
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startService(new Intent(getApplicationContext(),Myservice.class));
			}
		});
	}

	
}
