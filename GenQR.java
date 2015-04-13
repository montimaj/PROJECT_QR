import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.oracle.GenSig;
import edu.sxccal.utilities.ZipCreator;
import edu.sxccal.utilities.ImgtoBlackandWhite;

import com.google.zxing.*;
import com.google.zxing.client.j2se.*;
import com.google.zxing.common.*;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
class QRCode 
{	
  public static void gen_qrcode(String[] args) throws WriterException, IOException, NotFoundException
  {		
    String charset = "ISO-8859-1", file = args[1]+"/QRCode.png", data = args[0]; //ISO-8859-1 is used to encode bytes read from the input file, args[1] is the directory path of the QR image
    data=read_from_file(data, charset);		
    System.out.println("Result.zip size= "+data.length()+" bytes");
    Map<EncodeHintType, ErrorCorrectionLevel> hint_map1 = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
    hint_map1.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);	 //hints used by QRCodeWriter.encode for efficient generation of the QRCode image
    createQRCode(data, file,hint_map1,500,500);
    System.out.println("QR Code image created successfully!");	       
  }	
  public static String read_from_file(String s, String charset) throws IOException
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
  public static void createQRCode(String data, String file, Map<EncodeHintType, ErrorCorrectionLevel> hint_map, int qrh, int qrw) throws WriterException, IOException
  {		
    BitMatrix matrix = new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, qrw, qrh, hint_map); //Zxing libraries--> BitMatrix, MatrixToImageWriter
    MatrixToImageWriter.writeToFile(matrix, "png",new File(file));
  }	
}
class GenQR
{
  public static boolean is_image(String s)
  {
    String ext=s.substring(s.indexOf('.')+1,s.length());
    if(ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("png"))
      return true;
    return false;
  }
  public static void main(String args[]) throws IOException //args[0]-> input file directory, args[1]-> output directory
  {
    try
    {
      String filePath = args[1]; 
      File dir = new File(filePath);
      if(!dir.exists())
	dir.mkdir(); //create directory to store 'sig' and 'suepk'      
      if(is_image(args[0]))
      {
	//if the input file is an image and file size is greater than 1KB convert it to black and white image
      	args[0]=ImgtoBlackandWhite.toBW(args[0],filePath);
      }      
      GenSig.Gen_sig(args[0],filePath);
      String pubkey=filePath+"/suepk",sign=filePath+"/sig",zipin=filePath+"/result.zip";      
      String files[]={pubkey,sign,args[0]};
      ZipCreator.create_zip(zipin,files); //create result.zip
      String f[]={zipin,filePath};
      File f1=new File(pubkey),f2=new File(sign);
      f1.delete(); //delete 'sig' and 'suepk'
      f2.delete();      
      QRCode.gen_qrcode(f); //generate QRCode image          
    }
    catch(Exception e)
    {
      System.out.println("I/O error!: "+e.toString());
    }
  }
}
