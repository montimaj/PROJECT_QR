package edu.sxccal.qrcodescanner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.TextView.OnEditorActionListener;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

import java.io.File;

public class GenQR extends Activity implements Runnable
{
	private EditText et;	
	private static ProgressDialog dialog;
	public static TextView tv;	
	private String f;
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gen_qr);
		et=(EditText)findViewById(R.id.genqr);
		et.setText(QRCode.filePath+"/");
		tv=(TextView)findViewById(R.id.wfile);
		et.setOnEditorActionListener(new OnEditorActionListener() 
		{		    
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
		    {		        
		        if (actionId == EditorInfo.IME_ACTION_DONE)
		        {		            
		        	tv.setText("");
		        	f=et.getText().toString();
		        	File file=new File(f);
		        	if(!file.exists())
		        	{
		        		tv.setText("");
		        		Log.create_log(new Exception("File not found"), getApplicationContext());
		        	}
		        	else
		        	{
			        	dialog = ProgressDialog.show(GenQR.this, "Generating QRCode, signature...",
			                     "Please wait!", true,false);	
			        	Thread thread = new Thread(GenQR.this);		        	
		                thread.start();
		        	}
		        	return true;
		        }                
		        return false;
		    }	    
		});	
	}
	public void run()
	{	
		try
		{
			QR.generateQRCode(f);			
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}
		handler.sendEmptyMessage(0);
	}
	private static Handler handler = new Handler()
	{
         	public void handleMessage(Message msg)
         	{
                 	dialog.dismiss();
                 	tv.setText(QR.str);
         	}
 	};	
}
