import java.io.*;
import com.oracle.GenSig;
import edu.sxccal.utilities.ZipCreator;
import java.util.HashMap;
import java.util.Map;
import com.google.zxing.*;
import com.google.zxing.client.j2se.*;
import com.google.zxing.common.*;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
class QRCode 
{
	public static void gen_qrcode(String[] args) throws WriterException, IOException, NotFoundException
	{		
		String charset = "ISO-8859-1", file = args[1]+"/QRCode.png", data = args[0];		
		data=read_from_file(data, charset);		
		System.out.println("Result.zip size= "+data.length()+" bytes");
		Map<EncodeHintType, ErrorCorrectionLevel> hint_map1 = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
		hint_map1.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);		
		createQRCode(data, file,hint_map1,500,500);
	      	System.out.println("QR Code image created successfully!");	       
	}	
	public static String read_from_file(String s, String charset) throws IOException
	{
		FileInputStream fp=new FileInputStream(s);
		int c;
		String data="";
		while((c=fp.read())!=-1)
		  data+=(char)c;
		data = new String(data.getBytes(), charset);
		fp.close();
		return data;
	}	
	public static void createQRCode(String data, String file, Map<EncodeHintType, ErrorCorrectionLevel> hint_map, int qrh, int qrw) throws WriterException, IOException
	{		
		BitMatrix matrix = new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, qrw, qrh, hint_map);
		MatrixToImageWriter.writeToFile(matrix, "png",new File(file));
	}	
}
class GenQR
{
  public static void main(String args[]) throws IOException
  {
    try
    {
      String filePath = args[1];
      File dir = new File(filePath);
      if(!dir.exists())
	dir.mkdir();
      GenSig.Gen_sig(args[0],filePath);
      String pubkey=filePath+"/suepk",sign=filePath+"/sig",zipin=filePath+"/result.zip";
      String files[]={pubkey,sign,args[0]};
      ZipCreator.create_zip(zipin,files);
      String f[]={zipin,filePath};
      File f1=new File(pubkey),f2=new File(sign);
      f1.delete();
      f2.delete();
      QRCode.gen_qrcode(f);           
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
    }
  }
}
