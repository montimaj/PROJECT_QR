package edu.sxccal.qrcodescanner;

import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;


/**
 * This module decodes a QRCode image and stores it in Decoded directory as "decoded.txt"
 * @since 1.0
 */

public class DecodeQR extends Activity implements View.OnClickListener
{	
	private Button bt;
	public static TextView tv;
	private final int PICKFILE_RESULT_CODE = 1;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_decode_qr);		
		bt=(Button)findViewById(R.id.edqr);
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
				    decode_qr(f);
			   }
			   break;
		  }
	}

	/**
	 * Writes decoded message to Decoded.txt
	 * @param f Input QRCode image
	 */
	public void decode_qr(String f)
	{
		try
		{			
			tv= (TextView)findViewById(R.id.dqr);
			tv.setText("");
			Bitmap bmp=BitmapFactory.decodeFile(f); //import QRCode image file
			int width = bmp.getWidth(), height = bmp.getHeight();
	        int[] pixels = new int[width * height];
	        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
	        bmp.recycle();
		 	bmp = null;
	        RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels); 
	        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));	       
			Result qr_result = new QRCodeReader().decode(bitmap);
			tv.setText("Successfully Decoded!\n");
			tv.append("Decoded file is at:\n");
			write_to_file(qr_result.getText().toString());
		}
		catch(Exception e)
		{
			Log.create_log(e, getApplicationContext());
		}
	}

	/**
	 * Creates decoded.txt
	 * @param s Decoded string
	 * @throws IOException
	 */
	public void write_to_file(String s) throws IOException
	{
		String dfile=QRCode.filePath+"/Decoded";
		File dir=new File(dfile);
		if(!dir.exists())
			dir.mkdir();
		dfile+="/decoded.txt";
		File file=new File(dfile);
		FileOutputStream fp=new FileOutputStream(file);
		byte[] result=Base64.decode(s.getBytes(), Base64.DEFAULT);
		fp.write(result);
		fp.close();
		tv.append(dfile);
	}
}