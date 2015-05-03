package edu.sxccal.utilities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
* Converts a coloured image to black and ImgtoBlackandWhite
* @since 1.0
*/
public class ImgtoBlackandWhite
{   
  /**
  * @param f Input image filepath
  * @param dest Output directory to store the black and white image(BW.png)
  * @return path to BW.png
  * @throws Exception
  */
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