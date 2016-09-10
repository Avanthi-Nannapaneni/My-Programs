package com.example.sdcard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	// GUI controls
	EditText txtData;
	Button btnWriteSDFile;
	Button btnReadSDFile;
	Button btnClearScreen;
	Button btnClose;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	// bind GUI elements with local controls
	txtData = (EditText) findViewById(R.id.txtData);
	txtData.setHint("Enter some lines of data here...");

	btnWriteSDFile = (Button) findViewById(R.id.btnWriteSDFile);
	btnWriteSDFile.setOnClickListener(this);
	
	

		btnReadSDFile = (Button) findViewById(R.id.btnReadSDFile);
		btnReadSDFile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					File myFile = new File("/sdcard/mysdfile.txt");
					FileInputStream fIn = new FileInputStream(myFile);
					BufferedReader myReader = new BufferedReader(
							new InputStreamReader(fIn));
					String aDataRow = "";
					String aBuffer = "";
					while ((aDataRow = myReader.readLine()) != null) {
						aBuffer += aDataRow + "\n";
					}
					txtData.setText(aBuffer);
					myReader.close();
					Toast.makeText(getBaseContext(),
							"Done reading SD 'mysdfile.txt'",
							Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(getBaseContext(), e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
		
		


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try {
			File myFile = new File("/sdcard/mysdfile.txt");
			myFile.createNewFile();
			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = 
									new OutputStreamWriter(fOut);
			myOutWriter.append(txtData.getText());
			myOutWriter.close();
			fOut.close();
			Toast.makeText(getBaseContext(),
					"Done writing SD 'mysdfile.txt'",
					Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
	}

}// AndSDcard

