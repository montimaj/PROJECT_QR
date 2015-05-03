package AUTHENTIC_QR;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/** 
* Generates the QRCode image
* @since 1.0
* @see <a href="http://zxing.github.io/zxing/apidocs/">Zxing</a>
*/
public class QRCode 
{	
  /**
  * @param args Input arguments where 
  * <p>
  * args[0] is the path to Result.zip and 
  * args[1] is the output directory to store QRCode.png
  * @return Path to QRCode.png 
  */
  public static String gen_qrcode(String[] args) throws Exception
  {		
    //Base64 is used to encode bytes read from the input file, args[1] is the directory path of the QR image
    String file = args[1]+"/QRCode.png", data = args[0]; 
    data=read_from_file(data);  //if this exceeds ~4.2KB with low error correction level then Exception will occur    
    Map<EncodeHintType, ErrorCorrectionLevel> hint_map1 = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
    hint_map1.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L); //hints used by QRCodeWriter.encode for efficient generation of the QRCode image
    createQRCode(data, file,hint_map1,500,500);
    String s="Base64 encoded data size of result.zip: "+data.length()+" bytes\nQR Code image created successfully!";
    return s;
  }
  
  /**
  * Reads bytes from input file
  * and encodes in {@link java.util.Base64} BarcodeFormat
  * @param s Input file path
  * @return Base64 encoded String
  * @throws IOException
  */
  public static String read_from_file(String s) throws IOException
  {    
      FileInputStream fis=new FileInputStream(s);
      byte[] data = new byte[fis.available()];
      fis.read(data);
      s=Base64.getEncoder().encodeToString(data);
      return s;      
  }
  
  /**
  * Generates QRCode.png
  * @param data String to be encoded in QRCode
  * @param file Output file path
  * @param hint_map hints used by {@link QRCodeWriter} for efficiency
  * @param qrh Height of QRCode.png
  * @param qrw Width of QRCode.png
  * @throws WriterException generally if input data is too big
  * @throws IOException
  */
  public static void createQRCode(String data, String file, Map<EncodeHintType, ErrorCorrectionLevel> hint_map, int qrh, int qrw) throws WriterException, IOException
  {		
    BitMatrix matrix = new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, qrw, qrh, hint_map); //Zxing libraries--> BitMatrix, MatrixToImageWriter
    MatrixToImageWriter.writeToFile(matrix, "png",new File(file));
  }	
}