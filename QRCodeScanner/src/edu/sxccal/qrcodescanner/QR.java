package edu.sxccal.qrcodescanner;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.oracle.android.GenSig;

import java.io.*;

import android.graphics.Bitmap;
import android.graphics.Color;

public class QR
{
	public static void generateQRCode(String dataPath) //create QRCode image
	{
	    try
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
		String ext=s.substring(s.indexOf('.')+1,s.length());
		boolean flag=false;	
		RandomAccessFile fp=new RandomAccessFile(s,"rw");
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
		GenSig.Gen_sig(s);	//Generate digital signature
		if(flag)
			GenQR.tv.append("\nB&W image: "+s);
		data = new String(data.getBytes(), charset);
		fp.close();
		return data;
	}	
}