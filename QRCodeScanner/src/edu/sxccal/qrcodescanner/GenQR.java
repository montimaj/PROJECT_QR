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

//This activity uses QR.java module to display 
//the paths of the generated QRCode.png, sig and suepk files
//if successful
//else raises a Toast that displays the path to Log.txt
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
			handler.sendEmptyMessage(0);
		}
		catch(Exception e)
		{			
			handler.sendEmptyMessage(1);
		}
			
	}
	private static Handler handler = new Handler()
	{
         	public void handleMessage(Message msg)
         	{
         		dialog.dismiss();
         		tv.setText(QR.str);
         		if(msg.what==1)
         		{
         			tv.append("\nCheck QR/log.txt");
         			Log.create_log("QRCode generation failed!");         	
         		}
         	}
         	
 	};	
}

/*			***		LIBRARY OVERVIEW	***	               				*/

/*Thread: A Thread is a concurrent unit of execution.
  It has its own call stack for methods being invoked, their arguments and local variables
  The start() method must be called to actually execute the new Thread. 
  Class Details: http://developer.android.com/reference/java/lang/Thread.html

  Runnable: Represents a command that can be executed.
  Often used to run code in a different Thread.
  Interface Details: http://developer.android.com/reference/java/lang/Runnable.html
  
  Handler: Used to send and process Message and Runnable objects associated with a thread's MessageQueue
  Class Details: http://developer.android.com/reference/android/os/Handler.html
  
  Message: Defines a message containing a description and arbitrary data object
  that can be sent to a Handler
  Class Details: http://developer.android.com/reference/android/os/Message.html
  
  ProgressDialog: A dialog showing a progress indicator and an optional text message or view. 
  Only a text message or a view can be used at the same time.
  The dialog can be made cancelable on back key press.
  Class Details: http://developer.android.com/reference/android/app/ProgressDialog.html
  
  Intent.ACTION_GET_CONTENT: Allows the user to select a particular kind of data and return it. 
  Class Details: http://developer.android.com/reference/android/content/Intent.html
*/
  
   

