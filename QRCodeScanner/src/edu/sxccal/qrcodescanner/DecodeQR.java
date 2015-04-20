package edu.sxccal.qrcodescanner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import edu.sxccal.qrcodescanner.R;
import edu.sxccal.qrcodescanner.QRCode;

import java.io.File;
import java.io.FileOutputStream;

import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;


/*This module decodes a QRCode image and stores it in Decoded directory as "decoded.txt" */

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
				    String f = data.getData().getPath();
				    decode_qr(f);
			   }
			   break;
		  }
	}
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
	        RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels); //ZxiHashMap<K, V>aries
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
	public void write_to_file(String s) throws Exception 
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
}

/*		***		LIBRARY OVERVIEW	***					*/

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
 
