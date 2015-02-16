package edu.sxccal.utilities;
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
    ImageIO.write(bwimg, "png", new File(dest+"/BW.png"));
    return dest+"/BW.png";
  } 
}
