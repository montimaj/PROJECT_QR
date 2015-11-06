package edu.sxccal.qrcodescanner;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * This is the main activity
 * @since 1.0
 */
public class QRCode extends Activity implements OnClickListener
{
	/**
	 * @param tv TextView for displaying information
	 * @param filePath External storage directory path
	 */
	private Button scanBtn,gen,ver,ab,dqr;
	public static TextView tv;	
	public static String scanContent="No result";
	public static final String filePath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/QR";	
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);        
        //load the main activity layout
        setContentView(R.layout.activity_qr);        
        //Create directory 
        File dir=new File(QRCode.filePath);
        if(!dir.exists())
        	dir.mkdir();        
        //Check which button is pressed
        scanBtn = (Button)findViewById(R.id.scan_button);           
        tv=(TextView)findViewById(R.id.file_write);       
        ver=(Button)findViewById(R.id.ver_sig);
        ab=(Button)findViewById(R.id.ab);
        gen=(Button)findViewById(R.id.gen_qr);
        dqr=(Button)findViewById(R.id.decode);
        ab.setOnClickListener(this);    
        ver.setOnClickListener(this);         
        scanBtn.setOnClickListener(this);  
        gen.setOnClickListener(this);
        dqr.setOnClickListener(this);
    }

	@Override
	public void onClick(View v)
	{
		tv.setText("");
		if(v.getId()==R.id.scan_button)
		{			
			IntentIntegrator scanner = new IntentIntegrator(this); //Zxing android interface library 
			scanner.initiateScan(); //Requires BarcodeScanner app by Zxing to be installed in the phone			
		}	
		if(v.getId()==R.id.ab)
		{
			Intent about=new Intent(QRCode.this,About.class);
			startActivity(about);
		}
		if(v.getId()==R.id.ver_sig)
		{
			Intent verify= new Intent(QRCode.this,Verify.class);                               
        	startActivity(verify);      
		}
		if(v.getId()==R.id.gen_qr)
		{
			Intent qr=new Intent(QRCode.this,GenQR.class);
			startActivity(qr);
		}
		if(v.getId()==R.id.decode)
		{
			Intent qr=new Intent(QRCode.this,DecodeQR.class);
			startActivity(qr);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		if(intent!=null)
		{
			IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);		
			if (scanningResult != null)
			{
				scanContent = scanningResult.getContents();		
				if(checkExternalMedia())
				{
					write_to_file();	        			
					String zipin=filePath+"/result.zip";
					try
					{
						String files[]=Unzip.unzip(zipin, filePath);
						if(files[2].equals(""))
							tv.setText("Image was not scanned properly.Try again!");
						else
							tv.append("\nExtracted files are: \n"+files[0]+"\n"+files[1]+"\n"+files[2]);	
					}
					catch(Exception e)
					{
						Log.create_log(e,getApplicationContext());
					}
				}									
			}
			else
				tv.setText("Device doesn't support read/write!");
		}
		else
		{
		    Toast toast = Toast.makeText(getApplicationContext(),"No scan data received!", Toast.LENGTH_SHORT);
		    toast.setGravity(Gravity.CENTER,0,0);
		    toast.show();		    
		}	
	}

	/**
	 * checks if there is read and write access to device storage
	 * @return true if media has both RW access false otherwise
	 */
	public boolean checkExternalMedia()
	{
		    boolean readable = false;
		    boolean writeable = false;
		    String state = Environment.getExternalStorageState();
		    if (Environment.MEDIA_MOUNTED.equals(state))
		        readable = writeable = true;
		    else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) 
		    {		        
		        readable = true;
		        writeable = false;
		    }
		    else
		        readable = writeable = false;
		    return (readable && writeable);
	}		
	public void write_to_file()
	{			 			
		    File dir = new File (filePath);		    
		    File file = new File(dir, "/result.zip");		    
		    try 
		    {
		        FileOutputStream fos = new FileOutputStream(file);
				fos.write(Base64.decode(scanContent.getBytes(), Base64.DEFAULT));
		        fos.close();
		    } 
		    catch(IOException e)
		    {		    	
		    	Log.create_log(e, getApplicationContext()); //Write logs to log.txt
		    }
		    tv.append("File written to: "+file);
	}	
}