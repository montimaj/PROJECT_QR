package edu.sxccal.utilities;
import java.io.File;
import java.io.IOException;

import com.oracle.GenSig;

/**
* QRCode generation module
* <p>
* Uses
* <p>
* {@link edu.sxccal.utilities.ImgtoBlackandWhite} if input file is an image
* <p>
* {@link com.oracle.GenSig} to generate 'sig', 'suepk' files
* <p>
* {@link edu.sxccal.utilities.ZipCreator} to create result.zip containing 'sig','suepk' and input file
* <p>
* {@link edu.sxccal.utilities.QRCode} to generate QRCode.png from result.zip
* <p>
* {@link edu.sxccal.utilities.Log} to create Log.txt in case of any Exception thrown
* @since 1.0
*/
public class GenQR
{
  /**
  * Checks whether an input file is image or not 
  * @param s Input file path
  * @return result in boolean
  */  
  public static boolean is_image(String s)
  {
    String ext=s.substring(s.lastIndexOf('.')+1,s.length()); //get file extension
    if(ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("png"))
      return true;
    return false;
  }
  
  /**
  * @param args[] Input arguments where args[0] should be input file directory, args[1] the output directory
  * @throws Exception 
  */   
  public static void main(String args[]) throws Exception 
  {
    Process p1=null;
    try
    {     
      if(args[0].isEmpty() || args[1].isEmpty())
    	  throw new IOException("Invalid input!");      
      File file=new File(args[0]);
      if(!file.exists())
    	  throw new IOException("Input file doesn't exist!");      
      String[] x={"zenity","--progress","--pulsate","--no-cancel","--text=Generating..."};
      p1=new ProcessBuilder(x).start();
      String filePath = args[1]; 
      File dir = new File(filePath);   
      if(!dir.exists())      
    	  dir.mkdir(); //create directory to store 'sig' and 'suepk' 	
      String result="";      
      if(is_image(args[0]))
      {
	//if the input file is an image convert it to black and white image
      	args[0]=ImgtoBlackandWhite.toBW(args[0],filePath);
      	file=new File(args[0]);
      	result="Black and White image is: "+args[0]+"\n";
      }     
      GenSig.Gen_sig(args[0],filePath);
      String pubkey=filePath+"/suepk",sign=filePath+"/sig",zipin=filePath+"/result.zip";      
      String files[]={pubkey,sign,args[0]};
      ZipCreator.create_zip(zipin,files); //create result.zip
      String f[]={zipin,filePath};     
      result+=QRCode.gen_qrcode(f); //generate QRCode image       
      String[] x1={"zenity","--info","--title=Result","--text="+result};
      Process p2=new ProcessBuilder(x1).start(); //Display window to notify about successful generation
      p1.destroy(); //Destroy progress process
      p2.waitFor(); //Run process p2 until "OK" is pressed      
      String[] x2={"xdg-open",args[1]+"/QRCode.png"};
      p1=new ProcessBuilder(x2).start(); //Display QRCode image      
      p1.waitFor();
    }
    catch(Exception e)
    {    
      p1.destroy();
      String s=Log.create_log(e);
      String[] x={"zenity","--error","--text="+s};
      Process p=new ProcessBuilder(x).start(); //Show error window
      p.waitFor();     
    }    
  }
}