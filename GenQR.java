import java.io.FileInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.oracle.GenSig;
import edu.sxccal.utilities.ZipCreator;
import edu.sxccal.utilities.ImgtoBlackandWhite;
import edu.sxccal.utilities.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

class QRCode 
{	
  public static void gen_qrcode(String[] args) throws Exception
  {		
    String charset = "ISO-8859-1", file = args[1]+"/QRCode.png", data = args[0]; //ISO-8859-1 is used to encode bytes read from the input file, args[1] is the directory path of the QR image
    data=read_from_file(data, charset);   
    Map<EncodeHintType, ErrorCorrectionLevel> hint_map1 = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
    hint_map1.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L); //hints used by QRCodeWriter.encode for efficient generation of the QRCode image
    createQRCode(data, file,hint_map1,500,500);
    System.out.println("QR Code image created successfully!");	       
  }	
  public static String read_from_file(String s, String charset) throws Exception
  {
    FileInputStream fp=new FileInputStream(s);
    int c;
    String data="";
    while((c=fp.read())!=-1)
      data+=(char)c; //convert the data read from file to a string
    data = new String(data.getBytes(), charset);
    fp.close();
    return data;
  }	
  public static void createQRCode(String data, String file, Map<EncodeHintType, ErrorCorrectionLevel> hint_map, int qrh, int qrw) throws Exception
  {		
    BitMatrix matrix = new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, qrw, qrh, hint_map); //Zxing libraries--> BitMatrix, MatrixToImageWriter
    MatrixToImageWriter.writeToFile(matrix, "png",new File(file));
  }	
}
class GenQR
{
  public static boolean is_image(String s)
  {
    String ext=s.substring(s.indexOf('.')+1,s.length()); //get file extension
    if(ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("png"))
      return true;
    return false;
  }
  public static void main(String args[]) throws Exception //args[0]-> input file directory, args[1]-> output directory
  {
    try
    {
      if(args.length<2) 
	throw new Exception("Invalid input!");
      if(!(new File(args[0])).exists())
	throw new Exception("File names should not have spaces!");
      Runtime r=Runtime.getRuntime();
      Process p1=r.exec("zenity --progress --pulsate --no-cancel --text="+"Generating");
      String filePath = args[1]; 
      File dir = new File(filePath), file=new File(args[0]);      
      if(!dir.exists())
	dir.mkdir(); //create directory to store 'sig' and 'suepk' 
      if(is_image(args[0]))
      {
	//if the input file is an image convert it to black and white image
      	args[0]=ImgtoBlackandWhite.toBW(args[0],filePath);
      	file=new File(args[0]);
      }      
      if(file.length()>3000) //File size must be less than 3KB
      {
	p1.destroy();
	throw new Exception("File too large!");	
      }
      GenSig.Gen_sig(args[0],filePath);
      String pubkey=filePath+"/suepk",sign=filePath+"/sig",zipin=filePath+"/result.zip";      
      String files[]={pubkey,sign,args[0]};
      ZipCreator.create_zip(zipin,files); //create result.zip
      String f[]={zipin,filePath};
      File f1=new File(pubkey),f2=new File(sign);      
      QRCode.gen_qrcode(f); //generate QRCode image 
      Process p2=r.exec("zenity --info --text="+"Success!");
      p1.destroy();
      p2.waitFor();
      p1=r.exec("xdg-open "+args[1]+"/QRCode.png");
      p1.waitFor();
    }
    catch(Exception e)
    {
      Runtime r=Runtime.getRuntime();
      String s=Log.create_log(e);
      Process p=r.exec("zenity --error --text="+s);   
      p.waitFor();
    }    
  }
}

/*		***		LIBRARY OVERVIEW	***	                              		   */

/*MatrixToImageWriter: Writes a BitMatrix to BufferedImage, file or stream.
  Class Details: http://zxing.github.io/zxing/apidocs/com/google/zxing/client/j2se/MatrixToImageWriter.html

  BitMatrix: Represents a 2D matrix of bits.  
  Internally the bits are represented in a 1-D array of 32-bit ints. 
  The ordering of bits is row-major.
  Class Details: http://zxing.github.io/zxing/apidocs/com/google/zxing/common/BitMatrix.html

  QRCodeWriter: Renders a QR Code as a BitMatrix 2D array of greyscale values.
  Class Details: http://zxing.github.io/zxing/apidocs/com/google/zxing/qrcode/QRCodeWriter.html

  EncodeHintType: These are a set of hints that you may pass to QRCodeWriter to specify its behavior.
  Class Details: http://zxing.github.io/zxing/apidocs/com/google/zxing/EncodeHintType.html
  
  BarcodeFormat: Specifies the barcode format(Ex: QR_CODE, AZTEC etc)
  Class Details: http://zxing.github.io/zxing/apidocs/com/google/zxing/BarcodeFormat.html

  ErrorCorrectionLevel: This enum encapsulates the four error correction levels defined by the QR code standard.
  Class Details: http://zxing.github.io/zxing/apidocs/com/google/zxing/qrcode/decoder/ErrorCorrectionLevel.html

  HashMap: Hash table based implementation of the Map interface. 
  Class Details: http://docs.oracle.com/javase/7/docs/api/java/util/HashMap.html

  Map: Maps keys to values. A map cannot contain duplicate keys; each key can map to at most one value. 
  Class Details: http://docs.oracle.com/javase/7/docs/api/java/util/Map.html
  
  Runtime: Every Java application has a single instance of class Runtime that allows the application to interface
  with the environment in which the application is running. 
  The current runtime can be obtained from the getRuntime method.
  An application cannot create its own instance of this class.
  Class Details: http://docs.oracle.com/javase/7/docs/api/java/lang/Runtime.html
  
  Process: Runtime.exec create a native process and return an instance of a subclass of Process 
  that can be used to control the process and obtain information about it
  Class Details: http://docs.oracle.com/javase/7/docs/api/java/lang/Process.html
  zenity --progress --no-cancel --text="Generating QRCode and Signature" --pulsate

*/