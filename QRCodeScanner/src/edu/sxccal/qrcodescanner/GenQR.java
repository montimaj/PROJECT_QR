package edu.sxccal.qrcodescanner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import java.io.File;

public class GenQR extends Activity implements Runnable,View.OnClickListener
{		
	private static ProgressDialog dialog;
	public static TextView tv;	
	private Button bt;
	private String f;
	public final int PICKFILE_RESULT_CODE = 1;
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gen_qr);		
		tv=(TextView)findViewById(R.id.wfile);
		bt=(Button)findViewById(R.id.genqr);
		bt.setOnClickListener(this);       	
	}
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{		  
		  switch(requestCode)
		  {
			  case PICKFILE_RESULT_CODE:
			   if(resultCode==RESULT_OK)
			   {
				    f = data.getData().getPath();	
				    File file=new File(f);
		        	if(!file.exists())
		        	{
		        		tv.setText("");
		        		Log.create_log(new Exception("File not found"), getApplicationContext());
		        	}
		        	else
		        	{
			        	tv.setText("");
		        		dialog = ProgressDialog.show(GenQR.this, "Generating QRCode, signature...",
			                     "Please wait!", true,false);	
			        	Thread thread = new Thread(GenQR.this);		        	
		                thread.start();
		        	}		    
			   }
			   break;		   
		  }
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
                 	QR.str="";
         	}
 	};	
}
