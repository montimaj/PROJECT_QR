package edu.sxccal.qrcodescanner;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import edu.sxccal.qrcodescanner.R;

//The about section of the app 
public class About extends Activity
{	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		TextView tv=(TextView)findViewById(R.id.about),tv1=(TextView)findViewById(R.id.TextView01);
		String s="Version 1.2\nDeveloped by Sayantan-Abhisek-Biswarup";
		s+="\nApp for verification of data!";
		tv.setText(s);
		s="View the entire source in https://github.com/montimaj/PROJECT_QR";
		tv1.setText(s);				
	}	
}
