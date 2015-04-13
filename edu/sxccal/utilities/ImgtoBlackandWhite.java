package edu.sxccal.utilities;

//This user libary is used to convert a colour image to black and ImgtoBlackandWhite

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImgtoBlackandWhite
{ 
  public static String toBW(String f,String dest) throws IOException
  {    
    File file = new File(f);
    BufferedImage img = ImageIO.read(file);
    BufferedImage bwimg = new BufferedImage(img.getWidth(), img.getHeight(),BufferedImage.TYPE_BYTE_BINARY);    
    Graphics2D graphics = bwimg.createGraphics();
    graphics.drawImage(img, 0, 0, null);
    f=dest+"/BW.png";
    ImageIO.write(bwimg, "png", new File(f)); //the black and white image is saved as BW.png
    System.out.println("Black and white image is: "+f);    
    return f; 
  } 
}
