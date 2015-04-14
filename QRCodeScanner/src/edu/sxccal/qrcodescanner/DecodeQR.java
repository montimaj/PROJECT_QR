package edu.sxccal.qrcodescanner;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView.OnEditorActionListener;
import android.widget.TextView;
import android.view.KeyEvent;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.inputmethod.EditorInfo;

import edu.sxccal.qrcodescanner.R;
import edu.sxccal.qrcodescanner.QRCode;

import java.io.File;
import java.io.FileOutputStream;

import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

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
		        if (actionId == EditorInfo.IME_ACTION_DONE) //Waits until DONE button is pressed on the keyboard
		        {
		        	String f= et.getText().toString(); //stores the filepath in a string
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
			Bitmap bmp=BitmapFactory.decodeFile(f); //import QRCode image file
			int width = bmp.getWidth(), height = bmp.getHeight();
	        int[] pixels = new int[width * height];
	        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
	        bmp.recycle();
	        bmp = null;
	        RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels); //Zxing libraries
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

/*								***		LIBRARY OVERVIEW	***	                              						*/

/*EditText: EditText is a thin veneer over TextView that configures itself to be editable. 
  Class Details: http://developer.android.com/reference/android/widget/EditText.html
  
  KeyEvent: Used to report key and button events.   
  Class Details: http://developer.android.com/reference/android/view/KeyEvent.html

  EditorInfo: Describes several attributes of a text editing object that an input method 
  is communicating with (typically an EditText), 
  most importantly the type of text content it contains and the current cursor position. 
  Class Details: http://developer.android.com/reference/android/view/inputmethod/EditorInfo.html

  TextView.OnEditorActionListener: Interface definition for a callback to be invoked 
  when an action is performed on the editor. 
  Class Details: http://developer.android.com/reference/android/widget/TextView.OnEditorActionListener.html

  BitMap: http://developer.android.com/reference/android/graphics/Bitmap.html

  BitMapFactory: Creates Bitmap objects from various sources, including files, streams, and byte-arrays. 
  Class Details: http://developer.android.com/reference/android/graphics/BitmapFactory.html

  RGBLuminanceSource: Used to help decode images from files which arrive as RGB data from an ARGB pixel array.
  Class Details: http://zxing.github.io/zxing/apidocs/com/google/zxing/RGBLuminanceSource.html
  
  BinaryBitMap: The core bitmap class used by ZXing to represent 1 bit data. 
  Reader objects accept a BinaryBitmap and attempt to decode it.
  Class Details: http://zxing.github.io/zxing/apidocs/com/google/zxing/BinaryBitmap.html
  
  Result: Encapsulates the result of decoding a barcode within an image.
  Class Details: http://zxing.github.io/zxing/apidocs/com/google/zxing/Result.html
  
  QRCodeReader: Detects and decodes QR Codes in an image.
  Class Details: http://zxing.github.io/zxing/apidocs/com/google/zxing/qrcode/QRCodeReader.html
 */
 