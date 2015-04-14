package edu.sxccal.qrcodescanner;

import java.io.FileOutputStream;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;

//create black and white image
public class ImgtoBW 
{	
	public static String toBW(String src) throws Exception
	{
		String bw=QRCode.filePath+"/BW.jpg";
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
		return bw;
	}
}

/*								***		LIBRARY OVERVIEW	***	                              						*/

/*ColorMatrix: 4x5 matrix for transforming the color and alpha components of a Bitmap
  Class Details: http://developer.android.com/reference/android/graphics/ColorMatrix.html
  
  ColorMatrixColorFilter: A color filter that transforms colors through a 4x5 color matrix
  Class Details: http://developer.android.com/reference/android/graphics/ColorMatrixColorFilter.html
  
  Canvas: The Canvas class holds the "draw" calls. To draw something, 4 basic components are needed: 
  A Bitmap to hold the pixels, 
  a Canvas to host the draw calls (writing into the bitmap), 
  a drawing primitive (e.g. Rect, Path, text, Bitmap), 
  and a paint (to describe the colors and styles for the drawing). 
  Class Details: http://developer.android.com/reference/android/graphics/Canvas.html
  
  Paint: The Paint class holds the style and color information about how to draw geometries, text and bitmaps. 
  Class Details: http://developer.android.com/reference/android/graphics/Paint.html
*/
