package edu.sxccal.qrcodescanner;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView.OnEditorActionListener;
import android.widget.TextView;
import edu.sxccal.qrcodescanner.R;
import edu.sxccal.qrcodescanner.QRCode;
import android.view.KeyEvent;
import android.app.Activity;
import android.view.inputmethod.EditorInfo;
import java.io.*;
import com.google.zxing.*;
import com.google.zxing.common.*;
import com.google.zxing.qrcode.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DecodeQR extends Activity 
{	
	private EditText et;
	public static TextView tv;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_decode_qr);		
		et=(EditText)findViewById(R.id.edqr);
		et.setText(QRCode.filePath+"/");
		et.setOnEditorActionListener(new OnEditorActionListener() 
		{		    
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
		    {		        
		        if (actionId == EditorInfo.IME_ACTION_DONE)
		        {
		        	String f= et.getText().toString();
		        	decode_qr(f);			            
		            return true;
		        }
		        return false;
		    }
		});		
	}
	public void decode_qr(String f)
	{
			
		try
		{
			tv= (TextView)findViewById(R.id.dqr);
			Bitmap bmp=BitmapFactory.decodeFile(f);
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
			Log.create_log(e, tv);
		}
	}	
	public static void write_to_file(String s)
	{
		try
		{
			String dfile=QRCode.filePath+"/Decoded";
			File dir=new File(dfile);
			if(!dir.exists())
				dir.mkdir();
			dfile+="/decoded.txt";
			File file=new File(dfile);
			FileOutputStream fp=new FileOutputStream(file);
			for(int i=0;i<s.length();++i)
			  fp.write(s.charAt(i));
			fp.close();
			tv.append(dfile);
		}
		catch(Exception e)
		{
			Log.create_log(e, tv);
		}
	}
}
