package edu.sxccal.qrcodescanner;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.*;
import android.graphics.Bitmap;
import android.graphics.Color;

public class QR
{
	public static void generateQRCode(String dataPath)
	{
	    try
	    {		
	    	String data =read_from_file(dataPath,"ISO-8859-1");
	    	QRCodeWriter writer = new QRCodeWriter();		    
		    String genqr=QRCode.filePath+"/QRCode.png";
		    BitMatrix bm = writer.encode(data, BarcodeFormat.QR_CODE,500,500);		    
		    Bitmap bmp = Bitmap.createBitmap(500,500,Bitmap.Config.ARGB_8888);
		    for (int i = 0; i < 500; i++) 		   
		        for (int j = 0; j < 500; j++) 
		            bmp.setPixel(i, j, bm.get(i, j) ? Color.BLACK: Color.WHITE);
		    if (bmp != null) 
		    {
		    	FileOutputStream gqr=new FileOutputStream(genqr);
		    	bmp.compress(Bitmap.CompressFormat.PNG, 100,gqr);
		    	GenQR.tv.append("\nQRCode img: "+genqr);
		    	gqr.close();
		    }
	    }
	    catch(Exception e)
	    {
	    	Log.create_log(e, GenQR.tv);
	    }
	    
	}
	public static String read_from_file(String s, String charset) throws IOException
	{				
		FileInputStream fp=new FileInputStream(s);
		int c;
		String data="";
		while((c=fp.read())!=-1)
		  data+=(char)c;
		data = new String(data.getBytes(), charset);
		fp.close();
		return data;
	}
}
