package edu.sxccal.qrcodescanner;
import java.io.*;
import android.graphics.*;

public class ImgtoBW
{	
	public static String toBW(String src)
	{
		String bw=QRCode.filePath+"/BW.jpg";
		try
		{
			ColorMatrix cm = new ColorMatrix();
		        cm.setSaturation(0);
		        ColorMatrixColorFilter cmf = new ColorMatrixColorFilter(cm);
			Bitmap bmp=BitmapFactory.decodeFile(src);					
			int sw=bmp.getWidth(),sh=(int)bmp.getHeight();
			Bitmap bmp1=Bitmap.createScaledBitmap(bmp,(int)(sw*.7),(int)(sh*.7),true);
			Bitmap bmp2=bmp1.copy(Bitmap.Config.ARGB_8888,true);			
			Paint paint = new Paint();
		        paint.setColorFilter(cmf);
		        Canvas canvas = new Canvas(bmp2);
		        canvas.drawBitmap(bmp2, 0, 0, paint);			
			FileOutputStream fp=new FileOutputStream(bw);				
			bmp2.compress(Bitmap.CompressFormat.JPEG,7,fp);
			fp.close();			
		}
		catch(Exception e)
		{
			Log.create_log(e, GenQR.tv);
		}
		return bw;
	}
}
