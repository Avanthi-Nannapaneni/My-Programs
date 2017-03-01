package com.inspire.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class BluetoothActivity extends Activity {
    /** Called when the activity is first created. */
	 int s=1;
    @Override
   
	/** Activity is created and bluetooth adapter is called for action request **/
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter!= null){
        	/** Check whether any bluetooth is nearby**/
        	if (!adapter.isEnabled()){
        		Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        		startActivityForResult(i, s);
        		
        	}
        	
        }
        else{
        	Toast.makeText(getApplicationContext(), "no bluetooth devise found", 3000);
        }
    }
}
