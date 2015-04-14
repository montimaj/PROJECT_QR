package edu.sxccal.qrcodescanner;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.oracle.android.GenSig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

import android.graphics.Bitmap;
import android.graphics.Color;

/*This entire module creates the QRCode image, digital signature and the public key */
public class QR
{	
	public static String str="";
	public static void generateQRCode(String dataPath) throws Exception //create QRCode image
	{			
	    String data =read_from_file(dataPath,"ISO-8859-1");	    
	    QRCodeWriter writer = new QRCodeWriter();		    
		String genqr=QRCode.filePath+"/QRCode.png";
		BitMatrix bm = writer.encode(data, BarcodeFormat.QR_CODE,500,500);	//Zxing library	    
		Bitmap bmp = Bitmap.createBitmap(500,500,Bitmap.Config.ARGB_8888); //image size is 500*500 pixels
		for (int i = 0; i < 500; i++) 		   
			for (int j = 0; j < 500; j++) 
				bmp.setPixel(i, j, bm.get(i, j) ? Color.BLACK: Color.WHITE);		
		if (bmp != null) 
		{
			File f=new File(genqr);
		    if(f.exists())
		    	f.delete();
		    FileOutputStream gqr=new FileOutputStream(genqr);
		    bmp.compress(Bitmap.CompressFormat.PNG, 100,gqr);
		    str+="\nQRCode img: "+genqr;		    		    
		    gqr.close();
		}			
	}
	public static String read_from_file(String s, String charset) throws Exception
	{		
		String ext=s.substring(s.indexOf('.')+1,s.length());
		boolean flag=false;	
		RandomAccessFile fp=new RandomAccessFile(s,"r");
		if(ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpeg") && fp.length()>1500)
		{
			//if input file is an image of size>~1.5KB convert it to black and white
			s=ImgtoBW.toBW(s);
			flag=true;			
		}
		fp.close();
		fp=new RandomAccessFile(s,"rw");			
		String data="";
		for(int i=0;i<fp.length();++i)
		  data+=(char)fp.read(); //store data read from input file in a string			
		GenSig.Gen_sig(s);	//Generate digital signature and public key		
		if(flag)
			str+="\nB&W image: "+s;
		data = new String(data.getBytes(), charset);
		fp.close();			
		return data;
	}
}
/*				***		LIBRARY OVERVIEW	***	 			*/

/*RandomAccessFile: Instances of this class support both reading and writing to a random access file
  Class Details: http://docs.oracle.com/javase/7/docs/api/java/io/RandomAccessFile.html

  BitMap.Config: Possible bitmap configurations. A bitmap configuration describes how pixels are stored.
  This affects the quality (color depth) as well as the ability to display transparent/translucent colors. 
  ARGB_8888: Each pixel is stored on 4 bytes.  
  Class Details: http://developer.android.com/reference/android/graphics/Bitmap.Config.html
  
  BitMap.CompressFormat: Specifies the known formats a bitmap can be compressed into 
  Class Details: http://developer.android.com/reference/android/graphics/Bitmap.CompressFormat.html

  Color: The Color class defines methods for creating and converting color ints
  Class Details: http://developer.android.com/reference/android/graphics/Color.html
  
  BitMatrix: Represents a 2D matrix of bits.  
  Internally the bits are represented in a 1-D array of 32-bit ints. 
  The ordering of bits is row-major.
  Class Details: http://zxing.github.io/zxing/apidocs/com/google/zxing/common/BitMatrix.html

  QRCodeWriter: Renders a QR Code as a BitMatrix 2D array of greyscale values.
  Class Details: http://zxing.github.io/zxing/apidocs/com/google/zxing/qrcode/QRCodeWriter.html
  
  BarcodeFormat: Specifies the barcode format(Ex: QR_CODE, AZTEC etc)
  Class Details: http://zxing.github.io/zxing/apidocs/com/google/zxing/BarcodeFormat.html
*/
