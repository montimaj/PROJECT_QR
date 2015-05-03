package edu.sxccal.qrcodescanner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.oracle.android.VerSig;

/**
 *  This activity displays the verification result
 *  @since 1.0
 *  */

public class Verify extends Activity implements View.OnClickListener
{
	private Button bt;
	public static TextView tv;
	private final int PICKFILE_RESULT_CODE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_ver_sig);		
		bt=(Button)findViewById(R.id.verify_file);
		bt.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
        fileintent.setType("file/*");
        try 
        {
            startActivityForResult(fileintent,PICKFILE_RESULT_CODE);            
        } 
        catch (Exception e) 
        {
            Log.create_log(e, getApplicationContext());
        }	
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{		  
		  switch(requestCode)
		  {
			  case PICKFILE_RESULT_CODE:
			   if(resultCode==RESULT_OK)
			   {
				    String f = data.getData().getPath();
				    verify_data(f);
			   }
			   break;
		  }
	}

	/**
	 *
	 * @param f3 Input file to be verified
	 */
	public void verify_data(String f3)
	{		
		//get absolute paths of the files
		String f1=QRCode.filePath + "/suepk", f2=QRCode.filePath + "/sig", files[]={f1,f2,f3};
		tv= (TextView)findViewById(R.id.file_verify);
		tv.setText("");
		try
		{
			VerSig.verify(files);
		}
		catch(Exception e)
		{
			Log.create_log(e, getApplicationContext());
		}
	}	
}
