package edu.sxccal.qrcodescanner;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import edu.sxccal.qrcodescanner.R;

/**
 * The about section of the app
 */
public class About extends Activity
{	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
	}	
}
