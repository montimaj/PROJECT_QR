package edu.sxccal.qrcodescanner;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView.OnEditorActionListener;
import android.widget.TextView;
import android.app.Activity;
import android.view.inputmethod.EditorInfo;
import android.view.KeyEvent;

import com.oracle.android.VerSig;

import edu.sxccal.qrcodescanner.R;
import edu.sxccal.qrcodescanner.QRCode;

public class Verify extends Activity 
{	
	private EditText et;
	public static TextView tv;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_ver_sig);		
		et=(EditText)findViewById(R.id.verify_file);
		et.setText(QRCode.filePath+"/");
		et.setOnEditorActionListener(new OnEditorActionListener() 
		{		    
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
		    {		        
		        if (actionId == EditorInfo.IME_ACTION_DONE)
		        {
		            verify_data();		            
		            return true;
		        }
		        return false;
		    }
		});		
	}
	public void verify_data()
	{
		String f3= et.getText().toString();
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
