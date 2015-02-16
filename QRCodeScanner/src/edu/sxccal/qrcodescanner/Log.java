package edu.sxccal.qrcodescanner;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import android.widget.TextView;
public class Log 
{
	public static void create_log(Exception e, TextView tv)
	{
		try
		 {    			 
			 String log=QRCode.filePath+"/log.txt";
			 FileOutputStream fp=new FileOutputStream(log);
			 tv.setText("Input error!\nCheck: "+log);
			 PrintWriter pw=new PrintWriter(fp);
			 pw.write(e.toString());
			 pw.flush();
			 pw.close();
			 fp.close();
		 }catch(Exception e2){}
     }      	
}
