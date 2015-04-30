package edu.sxccal.utilities;

//This user libary is used to convert a colour image to black and ImgtoBlackandWhite
//Throws java.lang.Exception if an exception occurs
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImgtoBlackandWhite
{   
  public static String toBW(String f,String dest) throws Exception
  {    
    File file = new File(f);    
    BufferedImage img = ImageIO.read(file);    
    BufferedImage bwimg = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_BYTE_BINARY);    
    Graphics2D graphics = bwimg.createGraphics();
    graphics.drawImage(img, 0, 0, null);
    f=dest+"/BW.png";
    ImageIO.write(bwimg, "png", new File(f));      
    return f; 
  } 
}

/*		***		LIBRARY OVERVIEW	***	                              		*/

/*Graphics2D: Provides sophisticated control over geometry, coordinate transformations, color management,
 and text layout. This is the fundamental class for rendering 2-dimensional shapes, text and images 
 on the Java(tm) platform.
 Graphics2D.drawImage(Image img,int x,int y,ImageObserver observer): The x,y location specifies the position for the top-left of the image. 
 The observer parameter notifies the application of updates to an image that is loaded asynchronously. 
 It is not frequently used directly and is not needed for the BufferedImage class, so it usually is null.
 Class Details: http://docs.oracle.com/javase/7/docs/api/java/awt/Graphics2D.html

 BufferedImage: Describes an Image with an accessible buffer of image data. 
 BufferedImage.createGraphics(): Creates a Graphics2D, which can be used to draw into this BufferedImage.
 Class Details: http://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferedImage.html
*/